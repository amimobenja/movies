/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.repositories;

import java.util.List;
import ke.co.safaricom.www.entities.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author afro
 */
public interface MoviesRepository extends JpaRepository<Movies, Integer>{
    List<Movies> findAllByWatchedAndDeleted(boolean watched, boolean deleted);
    
    List<Movies> findAllByDeleted(boolean deleted);
    
    Movies findByTitle(String title);
}
