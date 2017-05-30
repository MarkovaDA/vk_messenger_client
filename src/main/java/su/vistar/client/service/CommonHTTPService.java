package su.vistar.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.springframework.stereotype.Service;

@Service
public class CommonHTTPService {
    String ACCESS_TOKEN = "d673ddf6d978aeb273f5729889e02aa390b7274a893198870295640db9c8ef7add4e7d01d73fbeb658ac7";
    
    //сделать GET-запрос и вернуть ответ в виде строки
    String doPureGETQuery(String query) throws MalformedURLException, ProtocolException, IOException {
        URL obj = new URL(query);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        String response = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = readResponse(connection);
        }
        return response;
    }
    
    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
