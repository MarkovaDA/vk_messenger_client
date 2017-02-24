
package su.vistar.client.controller;

import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.User;
import su.vistar.client.service.AuthService;
import su.vistar.client.service.UserService;
import su.vistar.client.service.VKApiService;
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
    UserService userService;
    
    @Autowired
    DBMapper dbMapper;
     
   /*private final String clientId = "5801227";
    private final String clientSecret = "kzErha5eVdhBsKWJMcJ1";
    private final String redirectUri = "http://localhost:8080/vk_messanger/tools";*/
    
    @GetMapping(value = "/tools_options")
    public ModelAndView getToolPage(Model model){
        User currentUser = AuthService.getCurrentUser(userService);
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
        //сохранение критерия в базу
        User currentUser = AuthService.getCurrentUser(userService); 
        dbMapper.saveCriteria(criteria.toString(),0, currentUser.getId());
        dbMapper.saveMessageForCriteria(dbMapper.lastInsertedCriteriaId(), criteria.getMessage());
        return "ok";
    }
       
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage(ModelMap model){
        return new ModelAndView("login");
    }
}
