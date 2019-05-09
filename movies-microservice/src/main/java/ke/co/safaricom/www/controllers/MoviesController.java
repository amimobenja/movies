/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.services.UserService;
import ke.co.safaricom.www.swagger.SwaggerDocErrorResponseMessages;
import ke.co.safaricom.www.utils.ResponseWrapper;
import ke.co.safaricom.www.validators.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author afro
 */

@Controller
@Api(value = "Movies API endpoints.", description = "Movies API endpoints used to process User Registration Requests.")
public class MoviesController {

    private static final Logger logger = LogManager.getLogger(MoviesController.class);
    

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MoviesController(UserService userService) {
        this.userService = userService;
    }
    
    @ApiOperation(value = "Send a User Request", notes = "Use this API to send a User Registration request. As specified in the documentation"
            + "note that some parameters are mandetory. Ensure that the mandetory parameters are provided and meet the specified parameter conditions. "
            + "NB: Mandetory parameters are highlighted with a red asterisk.")    
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful user registration Request pushed for Processing.", response = Users.class),
            @ApiResponse(code = 400, message = "Empty, invalid parametes noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 404, message = "Access credentials provided are not valid.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 409, message = "Conflict, an existing Request Id noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 417, message = "Provided parameters, do not match specified dependencies.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/client")
    private ResponseEntity<ResponseWrapper> registerUser(@RequestBody Users userRegistrationRequest) {
        logger.info("REGISTRATION REQUEST RECEIVED.");
        UserValidator validate = new UserValidator();
        ResponseWrapper response = validate.validateUserRegistration(userRegistrationRequest, userService);

        if (validate.isIsValid()) {
            userRegistrationRequest.setPassword(bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()));
            String msidn = validate.formatMsisdn(userRegistrationRequest.getMsisdn());
            userRegistrationRequest.setMsisdn(msidn);
            Users savedUser = userService.saveUser(userRegistrationRequest);
            response.setObject(savedUser);
        }

        return new ResponseEntity<>(response, response.getHttpStatus());

    }
    
}
