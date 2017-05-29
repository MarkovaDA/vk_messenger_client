
package su.vistar.client.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import su.vistar.client.dto.CompanyDTO;
import su.vistar.client.dto.ResponseObjectDTO;
import su.vistar.client.exceptions.DataNotFoundException;
import su.vistar.client.exceptions.IrrelevantInformationException;
import su.vistar.client.exceptions.NoActionException;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseObjectDTO subscribe(@RequestParam("vk_uid")String vkUid, @RequestParam("code")String code){
        ResponseObjectDTO response = new ResponseObjectDTO();
        Company company = dbService.getCompanyByCode(code);
        if (company == null){ 
            throw new IrrelevantInformationException();
        }
        else if (dbService.tryUnigueSubscribe(vkUid, company.getId()) != null)  {
            throw new NoActionException();
        }
        else {                       
            dbService.subscribe(vkUid, company.getId());
            response.setMessage("подписка прошла успешно");
            response.setResponseObject(company); 
        }
        return response;
    }
    
    //отписаться - это тоже будет POST
    @GetMapping(value = "/unsubscribe")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseObjectDTO unsubscribe(@RequestParam("vk_uid")String vkUid, @RequestParam(value = "code", required=false)String code){
        ResponseObjectDTO response = new ResponseObjectDTO();   
        Company company;
        company = (code != null) ?  dbService.getCompanyByCode(code) : null;                  
        //указан неверный код компании для отписки
        if (company != null && code != null){
            response.setMessage("компании с указанным кодом не существует");
            response.setResponseObject(null);             
        }
        //отписываемся только от рассылки по текущей компании
        else if (company != null) {
            response.setResponseObject(company);
            response.setMessage("отписка осуществлена успешно");
            dbService.unscribe(vkUid, company.getId());
        }
        //отписываемся ото всех компаний, на которые подписаны
        else {
            response.setMessage("отписка осуществлена успешно");
            dbService.unscribe(vkUid, null);
        }            
        return response;
    }
    //запрос на список компаний, на которые осуществляется подписка
    @GetMapping(value = "/get_companies")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyDTO> getCompanies(@RequestParam("vk_uid")String vkUid){        
        List<CompanyDTO> companies = dbService.getCompanies(vkUid);
        if (companies.isEmpty())
            throw new DataNotFoundException();
        return companies;
    }
        
    //получить сообщение и адресатов
    public void getMessageAndRecipients(){

    }
    
}
