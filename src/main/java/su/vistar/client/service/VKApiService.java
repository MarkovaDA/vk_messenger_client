package su.vistar.client.service;


import su.vistar.client.dto.VKObjectDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.net.ProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VKApiService{
    
    @Autowired
    HTTPService httpService;  
      
    private String cityQueryFormat = "https://api.vk.com/method/database.getCities?country_id=1&count=100&v=5.60";
    private String countryQueryFormat = "https://api.vk.com/method/database.getCountries?сount=100&v=5.60";
    private String facultyQueryFormat = "https://api.vk.com/method/database.getFaculties?university_id=%d&v=5.60"; 
    private String universityQueryFormat = "https://api.vk.com/method/database.getUniversities?city_id=%d&v=5.60"; 
    private String chairQueryFormat = "https://api.vk.com/method/database.getChairs?faculty_id=%d&v=5.60";
    private String cityQueryByCountryFormat = "https://api.vk.com/method/database.getCities?country_id=%d&count=100&v=5.60";
    private String schoolQueryFormat = "https://api.vk.com/method/database.getCities?country_id=%d&count=100&v=5.60";
    private String searchUserQueryFormat = "https://api.vk.com/method/users.search?%s&offset=%d";
   
    public  List<VKObjectDTO> getCountries() throws MalformedURLException, IOException{        
       return httpService.doGETQuery(getQueryForCountries());
    }
    public  List<VKObjectDTO> getCities() throws MalformedURLException, IOException{
       return httpService.doGETQuery(getQueryForCities());
    }
    //получение городов страны
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
    private  String getQueryForCities(){
        return cityQueryFormat;
    } 
    private  String getQueryForCountries(){
        return countryQueryFormat;
    }
    private String getQueryForCitiesByCountry(int countryId){
        return String.format(cityQueryByCountryFormat, countryId);
    }       
}
