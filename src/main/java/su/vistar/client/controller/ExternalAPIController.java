package su.vistar.client.controller;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import su.vistar.client.dto.UserToCompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.dto.UsersToMessageDTO;
import su.vistar.client.dto.VKUserDTO;
import su.vistar.client.dto.ErrorResponse;
import su.vistar.client.dto.UserStatisticsDTO;
import su.vistar.client.mapper.StatisticsMapper;
import su.vistar.client.service.DBCriteriaService;
import su.vistar.client.service.ExtractUsersService;

@RestController
@RequestMapping("api/")
public class ExternalAPIController {

    @Autowired
    DBCriteriaService dbService;

    @Autowired
    ExtractUsersService extractService;

    
    //подписаться на рассылку
    @PostMapping(value = "/company/{code}/subscribe")
    @ResponseBody
    public ResponseEntity<?> subscribe(@RequestParam("vk_uid") Long vkUid,
            @PathVariable("code") Long code,
            @RequestParam("message_count") Integer count) {
        
        UserToCompanyDTO companyInfo = dbService.getCompanyInfo(code);             
        if (companyInfo == null) {
            return new ResponseEntity<>(new ErrorResponse("Компании с заданным кодом не существует"), HttpStatus.NOT_FOUND);
        }
        companyInfo.setMessage_count(count); 
        //проверка на соответсвие заданному количеству
        //как учесть саму кампанию, на которую может быть подписка?
        int limit = 20;
        Integer busyCount = dbService.countOfSubscribesForUser(vkUid);
        //за вычетом того, сколько осталось их
        busyCount = (busyCount == null) ? 0 : busyCount; //сколько сообщений осталось
        
        if (dbService.tryUnigueSubscribe(vkUid, companyInfo.getId()) != null) {         
            //пользователь подписан - происходит модификация параметров
            int oldCount = dbService.countOfSubscribeForUser(vkUid, companyInfo.getId());
            if (count > oldCount + limit - busyCount)
                return new ResponseEntity<>(new ErrorResponse("Количество сообщений превышает отведенный лимит. Осталось сообщений:" + (limit - busyCount)), 
                        HttpStatus.BAD_REQUEST);
            dbService.updateCompanyCode(vkUid, count, code);
            return new ResponseEntity<>(companyInfo, HttpStatus.OK);
        } 
        else {
            if (busyCount + count > limit) 
                return new ResponseEntity<>(new ErrorResponse("Количество сообщений превышает отведенный лимит. Осталось сообщений:" + (limit - busyCount)), 
                        HttpStatus.BAD_REQUEST);
            dbService.subscribe(vkUid, companyInfo.getId(), count);
            return new ResponseEntity<>(companyInfo, HttpStatus.OK);
        }
    }

