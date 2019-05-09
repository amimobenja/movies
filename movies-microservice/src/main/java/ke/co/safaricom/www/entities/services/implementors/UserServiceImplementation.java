/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.services.implementors;

import java.util.Arrays;
import java.util.List;
import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author baamimo
 */
@Service(value = "userService")
public class UserServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String msisdn) throws UsernameNotFoundException {
        Users user = userRepo.findByMsisdn(msisdn);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getMsisdn(), user.getPassword(), authorities);
    }

}
