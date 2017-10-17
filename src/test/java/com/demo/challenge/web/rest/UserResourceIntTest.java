package com.demo.challenge.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.challenge.security.TokenProvider;
import com.demo.challenge.service.UserService;
import com.demo.challenge.web.rest.vm.UserLoginVM;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResourceIntTest {

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	private MockMvc restUserMockMvc;
	
    @Before
    public void setup() {
        UserResource userResource = new UserResource(tokenProvider, authenticationManager, userService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }
    
    @Test
    public void testLoginValid() throws Exception {
    	UserLoginVM userLoginVM = new UserLoginVM();
    	userLoginVM.setUsername("prashant");
    	userLoginVM.setDeviceType("laptop");

    	restUserMockMvc.perform(
            post("/api/user/login")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userLoginVM)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id_token").value("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFzaGFudCIsImF1dGgiOiJ1c2VyIiwiZXhwIjoxNTA4MTg4MTAzfQ.aoZDs_-ydB4cM9bArPaXfPwxYpMOMj_Z7Dnfs34Bafb1VDPCyhfSXvi56q8Ifwik2Bj4zsfLGqvhsHkOnWOR2g"));;
    }
    
    @Test
    public void testGetUserDetails() throws Exception {
        restUserMockMvc.perform(get("/api/user/details")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.user").value("prashant"))
            .andExpect(jsonPath("$.deviceType").value("laptop"));
    }
}
