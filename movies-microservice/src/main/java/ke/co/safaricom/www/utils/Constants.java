/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.utils;

/**
 *
 * @author afro
 */
public class Constants {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    // Default server response messages
    public static final String DEFAULT_RESPONSE_MESSAGE = "Request has been accepted by the server.";
    public static final String BAD_PARAMS_RESPONSE_MESSAGE = "Unrecognized parameters passed.";
    public static final String EMPTY_PARAMS_RESPONSE_MESSAGE = "Empty parameters values noted.";
    public static final String NOT_FOUND_RESPONSE_MESSAGE = "Not found.";
    public static final String CONFLICT_RESPONSE_MESSAGE = "Parameter values are being used.";
    public static final String OBJECT_CREATED_RESPONSE_MESSAGE = "Successfully created.";
    public static final String PASSWORD_ENCRYPTION_RESPONSE_ERROR = "Password encryption error.";
    
    
    // HTTP Methods
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    
    //User Registration Status
    public static final boolean ACTIVE = true;
    public static final boolean NOT_ACTIVE = false;
    
    //Movies Deletion Status
    public static final boolean DELETED = true;
    public static final boolean NOT_DELETED = false;
            
    // Regex Expression
    public static final String REGEX_EXPRESSION_I = "^([2][5][4][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]){1,12}$";
    public static final String REGEX_EXPRESSION_II = "^([+][2][5][4][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]){1,13}$";
    public static final String REGEX_EXPRESSION_III = "^([0][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]){1,10}$";
    public static final String REGEX_EXPRESSION_IV = "^([0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]){1,9}$";


}
