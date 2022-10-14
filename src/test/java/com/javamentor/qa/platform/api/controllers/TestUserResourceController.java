package com.javamentor.qa.platform.api.controllers;

import com.javamentor.qa.platform.api.abstracts.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;



public class TestUserResourceController extends AbstractTestApi {


    @Test
    @Sql(value = {"/script/before_getUserProfileQuestionDto.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/after_getUserProfileQuestionDto.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("user@mail.ru")
    public void getUserProfileQuestionDto() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(getToken("user@mail.ru", "111"));
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8091/api/user/profile/questions")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Sql(value = {"/script/before_getTop10UsersByAnswer.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/after_getUserProfi   leQuestionDto.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getTop10UsersByAnswers() throws Exception {

    }

}
