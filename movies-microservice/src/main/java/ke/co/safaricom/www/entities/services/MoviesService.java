/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.services;

import java.util.List;
import ke.co.safaricom.www.entities.Movies;

/**
 *
 * @author afro
 */
public interface MoviesService {
    
    List<Movies> searchMovies(boolean watched);
    
    List<Movies> searchMovies();
    
    Movies searchMovieByTitle(String titleOfMovie);
    
    Movies saveMovies(Movies movie);
    
}
