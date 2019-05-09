/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;

/**
 *
 * @author afro
 */
@ApiModel(description = "Class representing an STK Push Error Response Body parameters.")
public class SwaggerDocErrorResponseMessages {
    @Column(name = "response_code")
    @ApiModelProperty(notes = "Mandetory responseCode parameter will be provided in the response", required = true, example = "400")
    private String responseCode;
    @Column(name = "response_message")
    @ApiModelProperty(notes = "Mandetory responseMessage parameter will be provided in the response", required = true, 
            example = "Top up failed. Please check your entry and try again.")
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    
    
}
