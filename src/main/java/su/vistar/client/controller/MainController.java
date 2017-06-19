package su.vistar.client.controller;

import com.google.gson.Gson;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.User;
import su.vistar.client.service.AuthService;
import su.vistar.client.service.VKApiService;
import su.vistar.client.service.DBCriteriaService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.mapper.CriteriaMapper;
import su.vistar.client.model.Company;



@Controller
public class MainController {
    @Autowired
    VKApiService vkService; 
    
    @Autowired
    DBCriteriaService criteriaService;
    
    @Autowired
    AuthService authService;
    
    @Autowired
    CriteriaMapper criteriaMapper;
    
    //дополнительный json-конвертер
    private static final Gson gson = new Gson();
    
    @GetMapping(value = "/tools_options")
    public ModelAndView getToolPage(Model model){
        User currentUser = authService.getCurrentUser();
        //здесь список компаний
        //String  token = currentUser.getAccess_token();   
        model.addAttribute("login", currentUser.getLogin());
        try {
            model.addAttribute("companies", authService.getCompanies(currentUser.getId()));
            model.addAttribute("cities", vkService.getCities());
            model.addAttribute("countries", vkService.getCountries());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("main_page");
    }

    //сохранение критерия
    @PostMapping(value = "/save_criteria/{company_code}",  
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> saveCriteria(@RequestBody AdresatCriteria criteria, 
           @PathVariable("company_code")Long companyCode){
        Company company = criteriaService.getCompanyByCode(companyCode);
        if (company == null)
            return new ResponseEntity<>(gson.toJson("ошибка добавления критерия"), HttpStatus.BAD_REQUEST);
        int companyId = company.getId();
        criteriaService.saveCriteria(criteria, companyId);
        return new ResponseEntity<>(gson.toJson("критерий успешно добавлен"), HttpStatus.OK);
    }
    
    @GetMapping(value = "/company/criteria/{company_code}",  
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<CriteriaDTO> getCriteria(@PathVariable("company_code")Long companyCode){
        Company company = criteriaService.getCompanyByCode(companyCode);
        int companyId = company.getId();
        List<CriteriaDTO> criteriaList = criteriaService.getCriteriaByCompanyId(companyId);
        return criteriaList;
    }
    
    @PostMapping(value = "/company/criteria/{id}/delete",  
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deleteCriteria(@PathVariable("id")Integer id){
        //criteriaMapper.deleteCriteriaById(id);
        return new ResponseEntity<>(gson.toJson("критерий удален успешно"), HttpStatus.OK);
    }
   
    @GetMapping(value = "/add_company", produces = "application/json;charset=UTF-8"
    /*MediaType.APPLICATION_JSON_VALUE*/)
    @ResponseBody
    public ResponseEntity<?> addCompany(@RequestBody Company company){
        ResponseEntity<String> response;
        Integer isUniqueCode = criteriaService.tryUnigueCompanyCode(company.getCode());
        isUniqueCode = (isUniqueCode != null) ? isUniqueCode : 0;
        if (isUniqueCode > 0)            
            return new ResponseEntity<>(gson.toJson("Такой код кампании уже используется"), HttpStatus.CONFLICT);
        Integer isUniqueTitle = criteriaService.tryUniqueCompanyTitle(company.getTitle());
        isUniqueTitle = (isUniqueTitle != null) ? isUniqueTitle : 0;
        if (isUniqueTitle > 0)            
            return new ResponseEntity<>(gson.toJson("Такое название кампании уже используется"), HttpStatus.CONFLICT);
        company.setUser_id(authService.getCurrentUser().getId());
        Integer count =  criteriaService.addCompany(company);
        //проверить код и название на уникальность
        count = (count != null) ? count : 0;
        if (count > 0)
            response = new ResponseEntity<>(gson.toJson("кампания добавлена успешно"), HttpStatus.OK);
        else 
            response = new ResponseEntity<>(gson.toJson("ошибка обновления кода кампании"), HttpStatus.BAD_REQUEST);
        return response;
    }
    
    @PostMapping(value = "/update_company", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> updateCompany(@RequestBody Company company){
        
        Integer isUniqueCode = criteriaService.tryUnigueCompanyCode(company.getCode());
        isUniqueCode = (isUniqueCode != null) ? isUniqueCode : 0;
        if (isUniqueCode > 0)            
            return new ResponseEntity<>(gson.toJson("предложенный код уже используется"), HttpStatus.CONFLICT);         
        company.setUser_id(authService.getCurrentUser().getId());
        Integer count =  criteriaService.updateCompanyCode(company);
        count = (count != null) ? count : 0;
        ResponseEntity<String> response;
        if (count > 0)
            response = new ResponseEntity<>(gson.toJson("код успешно обновлен"), HttpStatus.OK);
        else 
            response = new ResponseEntity<>(gson.toJson("ошибка обновления кода"), HttpStatus.BAD_REQUEST);          
        return response;
    }
  
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(ModelMap model){
        return new ModelAndView("login");
    }   
}
