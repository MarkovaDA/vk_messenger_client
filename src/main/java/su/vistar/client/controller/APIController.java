package su.vistar.client.controller;

import su.vistar.client.dto.VKObjectDTO;
import su.vistar.client.service.VKApiService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import su.vistar.client.model.Message;
import su.vistar.client.service.DBCriteriaService;


@RestController
@RequestMapping("api/")
public class APIController {
    @Autowired
    VKApiService vkService;
    
    @Autowired
    DBCriteriaService criteriaService;
    
    @GetMapping(value="get_cities")
    public List<VKObjectDTO> getCities(@RequestParam("token")String accessToken){
        try {
            return vkService.getCities(accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping(value="get_universities")
    public List<VKObjectDTO> getUniversities(@RequestParam("token")String accessToken,
            @RequestParam("city_id")int cityId){
        try {
            return vkService.getUniversities(cityId, accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping(value="get_faculties")
    public List<VKObjectDTO> getFaculties(@RequestParam("token")String accessToken,
            @RequestParam("univ_id")int univId){
        try {
            return vkService.getFaculties(univId,accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping(value="get_chairs")
    public List<VKObjectDTO> getChairs(@RequestParam("token")String accessToken,
            @RequestParam("faculty_id")int facultyId){
        try {
            return vkService.getChairs(facultyId, accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping(value="get_messages")
    @ResponseBody
    public List<Message> getMessages(){
        return criteriaService.getMessages();
    }
    
    @GetMapping(value="get_message")
    @ResponseBody
    public Message getMessage(@RequestParam("mes_id")Integer mesId){
        return criteriaService.getMessageById(mesId);
    }
}
