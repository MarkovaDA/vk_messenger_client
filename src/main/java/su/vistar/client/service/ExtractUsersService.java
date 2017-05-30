package su.vistar.client.service;


import com.google.gson.Gson;
import java.io.IOException;
import java.net.ProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.dto.ResponseForSearchDTO;

@Service
public class ExtractUsersService {
    @Autowired
    private CommonHTTPService httpService;  
    
    private String searchUserQueryFormat 
            = "https://api.vk.com/method/users.search?%s&offset=%d&count=%d&access_token=%s&v=5.60";
       
    public ResponseForSearchDTO getUsers(String queryString, int offset, int count) throws ProtocolException, IOException{            
        String queryStr = getQueryForUsersSearch(queryString, offset, count);
        String response = httpService.doPureGETQuery(queryStr);
        Gson gson = new Gson();
        return gson.fromJson(response,ResponseForSearchDTO.class);
    }
    
    private String getQueryForUsersSearch(String queryString, int offset, int count){          
        String response =  String.format(searchUserQueryFormat, queryString, offset, count, httpService.ACCESS_TOKEN);
        return response;
    }
}
