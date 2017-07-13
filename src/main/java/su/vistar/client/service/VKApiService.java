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

    @Autowired
    HTTPService httpService;  
    
    private final String BASE_URL = "https://api.vk.com/method/";
    
    enum QueryPattern {
        getCountriesUrl ("database.getCountries&v=5.60&count=200"),
        getCityByCountryUrl ("database.getCities?country_id=%d&count=100&v=5.60"),
        getUniversitiesByCity ("database.getUniversities?city_id=%d&v=5.60"),
        getFacultiesByUniversity("database.getFaculties?university_id=%d&v=5.60"),
        getChairByFaculty("database.getChairs?faculty_id=%d&v=5.60"),
        getSchoolByCity("database.getSchools?city_id=%d&count=100&v=5.60"),
        getUsersUrl("users.search?%s&offset=%d&v=5.60"),
        getCityUrl("database.getCities"),
        getUsersInfoUrl("users.get?user_ids=%d&v=5.65");
        
        private final String url;       
        private QueryPattern(String s) {
            url = s;
        }
        public boolean equalsName(String otherName) {
            return url.equals(otherName);
        }
        public String toString() {
           return this.url;
        }
    }
   
    private final String CLIENT_ID = "5801227";
    private final String REDIRECT_URI = "http://localhost:8084/on_click_spammer/regist";
    private final String CLIENT_SECRET = "kzErha5eVdhBsKWJMcJ1";
    private final String ACCESS_TOKEN_URL = "https://oauth.vk.com/access_token";
  
    //полуение персонального кода доступа польователя
    public AccessToken getAccessToken(String code) throws UnsupportedEncodingException, ProtocolException, IOException{
        Map<String,String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);
        String result = httpService.doPOSTQuery(ACCESS_TOKEN_URL, params);
        return (new Gson()).fromJson(result, AccessToken.class);
    }
    //получение информации о пользователе по uid
    public VKUserDTO getUserByUid(Long uid){
        try {
            String queryUrl = String.format(BASE_URL.concat(QueryPattern.getUsersInfoUrl.toString()), uid);
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
    public  List<VKObjectDTO> getSchoolsByCity(Integer cityId) throws MalformedURLException, IOException{
       return httpService.doGETQuery(getQueryForCitiesByCountry(cityId));
    }
    public  List<VKObjectDTO> getUniversitiesByCity(int cityId) throws MalformedURLException, IOException{
        return httpService.doGETQuery(getQueryForUniversitiesByCityId(cityId));
    }
    public  List<VKObjectDTO> getFaculties(int universityId) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForFaculties(universityId));
    }
    public  List<VKObjectDTO> getChairs(int facultyId) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForChairs(facultyId));
    }
    public  List<VKObjectDTO> getSchools(int cityId) throws MalformedURLException, ProtocolException, IOException{
       return httpService.doGETQuery(getQueryForSchoolsByCity(cityId));
    }       
   
    //поиск населенного пункта по названию
    public String getCityByPattern(String pattern, Integer countryId) throws ProtocolException, IOException{
        Map map = new HashMap<>();
        map.put("q", pattern);
        map.put("country_id", countryId);
        map.put("v", 5.60);
        //map.put("access_token", accessToken);
        map.put("count", 50);
        String url = BASE_URL.concat(QueryPattern.getCityUrl.toString());
        return  httpService.doPOSTQuery(url, map);
    }
    
    private String getQueryForChairs(int facultyId){                
        return String.format(BASE_URL.concat(QueryPattern.getChairByFaculty.toString()), facultyId);
    }            
    private String getQueryForFaculties(int universityId){       
        return String.format(BASE_URL.concat(QueryPattern.getFacultiesByUniversity.toString()), universityId);
    }       
    private String getQueryForUniversitiesByCityId(int cityId){              
        return String.format(BASE_URL.concat(QueryPattern.getUniversitiesByCity.toString()), cityId);
    }
    private String getQueryForCountries(){
        return BASE_URL.concat(QueryPattern.getCountriesUrl.toString());
    }    
    private String getQueryForCitiesByCountry(int countryId){
        return String.format(BASE_URL.concat(QueryPattern.getCityByCountryUrl.toString()), countryId);
    }    
    private String getQueryForSchoolsByCity(int cityId){
        return String.format(BASE_URL.concat(QueryPattern.getSchoolByCity.toString()), cityId);
    } 
}
