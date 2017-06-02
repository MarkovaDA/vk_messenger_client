package su.vistar.client.controller;

import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.User;
import su.vistar.client.service.AuthService;
import su.vistar.client.service.VKApiService;
import su.vistar.client.service.DBCriteriaService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import su.vistar.client.model.Company;



@Controller
public class MainController {
    @Autowired
    VKApiService vkService; 
    
    @Autowired
    DBCriteriaService criteriaService;
    
    @Autowired
    AuthService authService;
    
   
    @GetMapping(value = "/tools_options")
    public ModelAndView getToolPage(Model model){
        User currentUser = authService.getCurrentUser();
        //здесь список компаний
        //String  token = currentUser.getAccess_token();   
        //model.addAttribute("accessToken", token);
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
    
    @PostMapping(value = "/save_criteria/{company_code}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCriteria(@RequestBody AdresatCriteria criteria, 
            @PathVariable("company_code")Long companyCode){
        int companyId = criteriaService.getCompanyByCode(companyCode).getId();
        criteriaService.saveCriteria(criteria, companyId);
        return new ResponseEntity<>("success saving", HttpStatus.OK);
    }
    
    @PostMapping(value = "/add_company", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        ResponseEntity<String> response;
        company.setUser_id(authService.getCurrentUser().getId());
        int count =  criteriaService.addCompany(company);
        if (count > 0)
            response = new ResponseEntity<>("success inserting", HttpStatus.OK);
        else 
            response = new ResponseEntity<>("error inserting", HttpStatus.BAD_REQUEST);
        return response;
    }
       
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(ModelMap model){
        return new ModelAndView("login");
    } 
}
