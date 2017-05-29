
package su.vistar.client.dto;


public class ResponseObjectDTO {
    private String message;
    private Object responseObject;

    public ResponseObjectDTO(String message, Object responseObject) {
        this.message = message;
        this.responseObject = responseObject;
    }

    public ResponseObjectDTO(Object responseObject) {
        this.responseObject = responseObject;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
    
}
