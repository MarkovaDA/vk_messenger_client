package su.vistar.client.service;


import com.google.gson.Gson;
import su.vistar.client.dto.VKObjectDTO;
import su.vistar.client.model.AccessTokenObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.net.ProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VKApiService{
    
    @Autowired
    HTTPService httpService;
    //отправка сообещния, сделать отдельный пост -запрос
    public  boolean sendMessage(String userId, String message, String accessToken) throws IOException, MalformedURLException, MalformedURLException, IOException, UnsupportedEncodingException{ 
        String query = "https://api.vk.com/method/messages.send";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("user_ids", userId);
        params.put("message", message);
        params.put("access_token", accessToken);
        String response = httpService.doPOSTQuery(query,params);
        return (!response.contains("error"));
    }
    //параметр count настроить
    public  List<VKObjectDTO> getCities(String accessToken) throws MalformedURLException, IOException{
       return httpService.doGETQuery(getQueryForCities(accessToken));
    }
    //получение университетов по городу
    public List<VKObjectDTO> getUniversities(int cityId,String accessToken) throws MalformedURLException, IOException{
        return httpService.doGETQuery(getQueryForUniversities(cityId, accessToken));
    }  
    //получение факультетов по идентификатору университета
    public  List<VKObjectDTO> getFaculties(int universityId,String accessToken) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForFaculties(universityId,accessToken));
    }
    private  String getQueryForFaculties(int universityId,String accessToken){
        String queryFormat = "https://api.vk.com/method/database.getFaculties?university_id=%d&v=5.60&access_token=%s";        
        return String.format(queryFormat, universityId, accessToken);
    }   
    private  String getQueryForUniversities(int cityId, String accessToken){
        String queryFormat = "https://api.vk.com/method/database.getUniversities?city_id=%d&v=5.60&access_token=%s";        
        return String.format(queryFormat, cityId, accessToken);
    }  
    private  String getQueryForCities(String accessToken){
        String queryFormat = "https://api.vk.com/method/database.getCities?country_id=1&сount=50&v=5.60&access_token=%s";
        return String.format(queryFormat, accessToken);
    }
    public String getAccessToken(String clientId, String clientSecret,String redirectUri,String code) throws MalformedURLException, ProtocolException, IOException{
        Gson gson = new Gson();
        String baseUrl = "https://oauth.vk.com/access_token";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("code", code);
        String accessToken = httpService.doPOSTQuery(baseUrl, params);
        AccessTokenObject token = gson.fromJson(accessToken, AccessTokenObject.class);
        return token.getAccess_token();
    }        
}
