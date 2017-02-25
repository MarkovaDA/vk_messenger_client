
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
        String  token = currentUser.getAccess_token();   
        model.addAttribute("accessToken", token);
        model.addAttribute("login", currentUser.getLogin());
        try {
            model.addAttribute("cities", vkService.getCities(token));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("main_page");
    }
    
    @PostMapping(value = "/save_criteria")
    @ResponseBody
    public String saveCriteria(@RequestBody AdresatCriteria criteria){       
        criteriaService.saveCriteria(criteria);
        return "ok";
    }
       
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(ModelMap model){
        return new ModelAndView("login");
    } 
}
