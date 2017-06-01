package su.vistar.client.model;


public class ErrorResponse {
    String errorResponse;

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }    
}
