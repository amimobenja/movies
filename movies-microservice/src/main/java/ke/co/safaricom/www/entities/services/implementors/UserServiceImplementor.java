/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.services.implementors;

import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.repositories.UserRepository;
import ke.co.safaricom.www.entities.services.UserService;
import static ke.co.safaricom.www.utils.Constants.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author afro
 */

@Transactional
@Service
public class UserServiceImplementor implements UserService {
    
    @Autowired
    private UserRepository userRepo;

    @Override
    public Users searchUser(String msisdn, String password) {
        return userRepo.findByMsisdnAndPasswordAndStatus(msisdn, password, ACTIVE);
    }

    @Override
    public Users searchUserByMsisdnOrIdNo(String msisdn, String idNo) {
        return userRepo.findByMsisdnOrIdNo(msisdn, idNo);
    }
        
    @Override
    public Users searchUserByMsisdn(String searchParameter) {
        return userRepo.findByMsisdn(searchParameter);
    }

    @Override
    public Users saveUser(Users user) {
        return userRepo.save(user);
    }

    
    
    
}
