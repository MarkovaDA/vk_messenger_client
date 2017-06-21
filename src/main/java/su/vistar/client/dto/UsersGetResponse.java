package su.vistar.client.dto;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;



public class UsersGetResponse {
    
    @SerializedName("response")
    ArrayList<VKUserDTO> response;

    public ArrayList<VKUserDTO> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<VKUserDTO> response) {
        this.response = response;
    }
    
}
