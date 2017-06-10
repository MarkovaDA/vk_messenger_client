/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.vistar.client.dto;

/**
 *
 * @author Darya
 */
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
