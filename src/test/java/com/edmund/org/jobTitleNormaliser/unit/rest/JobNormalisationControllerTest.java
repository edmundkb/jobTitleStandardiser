package com.edmund.org.jobTitleNormaliser.unit.rest;

import com.edmund.org.jobTitleNormaliser.JobService;
import com.edmund.org.jobTitleNormaliser.exceptions.InvalidInputException;
import com.edmund.org.jobTitleNormaliser.rest.JobNormalisationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobNormalisationController.class)
class JobNormalisationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobService jobService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        JobService jobService() {
            return mock(JobService.class);
        }
    }

    @BeforeEach
    void setup() {
        when(jobService.normaliseTitle("java engineer")).thenReturn("software engineer");
    }

    @Test
    void testNormaliseEndpointReturnsExpectedJson() throws Exception {
        mockMvc.perform(get("/api/v1/jobs/normalise")
                        .param("title", "java engineer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input").value("java engineer"))
                .andExpect(jsonPath("$.normalised").value("software engineer"));
    }

    @Test
    void testNormaliseEndpointReturnsBadRequestForInvalidInput() throws Exception {
        // Simulate the service throwing InvalidInputException for blank input
        when(jobService.normaliseTitle("")).thenThrow(new InvalidInputException("Input cannot be empty"));

        mockMvc.perform(get("/api/v1/jobs/normalise")
                        .param("title", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Input cannot be empty"));
    }

    @Test
    void testNormaliseEndpointReturnsInternalServerErrorForGenericException() throws Exception {
        // Simulate a runtime exception
        when(jobService.normaliseTitle("java engineer")).thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/api/v1/jobs/normalise")
                        .param("title", "java engineer"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
