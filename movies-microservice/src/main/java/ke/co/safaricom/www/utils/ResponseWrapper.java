/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.utils;

import java.io.Serializable;
import static ke.co.safaricom.www.utils.Constants.DEFAULT_RESPONSE_MESSAGE;
import org.springframework.http.HttpStatus;

/**
 *
 * @author afro
 */
public class ResponseWrapper implements Serializable {
    
    private int responseCode;
    private String responseMessage;
    private HttpStatus httpStatus;
    private Object object;

    public ResponseWrapper() {
        this.responseCode = 202;
        this.responseMessage = DEFAULT_RESPONSE_MESSAGE;
        this.httpStatus = HttpStatus.ACCEPTED;
        this.object = "{}";
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    
}
