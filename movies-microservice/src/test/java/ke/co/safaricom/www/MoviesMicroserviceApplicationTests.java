package ke.co.safaricom.www;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.co.safaricom.www.entities.Movies;
import ke.co.safaricom.www.entities.Users;
import ke.co.safaricom.www.entities.repositories.MoviesRepository;
import ke.co.safaricom.www.entities.repositories.UserRepository;
import ke.co.safaricom.www.entities.services.UserService;
import org.junit.Test;
import static ke.co.safaricom.www.testconstants.TestCasesConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoviesMicroserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();    
    
    Users user = new Users(MSISDN, PASSWORD);
    Movies movies = new Movies(MOVIE_TITLE.toUpperCase());

    @Autowired
    UserRepository usersRepo;

    @Autowired
    UserService usersService;

    @Autowired
    MoviesRepository moviesRepo;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        user.setFirstName("Amimo");
        user.setIdNo("26089909");
        user.setSecondName("Benja");
        
        Users savedUser = usersRepo.save(user);
        

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "devglan-client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic("devglan-client", "devglan-secret"))
                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void contextLoads() throws Exception {
        
        assertThat(user).isNotNull();
        assertThat(movies).isNotNull();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get(GET_ALL_MOVIES)).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenToken_whenPostGetSecureRequest_thenCreated() throws Exception {
        String accessToken = obtainAccessToken("254721506974", "password");

        String employeeString = "{\n"
                + "	\"firstName\":\"Nicholus\",\n"
                + "	\"secondName\":\"Owenje\",\n"
                + "	\"msisdn\":\"0725506974\",\n"
                + "	\"idNo\":\"26189909\",\n"
                + "	\"password\":\"SMTH\"\n"
                + "}";

        this.mockMvc.perform(post("/apis/register?access_token=" + accessToken).contentType(MediaType.APPLICATION_JSON)
                .content(employeeString)).andExpect(status().isCreated());        
        
        usersRepo.deleteById(usersService.searchUserByMsisdn(user.getMsisdn()).getUserId());
        usersRepo.deleteById(usersService.searchUserByMsisdn("254725506974").getUserId());
    }
    
    @Test
    public void givenToken_whenPostEmptyFieldsGetSecureRequest_thenBadRequest() throws Exception {
        String accessToken = obtainAccessToken("254721506974", "password");

        String employeeString = "{\n"
                + "	\"firstName\":\"\",\n"
                + "	\"secondName\":\"Owenje\",\n"
                + "	\"msisdn\":\"0725506974\",\n"
                + "	\"idNo\":\"26189909\",\n"
                + "	\"password\":\"SMTH\"\n"
                + "}";

        this.mockMvc.perform(post("/apis/register?access_token=" + accessToken).contentType(MediaType.APPLICATION_JSON)
                .content(employeeString)).andExpect(status().isBadRequest());
        
        usersRepo.deleteById(usersService.searchUserByMsisdn(user.getMsisdn()).getUserId());
    }

}
