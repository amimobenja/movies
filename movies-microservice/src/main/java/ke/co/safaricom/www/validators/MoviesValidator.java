/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.validators;

import java.util.List;
import ke.co.safaricom.www.entities.Movies;
import ke.co.safaricom.www.entities.services.MoviesService;
import static ke.co.safaricom.www.utils.Constants.*;
import ke.co.safaricom.www.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author afro
 */
public class MoviesValidator {

    private static final Logger logger = LogManager.getLogger(MoviesValidator.class);
    private boolean isValid;
    private Movies movies;

    public ResponseWrapper validateMovieDeleteRequest(String nameOfMovie, MoviesService movieService) {
        ResponseWrapper response = new ResponseWrapper();

        Movies searchedMovie = movieService.searchMovieByTitle(nameOfMovie.toUpperCase().trim());
        if (searchedMovie == null) {
            logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.NOT_FOUND.value());

            return response;
        }
        setMovies(searchedMovie);
        setIsValid(true);
        
        return response;        

    }

    public ResponseWrapper validateMovieRequest(String allOrWatchedOrUnwatched, MoviesService movieService) {
        ResponseWrapper response = new ResponseWrapper();

        switch (allOrWatchedOrUnwatched) {
            case WATCHED:
                List<Movies> searchedMovies = movieService.searchMovies(MOVIE_WATCHED);
                if (searchedMovies.isEmpty()) {
                    logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
                    response.setHttpStatus(HttpStatus.NOT_FOUND);
                    response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                    response.setResponseCode(HttpStatus.NOT_FOUND.value());

                    return response;
                }
                return new ResponseWrapper(HttpStatus.OK.value(), FETCHED_RESPONSE_MESSAGE, HttpStatus.OK, searchedMovies);

            case UNWATCHED:
                List<Movies> searchedMoviesUnwatched = movieService.searchMovies(MOVIE_NOT_WATCHED);
                if (searchedMoviesUnwatched.isEmpty()) {
                    logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
                    response.setHttpStatus(HttpStatus.NOT_FOUND);
                    response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                    response.setResponseCode(HttpStatus.NOT_FOUND.value());

                    return response;
                }
                return new ResponseWrapper(HttpStatus.OK.value(), FETCHED_RESPONSE_MESSAGE, HttpStatus.OK, searchedMoviesUnwatched);
            case ALL:
                List<Movies> searchedMoviesAll = movieService.searchMovies();
                if (searchedMoviesAll.isEmpty()) {
                    logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
                    response.setHttpStatus(HttpStatus.NOT_FOUND);
                    response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                    response.setResponseCode(HttpStatus.NOT_FOUND.value());

                    return response;
                }
                return new ResponseWrapper(HttpStatus.OK.value(), FETCHED_RESPONSE_MESSAGE, HttpStatus.OK, searchedMoviesAll);

            default:
                logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                response.setResponseCode(HttpStatus.NOT_FOUND.value());

                return response;

        }

    }

    public ResponseWrapper validateMovieRequest(Movies movies, MoviesService movieService, String httpMethod) {
        logger.debug(" - Starting the validation process.");
        ResponseWrapper response = new ResponseWrapper();
        if (movies.getDescription().isEmpty() || movies.getMovieOrSeries().isEmpty() || movies.getRecommendation().isEmpty()
                || movies.getTitle().isEmpty()) {
            logger.debug(" - {}", EMPTY_PARAMS_RESPONSE_MESSAGE);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(EMPTY_PARAMS_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());

            return response;
        }

        if ((MIN < movies.getRating() && movies.getRating() < MAX) == false) {
            logger.debug(" - Not within rating range MAX - {} MIN - {}", MIN, MAX);
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setResponseMessage(EXPECTATION_FAILED_RESPONSE_MESSAGE);
            response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());

            return response;
        }

        Movies searchedMovie = movieService.searchMovieByTitle(movies.getTitle().toUpperCase().trim());

        if (httpMethod.equals(POST)) {
            if (searchedMovie != null) {
                logger.debug(" - {}", CONFLICT_RESPONSE_MESSAGE);
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setResponseMessage(CONFLICT_RESPONSE_MESSAGE);
                response.setResponseCode(HttpStatus.CONFLICT.value());

                return response;
            }
        }

        if (httpMethod.equals(PUT)) {
            if (searchedMovie == null) {
                logger.debug(" - {}", NOT_FOUND_RESPONSE_MESSAGE);
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(NOT_FOUND_RESPONSE_MESSAGE);
                response.setResponseCode(HttpStatus.NOT_FOUND.value());

                return response;
            }
            setMovies(searchedMovie);
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

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

}
