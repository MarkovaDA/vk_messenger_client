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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
    @PostMapping(value = "/save_criteria")
    @ResponseBody
    public String saveCriteria(@RequestBody AdresatCriteria criteria){       
        criteriaService.saveCriteria(criteria);
        return "ok saving";
    }
    
    @PostMapping(value = "/add_company")
    @ResponseBody
    public String addCompany(@RequestBody Company company){
        company.setUser_id(authService.getCurrentUser().getId());
        return "ok saving";
    }
       
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(ModelMap model){
        return new ModelAndView("login");
    } 
}
