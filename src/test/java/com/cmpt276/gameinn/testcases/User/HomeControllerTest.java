package com.cmpt276.gameinn.testcases.User;

// import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebSecurity
@SpringBootTest(classes = HomeControllerTest.class)
public class HomeControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void ShouldBeUnauthorizedWithoutPassword() throws Exception {
        mockMvc.perform(get("/")
        .param("email", "b@b.com"))
        .andExpect(status().isUnauthorized());
    }

    private String obtainAccessToken(String username, String password) throws Exception {
 
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "fooClientIdPassword");
        params.add("username", username);
        params.add("password", password);
    
        ResultActions result = mockMvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("fooClientIdPassword","secret"))
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));
    
        String resultString = result.andReturn().getResponse().getContentAsString();
    
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
