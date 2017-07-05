package su.vistar.client.controller;

import com.google.gson.Gson;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.User;
import su.vistar.client.service.AuthService;
import su.vistar.client.service.VKApiService;
import su.vistar.client.service.DBCriteriaService;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import su.vistar.client.configuration.SMTPMailSender;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.dto.UserStatisticsDTO;
import su.vistar.client.dto.VKUserDTO;
import su.vistar.client.mapper.AuthUserMapper;
import su.vistar.client.mapper.CriteriaMapper;
import su.vistar.client.mapper.StatisticsMapper;
import su.vistar.client.model.AccessToken;
import su.vistar.client.model.Company;
import su.vistar.client.model.SenderStatisticsReport;



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
    
    @Autowired
    AuthUserMapper authUserMapper;
    
    @Autowired
    StatisticsMapper statisticsMapper;
    
    @Autowired
    private SMTPMailSender mailSender;

    private static final Gson gson = new Gson();
    
    private static String oauthVkUrl = "https://oauth.vk.com/" +
            "authorize?" +
            "client_id=5801227" +
            "&redirect_uri=http://localhost:8084/on_click_spammer/regist"+
            "&response_type=code&scope=offline";
    
    @GetMapping(value = "/tools")
    public ModelAndView getToolsPage(Model model, @ModelAttribute("uid")Long uid){        
        User currentUser = authService.getCurrentUser(uid);  
        model.addAttribute("login", currentUser.getFio());      
        model.addAttribute("companies", authService.getCompanies(currentUser.getId()));
        model.addAttribute("countries", criteriaService.getCountries());
        model.addAttribute("uid", uid);       
        return new ModelAndView("main_page");
    }   
    
    @GetMapping(value = "/start")
    public ModelAndView start(){
        return new ModelAndView("redirect:" + oauthVkUrl);
    }    
    
    @GetMapping(value = "/wait")
    public ModelAndView getWaitPage(Model model, @ModelAttribute("approval")Integer approval, @ModelAttribute("uid")Long uid){
        if (approval==0)
            model.addAttribute("message", "Ваша заявка еще не подтверждена. Пожалуйста, ждите");
        else if (approval==1)
            model.addAttribute("message", "К сожалению, Ваша заявка отклонена");
        String login = vkService.getUserByUid(uid).getFirst_name() + " " + vkService.getUserByUid(uid).getLast_name();
        model.addAttribute("login", login);
        return new ModelAndView("wait_page");
    }
    
    @GetMapping(value = "/regist")
    public RedirectView regist(@RequestParam("code")String code, RedirectAttributes attributes) throws MessagingException{       
        try {          
            AccessToken token = vkService.getAccessToken(code);
            Long uid = token.getUser_id();
            VKUserDTO vkUser =  vkService.getUserByUid(uid);
            User systemUser = authUserMapper.getUserByUid(uid);
            if (systemUser == null){
                systemUser = new User();
                //добавляем пользователя в бд
                systemUser.setLogin(null);
                systemUser.setPassword(null);
                systemUser.setStatus("FORBIDDEN");
                systemUser.setUid(uid);
                systemUser.setExpires_in(token.getExpires_in());
                systemUser.setLast_date(new Date());
                systemUser.setFio(vkUser.getFirst_name() + " " + vkUser.getLast_name());
                systemUser.setAccess_token(token.getAccess_token());
                authUserMapper.addNewUser(systemUser);
                mailSender.newUserNotify(uid);
                attributes.addAttribute("approval", 0);
                attributes.addAttribute("uid", systemUser.getUid());
                return new RedirectView("wait");
            }
            else {
                if (systemUser.getStatus().equals("ACTIVE")){
                    attributes.addAttribute("uid", systemUser.getUid());
                    return new RedirectView("tools");
                }
                else {
                    attributes.addAttribute("uid", systemUser.getUid());
                    if (systemUser.getStatus().equals("FORBIDDEN"))
                        attributes.addAttribute("approval", 0);
                    if (systemUser.getStatus().equals("DECLINED"))
                        attributes.addAttribute("approval", 1);
                    return new RedirectView("wait");
                }
            }

        } catch (ProtocolException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new RedirectView("wait");
    }    
    
  
    @GetMapping(value = "/statistics/{criterion_id}")
    public ModelAndView getStatisticsPage(Model model, 
            @PathVariable("criterion_id")Integer criterionId,
            @RequestParam(value = "vk_sender", required=false)Long vkSender){
        
        User currentUser = authService.getCurrentUser(null);  
        model.addAttribute("login", currentUser.getFio());        
        if (vkSender != null){
            List<UserStatisticsDTO> userStatisticsList 
                    = statisticsMapper.getCriteriaStatisticsBySender(vkSender);
            model.addAttribute("listInfo", userStatisticsList);
            model.addAttribute("addressee", vkSender);
            return new ModelAndView("sender_report_page");
        }
        List<SenderStatisticsReport> senderStatisticsList = statisticsMapper.getStatisticsBySendors();
        model.addAttribute("listInfo", senderStatisticsList);
        return new ModelAndView("criteria_statistics_page");
    }
        
    @GetMapping(value = "/approve")
    public ModelAndView getApproveActionPage(@RequestParam("uid")Long uid){
        authUserMapper.updateUserStatus("ACTIVE", uid);
        return new ModelAndView("approve_page");
    }
    @GetMapping(value = "/decline")
    public ModelAndView getDeclineActionPage(@RequestParam("uid")Long uid){
        authUserMapper.updateUserStatus("DECLINED", uid);
        return new ModelAndView("decline_page");
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
        criteriaMapper.deleteCriteriaById(id);
        criteriaMapper.deleteCriteriaReference(id);
        return new ResponseEntity<>(gson.toJson("критерий удален успешно"), HttpStatus.OK);
    }
   
    @PostMapping(value = "/add_company", produces = "application/json;charset=UTF-8"
    /*MediaType.APPLICATION_JSON_VALUE*/)
    @ResponseBody
    public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestParam("uid")Long uid){
        ResponseEntity<String> response;
        Integer isUniqueCode = criteriaService.tryUnigueCompanyCode(company.getCode());
        isUniqueCode = (isUniqueCode != null) ? isUniqueCode : 0;
        if (isUniqueCode > 0)            
            return new ResponseEntity<>(gson.toJson("Такой код кампании уже используется"), HttpStatus.CONFLICT);
        Integer isUniqueTitle = criteriaService.tryUniqueCompanyTitle(company.getTitle());
        isUniqueTitle = (isUniqueTitle != null) ? isUniqueTitle : 0;
        if (isUniqueTitle > 0)            
            return new ResponseEntity<>(gson.toJson("Такое название кампании уже используется"), HttpStatus.CONFLICT);
        company.setUser_id(authService.getCurrentUser(uid).getId());
        Integer count =  criteriaService.addCompany(company);
        //проверить код и название на уникальность
        count = (count != null) ? count : 0;
        if (count > 0){
            response = new ResponseEntity<>(gson.toJson("кампания добавлена успешно"), HttpStatus.OK);
            try {
                mailSender.newCompanyNotify(uid, company.getTitle());
            } catch (MessagingException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
            response = new ResponseEntity<>(gson.toJson("ошибка добавления кампании"), HttpStatus.BAD_REQUEST);
        return response;
    }
    
    @PostMapping(value = "/update_company", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> updateCompany(@RequestBody Company company,@RequestParam("uid")Long uid){
        
        Integer isUniqueCode = criteriaService.tryUnigueCompanyCode(company.getCode());
        isUniqueCode = (isUniqueCode != null) ? isUniqueCode : 0;
        if (isUniqueCode > 0)            
            return new ResponseEntity<>(gson.toJson("предложенный код уже используется"), HttpStatus.CONFLICT);         
        company.setUser_id(authService.getCurrentUser(uid).getId());
        Integer count =  criteriaService.updateCompanyCode(company);
        count = (count != null) ? count : 0;
        ResponseEntity<String> response;
        if (count > 0)
            response = new ResponseEntity<>(gson.toJson("код успешно обновлен"), HttpStatus.OK);
        else 
            response = new ResponseEntity<>(gson.toJson("ошибка обновления кода"), HttpStatus.BAD_REQUEST);          
        return response;
    }
}
