/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author afro
 */
@Entity
@ApiModel(description = "Class representing a User Entity Body parameters.")
public class Users  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The userId parameter that is not mandatory, as the API will generate it.", required = false)
    private int userId;
    @NotNull(message = "The firstName cannot be empty.")
    @ApiModelProperty(notes = "The firstName parameter is mandatory as it's used to identify a user. "
            , required = true, example = "Njoroge")
    private String firstName;
    @NotNull(message = "The secondName cannot be empty.")
    @ApiModelProperty(notes = "The secondName parameter is mandatory as it's used to identify a user. "
            , required = true, example = "Wafula")
    private String secondName;
    @NotNull(message = "The msisdn cannot be empty.")
    @ApiModelProperty(notes = "The msisdn parameter is mandatory as it's used to uniquely identify a user. "
            , required = true, example = "254721506974")
    private String msisdn;
    @NotNull(message = "The idNo cannot be empty.")
    @ApiModelProperty(notes = "The idNo parameter is mandatory as it's used to uniquely identify a user. "
            , required = true, example = "254721506974")
    private String idNo;
    @NotNull(message = "The password cannot be empty.")
    @ApiModelProperty(notes = "The idNo parameter is mandatory as it's used to authenticate a user. "
            , required = true, example = "smth")
    private String password;
    @ApiModelProperty(notes = "The status parameter that is not mandatory, as the API will generate it.", required = false)
    private boolean status;
        
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date registrationDate;

    public Users() {
        this.status = true;
        this.registrationDate = new Date();
    }

    public Users(String msisdn, String password) {
        this.msisdn = msisdn;
        this.password = password;
        this.status = true;
        this.registrationDate = new Date();
    }    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    
    
    
}
