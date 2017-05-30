package su.vistar.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import su.vistar.client.dto.CompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.dto.ResponseForSearchDTO;
import su.vistar.client.dto.ResponseObjectDTO;
import su.vistar.client.model.Company;
import su.vistar.client.service.DBCriteriaService;
import su.vistar.client.service.ExtractUsersService;

@RestController
@RequestMapping("external_api/")
public class ExternalAPIController {

    @Autowired
    DBCriteriaService dbService;
    
    @Autowired
    ExtractUsersService  extractService;
    
    //подписаться на рассылку
    @PostMapping(value = "/subscribe")
    @ResponseBody
    public ResponseEntity<?> subscribe(@RequestParam("vk_uid") String vkUid, @RequestParam("code") String code) {

        Company company = dbService.getCompanyByCode(code);
        if (company == null) {
            return new ResponseEntity<>(new ResponseObjectDTO("компании с заданным кодом не существует", null), HttpStatus.NOT_FOUND);
        } else if (dbService.tryUnigueSubscribe(vkUid, company.getId()) != null) {            
            return new ResponseEntity<>(new ResponseObjectDTO("подписки итак не было", null), HttpStatus.ACCEPTED);
        } else {
            dbService.subscribe(vkUid, company.getId());
            return new ResponseEntity<>(new ResponseObjectDTO("подписка прошла успешно", company), HttpStatus.OK);
        }
    }

    //отписаться - это тоже будет POST
    @GetMapping(value = "/unsubscribe")
    @ResponseBody
    public ResponseEntity<?> unsubscribe(@RequestParam("vk_uid") String vkUid, @RequestParam(value = "code", required = false) String code) {
        Company company;
        company = (code != null) ? dbService.getCompanyByCode(code) : null;
        //указан неверный код компании для отписки
        if (company == null && code != null) {
            return new ResponseEntity<>(new ResponseObjectDTO("компании с заданным кодом не существует", null), HttpStatus.NOT_FOUND);
        } //отписываемся только от рассылки по текущей компании
        else if (company != null) {
            dbService.unscribe(vkUid, company.getId());
            return new ResponseEntity<>(new ResponseObjectDTO("отписка осуществлена успешно", company), HttpStatus.OK);
        } //отписываемся ото всех компаний, на которые подписаны
        else {
            dbService.unscribe(vkUid, null);
            return new ResponseEntity<>(new ResponseObjectDTO("отписка ото всех камапний осуществлена успешно", company), HttpStatus.OK);
        }
    }

    //запрос на список компаний, на которые осуществляется подписка
    @GetMapping(value = "/get_companies")
    @ResponseBody
    public ResponseEntity<?> getCompanies(@RequestParam("vk_uid") String vkUid) {
        List<CompanyDTO> companies = dbService.getCompanies(vkUid);
        if (companies.isEmpty()) {
            return new ResponseEntity<>(new ResponseObjectDTO("подписки на камапнии отсуствуют", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseObjectDTO(companies), HttpStatus.OK);
    }

    //получить сообщение и адресатов по критериям
    @GetMapping(value = "/get_users_and_message")
    @ResponseBody
    public ResponseEntity<?> getMessageAndRecipients(@RequestParam("company_id")int companyId, @RequestParam("count")int count) {
        List<CriteriaDTO> listCriteria = dbService.getCriteriaByCompanyId(companyId);
        int criteriaCount = listCriteria.size();
        List<ResponseForSearchDTO> list = new ArrayList<>();
        //перенести метод в сервис и обобщить для двух случаев
        //кол-во сообщений меньше числа существующих критериев
        String message;
        String queryVkString;
        int offset;           
        CriteriaDTO criteria;
        ResponseForSearchDTO serverResponse;
        if (count <= criteriaCount){          
            for(int i=0; i < count; i++){
                criteria = listCriteria.get(i);
                queryVkString = criteria.getCondition();//строка запроса
                offset = criteria.getOffset();//смещение
                message = dbService.getMessageByCriteriaId(criteria.getId()).getText();
                try {
                    //обновить offset 
                    serverResponse = extractService.getUsers(queryVkString, offset, 1);
                    serverResponse.setMessage(message);
                    list.add(serverResponse);
                } catch (IOException ex) {
                    Logger.getLogger(ExternalAPIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }          
        }
        ////кол-во сообщений больше числа существующих критериев
        else {
            //распределение количества сообщений по критериям
            int[] numberByCriteria = new int[criteriaCount];
            //по каждому критерию отправляем portion сообщений
            int portion = count / criteriaCount;
            for(int i=0; i < criteriaCount; i++){
                numberByCriteria[i] = portion;
            }
            //остаток сливаем в последний критерий
            numberByCriteria[criteriaCount-2] += count - portion*criteriaCount;
            //вытаскиваем по критериям
            for(int i=0; i < criteriaCount; i++){
                criteria = listCriteria.get(i);
                queryVkString = criteria.getCondition();
                offset = listCriteria.get(i).getOffset();
                message = dbService.getMessageByCriteriaId(criteria.getId()).getText();
                //обновляем offset              
                try { 
                    serverResponse = extractService.getUsers(queryVkString, offset, numberByCriteria[i]);
                    serverResponse.setMessage(message);
                    list.add(serverResponse);
                    
                } catch (IOException ex) {
                    Logger.getLogger(ExternalAPIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }      
           //return new ResponseEntity<>(new ResponseObjectDTO("все адресаты исчерпаны", null), HttpStatus.NOT_FOUND); 
        return new ResponseEntity<>(new ResponseObjectDTO(list), HttpStatus.OK); 
    }
}