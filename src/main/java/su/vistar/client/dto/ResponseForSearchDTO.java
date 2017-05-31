
package su.vistar.client.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ResponseForSearchDTO{
    
    String message;
    
    @SerializedName("response")
    Response response;

    public Response getResponse() {
        return response;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    
    public class Response {
        @SerializedName("count")
        public int count;//кол-во человек по заданному критерию
        
        @SerializedName("items")
        public List<VKUserDTO> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<VKUserDTO> getItems() {
            return items;
        }

        public void setItems(List<VKUserDTO> items) {
            this.items = items;
        }
        
    }
}
