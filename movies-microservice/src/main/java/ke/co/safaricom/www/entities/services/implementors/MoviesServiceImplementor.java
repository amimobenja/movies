/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.services.implementors;

import java.util.List;
import ke.co.safaricom.www.entities.Movies;
import ke.co.safaricom.www.entities.repositories.MoviesRepository;
import ke.co.safaricom.www.entities.services.MoviesService;
import static ke.co.safaricom.www.utils.Constants.NOT_DELETED;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author afro
 */
@Transactional
@Service
public class MoviesServiceImplementor implements MoviesService {
    
    @Autowired
    private MoviesRepository moviesRepo;

    @Override
    public List<Movies> searchMovies(boolean watched) {
        return moviesRepo.findAllByWatchedAndDeleted(watched, NOT_DELETED);
    }

    @Override
    public List<Movies> searchMovies() {
        return moviesRepo.findAllByDeleted(NOT_DELETED);
    }    

    @Override
    public Movies saveMovies(Movies movie) {
        return moviesRepo.save(movie);
    }

    @Override
    public Movies searchMovieByTitle(String titleOfMovie) {
        return moviesRepo.findByTitle(titleOfMovie);
    }
    
}