    //отписаться
    @PostMapping(value = "/company/{code}/unsubscribe")
    @ResponseBody
    public ResponseEntity<?> unsubscribe(@RequestParam("vk_uid") Long vkUid,
            @PathVariable(value = "code", required = false) Long code) {

        UserToCompanyDTO companyInfo;
        companyInfo = (code != null) ? dbService.getCompanyInfo(code) : null;
        //указан неверный код компании для отписки
        if (companyInfo == null && code != null) {
            return new ResponseEntity<>(new ErrorResponse("Компании с заданным кодом не существует"), HttpStatus.NOT_FOUND);
        }
        //юзер итак не подписан
        if (dbService.tryUnigueSubscribe(vkUid, companyInfo.getId()) == null) {
            return new ResponseEntity<>(new ErrorResponse("Юзер с заданным vk_uid не подписан на рассылку по данной кампании"), HttpStatus.CONFLICT);
        } //отписка от установленной компании
        else if (companyInfo != null) {
            dbService.unscribe(vkUid, companyInfo.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } //отписываемся ото всех компаний, на которые подписаны
        else {
            dbService.unscribe(vkUid, null);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //запрос на список компаний, на которые осуществляется подписка (+) добавить кол-во собщений
    @GetMapping(value = "/subscriptions")
    @ResponseBody
    public ResponseEntity<?> getCompanies(@RequestParam("vk_uid") String vkUid) {
        List<UserToCompanyDTO> companies = dbService.getCompanies(vkUid);
        if (companies.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("У данного пользователя подписки отсутсвуют"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    //получить сообщение и адресатов по критериям 
    @GetMapping(value = "/company/{code}/messages")
    @ResponseBody
    public  ResponseEntity<?> getMessageAndRecipients(
            @PathVariable("code") Long code, 
            @RequestParam("vk_uid")Long vkUid) {
        UserToCompanyDTO companyInfo = dbService.getCompanyInfo(code);
        if (companyInfo == null) {
            return new ResponseEntity<>(new ErrorResponse("Компании с заданным code не существует"), HttpStatus.BAD_REQUEST);
        }
        //кол-во сообщений, которые юзер готов отправлять за кампанию ежедневно
        Integer count = dbService.getCountMessagesByCompanyId(companyInfo.getId(), vkUid);        
        if (count == null) {
            return new ResponseEntity<>(new ErrorResponse("Юзер с заданным vk_uid не подписан на рассылку по данной кампании"), HttpStatus.CONFLICT);
        }
        List<CriteriaDTO> listCriteria = dbService.getCriteriaByCompanyId(companyInfo.getId());       
        int criteriaCount = listCriteria.size();
        List<UsersToMessageDTO> responseList = new ArrayList<>();
        //у кампании нету критериев
        if (criteriaCount == 0){
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        //кол-во сообщений меньше числа существующих критериев
        String message;
        String queryVkString;
        int offset;
        CriteriaDTO criteria;
        List<VKUserDTO> usersByCriteria;//список пользователей по критерию полученных
        if (count <= criteriaCount) {
            for (int i = 0; i < count; i++) {
                criteria = listCriteria.get(i);
                queryVkString = criteria.getCondition();//строка запроса
                offset = criteria.getOffset();//смещение
                message = dbService.getMessageByCriteriaId(criteria.getId()).getText();
                try {
                    usersByCriteria = extractService.getUsers(queryVkString, offset, 1).getResponse().getItems();
                    //обновление offset
                    dbService.updateOffset(criteria.getId(), offset + 1);
                    responseList.add(new UsersToMessageDTO(message, usersByCriteria, criteria.getId()));
                } catch (IOException ex) {
                    Logger.getLogger(ExternalAPIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } ////кол-во сообщений больше числа существующих критериев
        else {
            //распределение количества сообщений по критериям
            int[] numberByCriteria = new int[criteriaCount];
            //по каждому критерию отправляем portion сообщений
            int portion = count / criteriaCount;
            for (int i = 0; i < criteriaCount; i++) {
                numberByCriteria[i] = portion;
            }
            //остаток сливаем в последний критерий
            if (criteriaCount > 1) {
                numberByCriteria[criteriaCount - 2] += count - portion * criteriaCount;
            }
            //вытаскиваем по критериям
            for (int i = 0; i < criteriaCount; i++) {
                criteria = listCriteria.get(i);
                queryVkString = criteria.getCondition();
                offset = listCriteria.get(i).getOffset();
                message = dbService.getMessageByCriteriaId(criteria.getId()).getText();
                //обновляем offset              
                try {
                    usersByCriteria = extractService.getUsers(queryVkString, offset, numberByCriteria[i]).getResponse().getItems();
                    dbService.updateOffset(criteria.getId(), offset + numberByCriteria[i]);
                    responseList.add(new UsersToMessageDTO(message, usersByCriteria, criteria.getId()));
                } catch (IOException ex) {
                    Logger.getLogger(ExternalAPIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (responseList.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Адресаты исчерпаны"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @Autowired
    StatisticsMapper statisticsMapper;
    
    //статистика по отправке
    @PutMapping(value = "/statistics/{sender_vk_id}")
    @ResponseBody
    public ResponseEntity<?> putStatistics(@RequestBody  ArrayList<UserStatisticsDTO> list,
            @PathVariable("sender_vk_id")Long senderVkId){
        //проверка на несуществующий критерий
        list.forEach(item -> {
            Date sendingDate = new Date(item.getDeviceDate()*1000L);
            
            if (item.getErrorMsg() == null)
                item.setErrorMsg("SUCCESS");
            
            statisticsMapper.insertStatisticsInfo(
                    item.getCriterionId(), 
                    senderVkId, 
                    item.getReceiverVkId(),
                    item.getErrorMsg(),
                    sendingDate);
        });
        return new ResponseEntity<>(HttpStatus.OK); 
    }
   
}
