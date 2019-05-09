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
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import ke.co.safaricom.www.entities.Movies;
import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.services.MoviesService;
import ke.co.safaricom.www.entities.services.UserService;
import ke.co.safaricom.www.swagger.SwaggerDocErrorResponseMessages;
import static ke.co.safaricom.www.utils.Constants.*;
import ke.co.safaricom.www.utils.ResponseWrapper;
import ke.co.safaricom.www.validators.MoviesValidator;
import ke.co.safaricom.www.validators.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author afro
 */

@Controller
@RequestMapping("/apis")
@Api(value = "Movies API endpoints.", description = "Movies API endpoints used to process User Registration Requests.")
public class MoviesController {

    private static final Logger logger = LogManager.getLogger(MoviesController.class);
    

    @Autowired
    private UserService userService;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MoviesController(UserService userService, MoviesService moviesService) {
        this.userService = userService;
        this.moviesService = moviesService;
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
    @PostMapping(value = "/register")
    private ResponseEntity<ResponseWrapper> registerUser(@RequestBody String userRegistrationRequest) {
        try {
            logger.info("REGISTRATION REQUEST RECEIVED.");
            UserValidator validate = new UserValidator();
            ResponseWrapper response = validate.validateUserRegistration(userRegistrationRequest, userService);
            
            if (validate.isIsValid()) {
                validate.getUsers().setPassword(bCryptPasswordEncoder.encode(validate.getUsers().getPassword()));
                String msidn = validate.formatMsisdn(validate.getUsers().getMsisdn());
                validate.getUsers().setMsisdn(msidn);
                Users savedUser = userService.saveUser(validate.getUsers());
                response.setObject(savedUser);
            }
            
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (IOException ex) {
            ResponseWrapper response = new ResponseWrapper();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(ITERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());            
            
            return new ResponseEntity<>(response, response.getHttpStatus());

        }

    }
    
    @ApiOperation(value = "Send Add Movie Request", notes = "Use this API to send a Movie Adding request. As specified in the documentation"
            + "note that some parameters are mandetory. Ensure that the mandetory parameters are provided and meet the specified parameter conditions. "
            + "NB: Mandetory parameters are highlighted with a red asterisk.")    
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful add movie request pushed for Processing.", response = Movies.class),
            @ApiResponse(code = 400, message = "Empty, invalid parametes noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 401, message = "Access token expired.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 404, message = "Access credentials provided are not valid.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 409, message = "Conflict, an existing Request Id noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 417, message = "Provided parameters, do not match specified dependencies.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/add")
    private ResponseEntity<ResponseWrapper> addMovie(@RequestBody String moviesRequest) {
        try {
            logger.info("MOVIE ADDITION REQUEST RECEIVED.");
            MoviesValidator validate = new MoviesValidator();
            ResponseWrapper response = validate.validateMovieRequest(moviesRequest, moviesService, POST);
            
            if (validate.isIsValid()) {
                
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetails user = (UserDetails) auth.getPrincipal();
                
                validate.getMovies().setAddDate(new Date());
                validate.getMovies().setTitle(validate.getMovies().getTitle().toUpperCase().trim());
                validate.getMovies().setAddedBy(user.getUsername());
                Movies savedMovie = moviesService.saveMovies(validate.getMovies());
                response.setObject(savedMovie);
            }
            
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (IOException ex) {
            ResponseWrapper response = new ResponseWrapper();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(ITERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());            
            
            return new ResponseEntity<>(response, response.getHttpStatus());
        }
    }
    
    @ApiOperation(value = "Send Update Movie Request", notes = "Use this API to send a Movie updating request. As specified in the documentation"
            + "note that some parameters are mandetory. Ensure that the mandetory parameters are provided and meet the specified parameter conditions. "
            + "NB: Mandetory parameters are highlighted with a red asterisk.")    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update movie request pushed for Processing.", response = Movies.class),
            @ApiResponse(code = 400, message = "Empty, invalid parametes noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 401, message = "Access token expired.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 404, message = "Access credentials provided are not valid.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 409, message = "Conflict, an existing Request Id noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 417, message = "Provided parameters, do not match specified dependencies.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PutMapping(value = "/update")
    private ResponseEntity<ResponseWrapper> updateMovie(@RequestBody String moviesRequest) {
        try {
            logger.info("MOVIE UPDATE REQUEST RECEIVED.");
            MoviesValidator validate = new MoviesValidator();
            ResponseWrapper response = validate.validateMovieRequest(moviesRequest, moviesService, PUT);
            
            if (validate.isIsValid()) {
                
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetails user = (UserDetails) auth.getPrincipal();
                
                validate.getMovies().setMovieId(validate.getMovies().getMovieId());
                validate.getMovies().setUpdatedDate(new Date());
                validate.getMovies().setTitle(validate.getMovies().getTitle().toUpperCase().trim());
                validate.getMovies().setUpdatedBy(user.getUsername());
                
                moviesService.saveMovies(validate.getMovies());
                
                response.setObject(moviesService.searchMovieByTitle(validate.getMovies().getTitle()));
                response.setHttpStatus(HttpStatus.OK);
                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage(OBJECT_UPDATED_RESPONSE_MESSAGE);
            }
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (IOException ex) {
            ResponseWrapper response = new ResponseWrapper();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(ITERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());            
            
            return new ResponseEntity<>(response, response.getHttpStatus());
        }
    }
    
    @ApiOperation(value = "Send Delete Movie Request", notes = "Use this API to send a Movie Delete request. As specified in the documentation"
            + "note that some parameters are mandetory. Ensure that the mandetory parameters are provided and meet the specified parameter conditions. "
            + "NB: Mandetory parameters are highlighted with a red asterisk.")    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update movie request pushed for Processing.", response = Movies.class),
            @ApiResponse(code = 400, message = "Empty, invalid parametes noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 401, message = "Access token expired.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 404, message = "Access credentials provided are not valid.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 409, message = "Conflict, an existing Request Id noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 417, message = "Provided parameters, do not match specified dependencies.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 500, message = "Internal server error") })
    @DeleteMapping(value = "/delete/{nameOfMovie}")
    private ResponseEntity<ResponseWrapper> deleteMovie(@PathVariable("nameOfMovie") String nameOfMovie) {
        logger.info("MOVIE UPDATE REQUEST RECEIVED.");
        MoviesValidator validate = new MoviesValidator();
        ResponseWrapper response = validate.validateMovieDeleteRequest(nameOfMovie, moviesService);

        if (validate.isIsValid()) {
              
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            
            Movies toDeleteMovie = validate.getMovies();
            toDeleteMovie.setDeleteDate(new Date());
            toDeleteMovie.setDeletedBy(user.getUsername());
            toDeleteMovie.setDeleted(true);
            
            moviesService.saveMovies(toDeleteMovie);
            
            response.setObject(toDeleteMovie);
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseCode(HttpStatus.OK.value());
            response.setResponseMessage(OBJECT_DELETE_RESPONSE_MESSAGE);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation(value = "Send Get Movie Request", notes = "Use this API to send a Get Movies request. As specified in the documentation"
            + "note that some parameters are mandetory. Ensure that the mandetory parameters are provided and meet the specified parameter conditions. "
            + "NB: Mandetory parameters are highlighted with a red asterisk.")    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful get movie request pushed for Processing.", response = Movies.class),
            @ApiResponse(code = 400, message = "Empty, invalid parametes noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 401, message = "Access token expired.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 404, message = "Access credentials provided are not valid.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 409, message = "Conflict, an existing Request Id noted.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 417, message = "Provided parameters, do not match specified dependencies.", response = SwaggerDocErrorResponseMessages.class),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/get/{filterValue}")
    private ResponseEntity<ResponseWrapper> fetchMovies(@PathVariable("filterValue") String filterValue) {
        logger.info("MOVIE GET REQUEST RECEIVED.");
        MoviesValidator validate = new MoviesValidator();
        ResponseWrapper response = validate.validateMovieRequest(filterValue.toUpperCase(), moviesService);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
}
