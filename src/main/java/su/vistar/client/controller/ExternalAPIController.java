package su.vistar.client.controller;

import java.util.List;
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
import su.vistar.client.dto.ResponseObjectDTO;
import su.vistar.client.model.Company;
import su.vistar.client.service.DBCriteriaService;

@RestController
@RequestMapping("external_api/")
public class ExternalAPIController {

    @Autowired
    DBCriteriaService dbService;

    //подписаться на рассылку - и это будет POST
    @GetMapping(value = "/subscribe")
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
        if (company != null && code != null) {
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

    //получить сообщение и адресатов
    public void getMessageAndRecipients() {

    }

}
