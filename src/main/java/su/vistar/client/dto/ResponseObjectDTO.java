
package su.vistar.client.dto;


public class ResponseObjectDTO {
    private String serverMessage;
    private Object responseObject;

    public ResponseObjectDTO(String message, Object responseObject) {
        this.serverMessage = message;
        this.responseObject = responseObject;
    }

    public ResponseObjectDTO(Object responseObject) {
        this.responseObject = responseObject;
    }
    
    
    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
    
}
