/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.repositories;

import ke.co.safaricom.www.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author afro
 */
public interface UserRepository extends JpaRepository<Users, Integer> {
    
    Users findByMsisdnAndPasswordAndStatus(String msisdn, String password, boolean status);
    
    Users findByMsisdnOrIdNo(String msisdn, String idNo);
    
    Users findByMsisdn(String searchParameter);
    
}
