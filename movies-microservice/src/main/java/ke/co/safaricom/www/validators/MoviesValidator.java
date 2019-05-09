/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.validators;

import ke.co.safaricom.www.entities.Movies;
import ke.co.safaricom.www.entities.services.MoviesService;
import static ke.co.safaricom.www.utils.Constants.*;
import ke.co.safaricom.www.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author afro
 */
public class MoviesValidator {
    
    private static final Logger logger = LogManager.getLogger(MoviesValidator.class);
    private boolean isValid;
    
    public ResponseWrapper validateMovieRequest(Movies movies, MoviesService movieService, String httpMethod) { 
        logger.debug(" - Starting the validation process.");       
        ResponseWrapper response = new ResponseWrapper();
         if (movies.getDescription().isEmpty() || movies.getMovieOrSeries().isEmpty() || movies.getRecommendation().isEmpty() 
                || movies.getTitle().isEmpty()) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(EMPTY_PARAMS_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());

            return response;        
        }
        
        Movies searchedMovie = movieService.searchMovieByTitle(movies.getTitle());
         
        if (httpMethod.equals(POST)) {
            if (searchedMovie != null) {
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setResponseMessage(CONFLICT_RESPONSE_MESSAGE);
                response.setResponseCode(HttpStatus.CONFLICT.value());            
            }
        }
        if (httpMethod.equals(POST)) {
            if (searchedMovie == null) {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                response.setResponseCode(HttpStatus.NOT_FOUND.value());            
            }
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
    
    
}
