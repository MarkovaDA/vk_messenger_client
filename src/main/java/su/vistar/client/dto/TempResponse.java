/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.vistar.client.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Darya
 */
public class TempResponse {
     
    @SerializedName("response")
    Response response;

    public Response getResponse() {
        return response;
    }
    
    public void setResponse(Response response) {
        this.response = response;
    }
    
    public class Response {
        
        @SerializedName("count")
        public int count;//кол-во человек по заданному критерию
        
        @SerializedName("items")
        public List<VKObjectDTO> items;

        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public List<VKObjectDTO> getItems() {
            return items;
        }
        public void setItems(List<VKObjectDTO> items) {
            this.items = items;
        }        
    }
}
