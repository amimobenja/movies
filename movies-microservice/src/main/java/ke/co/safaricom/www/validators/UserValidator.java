/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.services.UserService;
import static ke.co.safaricom.www.utils.Constants.*;
import ke.co.safaricom.www.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author afro
 */


public class UserValidator {
    
    private static final Logger logger = LogManager.getLogger(UserValidator.class);
    private boolean isValid;
    
    public ResponseWrapper validateUserRegistration(Users users, UserService userService) { 
        logger.debug(" - Starting the validation process.");       
        ResponseWrapper response = new ResponseWrapper();
        
        String msidn = formatMsisdn(users.getMsisdn());
        if (msidn.equals("")) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(BAD_PARAMS_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());

            return response;
        } 

        if (users.getFirstName().isEmpty() || users.getIdNo().isEmpty() || users.getPassword().isEmpty() || users.getSecondName().isEmpty()) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(EMPTY_PARAMS_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());

            return response;
        }        
        
        Users existingUser = userService.searchUser(msidn, users.getIdNo());
        
        if (existingUser != null) {
            response.setHttpStatus(HttpStatus.CONFLICT);
            response.setResponseMessage(CONFLICT_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.CONFLICT.value());

            return response;
        }
        
        logger.debug(" - Entity Object is valid.");
        setIsValid(true);
        response.setHttpStatus(HttpStatus.CREATED);
        response.setResponseMessage(OBJECT_CREATED_RESPONSE_MESSAGE);
        response.setResponseCode(HttpStatus.CREATED.value());

        return response;    
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    
    private boolean isValidInput(String inputStr, String expression) {
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    
    public String formatMsisdn(String msisdn) {
        String formatedMsisdn = "";
        if (isValidInput(msisdn, REGEX_EXPRESSION_I)) {
            formatedMsisdn = msisdn;
        } else if (isValidInput(msisdn, REGEX_EXPRESSION_II)) {
            formatedMsisdn = msisdn.replace("+", "");
        } else if (isValidInput(msisdn, REGEX_EXPRESSION_III)) {
            formatedMsisdn = String.valueOf(msisdn.charAt(0)).equals("0") ? msisdn.replaceFirst("0", "254") : msisdn;
        } else if (isValidInput(msisdn, REGEX_EXPRESSION_IV)) {
            formatedMsisdn = "254" + msisdn;
        }
        return formatedMsisdn;
    }
    
    
}
