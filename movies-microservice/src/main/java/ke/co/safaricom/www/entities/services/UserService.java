/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities.services;

import ke.co.safaricom.www.entities.Users;

/**
 *
 * @author afro
 */
public interface UserService {
    
    Users searchUser(String msisdn, String password);
    
    Users searchUserByMsisdnOrIdNo(String msisdn, String idNo);
    
    Users searchUserByMsisdn(String searchParameter);
    
    Users saveUser(Users user);
    
}
