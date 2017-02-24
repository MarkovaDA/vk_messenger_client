package su.vistar.client.controller;

import su.vistar.client.dto.VKObjectDTO;
import su.vistar.client.service.VKApiService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIController {
    @Autowired
    VKApiService vkService;
    
    @GetMapping(value="api/get_cities")
    public List<VKObjectDTO> getCities(@RequestParam("token")String accessToken){
        try {
            return vkService.getCities(accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping(value="api/get_universities")
    public List<VKObjectDTO> getUniversities(@RequestParam("token")String accessToken,
            @RequestParam("city_id")int cityId){
        try {
            return vkService.getUniversities(cityId, accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping(value="api/get_faculties")
    public List<VKObjectDTO> getFaculties(@RequestParam("token")String accessToken,
            @RequestParam("univ_id")int univId){
        try {
            return vkService.getFaculties(univId,accessToken);
        } catch (IOException ex) {
            Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  
}
