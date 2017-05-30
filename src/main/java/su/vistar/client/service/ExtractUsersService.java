package su.vistar.client.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.dto.VKUserDTO;

@Service
public class ExtractUsersService {
    @Autowired
    private CommonHTTPService httpService;  
    
    private String searchUserQueryFormat = "https://api.vk.com/method/users.search?%s&offset=%d&count=%d&access_token=%s&v=5.60";
       
    public List<VKUserDTO> getUsers(String queryString, int offset, int count) throws ProtocolException, IOException{            
        String queryStr = getQueryForUsersSearch(queryString, offset, count);
        String response = httpService.doPureGETQuery(queryStr);
        //парсим - невероятный костылище просто
        Gson gson = new Gson();
        Type responseType = new TypeToken<ArrayList<VKUserDTO>>(){}.getType(); 
        int startIndex = response.lastIndexOf("items") + "items".length() + 1;
        queryStr = response.substring(startIndex + 1, response.length() - 2);
        return gson.fromJson(response, responseType);
    }
    
    private String getQueryForUsersSearch(String queryString, int offset, int count){          
        String response =  String.format(searchUserQueryFormat, queryString, offset, count, httpService.ACCESS_TOKEN);
        return response;
    }
}
