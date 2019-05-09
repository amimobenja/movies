/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *
 * @author baamimo
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {    
    
    @Value("${app.oauth2.params.client.id}")
    private String CLIEN_ID;
    @Value("${app.oauth2.params.client.secret}")
    private String CLIENT_SECRET; //    static final String CLIENT_SECRET = "devglan-secret";
    @Value("${app.oauth2.params.grant_type}")
    private String GRANT_TYPE_PASSWORD;
    @Value("${app.oauth2.params.authorization_code}")
    private String AUTHORIZATION_CODE;
    @Value("${app.oauth2.params.refresh_token}")
    private String REFRESH_TOKEN;    
    @Value("${app.oauth2.params.implicit}")
    private String IMPLICIT;
    @Value("${app.oauth2.params.scope.read}")
    private String SCOPE_READ;
    @Value("${app.oauth2.params.scope.write}")
    private String SCOPE_WRITE;
    @Value("${app.oauth2.params.trust}")
    private String TRUST;
    @Value("${app.oauth2.params.token_validity.access}")
    private int ACCESS_TOKEN_VALIDITY_SECONDS;
    @Value("${app.oauth2.params.token_validity.refresh}")
    private int FREFRESH_TOKEN_VALIDITY_SECONDS;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer
                .inMemory()
                .withClient(CLIEN_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }
}
