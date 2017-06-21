package su.vistar.client.service;


import com.google.gson.Gson;
import su.vistar.client.dto.VKObjectDTO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.dto.UsersGetResponse;
import su.vistar.client.dto.VKUserDTO;
import su.vistar.client.model.AccessToken;

@Service
public class VKApiService{
    //этот сервис подлежит переписыванию, а данные выкачиванию
    @Autowired
    HTTPService httpService;  
    
    private String cityQueryFormat = "https://api.vk.com/method/database.getCities?country_id=1&v=5.60";
    private String countryQueryFormat = "https://api.vk.com/method/database.getCountries&v=5.60&count=200";
    private String facultyQueryFormat = "https://api.vk.com/method/database.getFaculties?university_id=%d&v=5.60"; 
    private String universityQueryFormat = "https://api.vk.com/method/database.getUniversities?city_id=%d&v=5.60"; 
    private String chairQueryFormat = "https://api.vk.com/method/database.getChairs?faculty_id=%d&v=5.60";
    private String cityQueryByCountryFormat = "https://api.vk.com/method/database.getCities?country_id=%d&count=100&v=5.60";
    private String schoolQueryFormat = "https://api.vk.com/method/database.getCities?country_id=%d&count=100&v=5.60";
    private String searchUserQueryFormat = "https://api.vk.com/method/users.search?%s&offset=%d&v=5.60";
    //запрашиваем token
    private String CLIENT_ID = "5801227";
    private String REDIRECT_URI = "http://localhost:8084/vk_messenger_client/regist";
    private String CLIENT_SECRET = "kzErha5eVdhBsKWJMcJ1";
    private String accessTokenUrl = "https://oauth.vk.com/access_token";
    private String usersGetUrl = "https://api.vk.com/method/users.get?user_ids=%d&v=5.65";    
   
    public AccessToken getAccessToken(String code) throws UnsupportedEncodingException, ProtocolException, IOException{
        Map<String,String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);
        String result = httpService.doPOSTQuery(accessTokenUrl, params);
        return (new Gson()).fromJson(result, AccessToken.class);
    }
    public VKUserDTO getUserByUid(Long uid){
        try {
            String queryUrl = String.format(usersGetUrl, uid);
            String result = httpService.doPureGetQuery(queryUrl);
            Gson gson = new Gson();
            UsersGetResponse response = gson.fromJson(result, UsersGetResponse.class);
            if (response.getResponse() != null && response.getResponse().size() > 0)
                return response.getResponse().get(0);
            return null;
        } catch (ProtocolException ex) {
            Logger.getLogger(VKApiService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VKApiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public  List<VKObjectDTO> getCountries() throws MalformedURLException, IOException{        
       return httpService.doGETQuery(getQueryForCountries());
    }
    public  List<VKObjectDTO> getCities(int countryId) throws MalformedURLException, IOException{
       return httpService.doGETQuery(getQueryForCitiesByCountry(countryId));
    }
    public  List<VKObjectDTO> getCitiesByCountry(Integer countryId) throws MalformedURLException, IOException{
       return httpService.doGETQuery(getQueryForCitiesByCountry(countryId));
    }
    public  List<VKObjectDTO> getUniversities(int cityId) throws MalformedURLException, IOException{
        return httpService.doGETQuery(getQueryForUniversities(cityId));
    }  
    public  List<VKObjectDTO> getFaculties(int universityId) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForFaculties(universityId));
    }
    public  List<VKObjectDTO> getChairs(int facultyId) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForChairs(facultyId));
    }
    private  String getQueryForChairs(int facultyId){                
        return String.format(chairQueryFormat, facultyId);
    }        
    private  String getQueryForFaculties(int universityId){       
        return String.format(facultyQueryFormat, universityId);
    }   
    private  String getQueryForUniversities(int cityId){              
        return String.format(universityQueryFormat, cityId);
    }      
    private  String getQueryForCountries(){
        return countryQueryFormat;
    }
    private String getQueryForCitiesByCountry(int countryId){
        return String.format(cityQueryByCountryFormat, countryId);
    }
    
    
}
