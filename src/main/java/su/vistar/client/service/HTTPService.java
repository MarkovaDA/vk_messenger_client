package su.vistar.client.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import su.vistar.client.dto.VKObjectDTO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HTTPService {
    
    @Autowired
    private CommonHTTPService commonService;
    String doPureGetQuery(String query) throws ProtocolException, IOException {
        return commonService.doPureGETQuery(query);
    }
    protected String doPOSTQuery(String baseQuery, Map<String, String> params) throws MalformedURLException, UnsupportedEncodingException, ProtocolException, IOException {
        
        URL url = new URL(baseQuery);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        return commonService.readResponse(conn);
    }
    protected List<VKObjectDTO> doGETQuery(String query) throws MalformedURLException, ProtocolException, IOException {
        Gson gson = new Gson();
        Type responseType = new TypeToken<ArrayList<VKObjectDTO>>() {
        }.getType();
        String response = commonService.doPureGETQuery(query);
        int startIndex = response.lastIndexOf("items") + "items".length() + 1;
        response = response.substring(startIndex + 1, response.length() - 2);
        return gson.fromJson(response, responseType);
    }
}
