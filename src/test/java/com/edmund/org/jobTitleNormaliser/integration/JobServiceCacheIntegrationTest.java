package com.edmund.org.jobTitleNormaliser.integration;

import com.edmund.org.jobTitleNormaliser.JobService;
import com.edmund.org.jobTitleNormaliser.Normaliser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@EnableCaching
class JobServiceCacheIntegrationTest {

    @TestConfiguration
    static class MockConfig {

        @Bean
        Normaliser normaliser() {
            return mock(Normaliser.class);
        }
    }

    @Autowired
    private Normaliser normaliser;

    @Autowired
    private JobService jobService;

    @Test
    void testCacheHits() {

        when(normaliser.normalise("java engineer"))
                .thenReturn("software engineer");

        jobService.normaliseTitle("java engineer"); // miss
        jobService.normaliseTitle("java engineer"); // hit

        verify(normaliser, times(1)).normalise("java engineer");
    }
}

