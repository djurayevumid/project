package com.javamentor.qa.platform.api.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.authentication.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.dto.token.TokenResponseDto;
import com.javamentor.qa.platform.security.jwt.JwtService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringBootTest(classes = JmApplication.class)
@TestPropertySource(properties = {"spring.config.location = src/test/resources/application-test.properties"})
@DBUnit(caseSensitiveTableNames = true, cacheConnection = false, allowEmptyFields = true)
@AutoConfigureMockMvc
public abstract class AbstractTestApi {
    private static final String AUTH_URI = "/api/auth/token";

    @PersistenceContext
    protected EntityManager em;
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected JwtService jwtService;

    private static boolean cleaned = false;

    public String getToken(String username, String password) throws Exception {
        AuthenticationRequestDto authDto = new AuthenticationRequestDto(username, password,false);
        TokenResponseDto token = objectMapper.readValue(mvc
                .perform(post(AUTH_URI).content(objectMapper.writeValueAsString(authDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), TokenResponseDto.class);

        return token.getToken();
    }

    public void cleaningCache() {
        if (!cleaned) {
            cacheManager.getCacheNames().stream().forEach(name -> cacheManager.getCache(name).clear());
            cleaned = true;
        }
    }

}
