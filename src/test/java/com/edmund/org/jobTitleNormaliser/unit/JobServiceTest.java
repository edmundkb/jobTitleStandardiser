package com.edmund.org.jobTitleNormaliser.unit;

import com.edmund.org.jobTitleNormaliser.JobService;
import com.edmund.org.jobTitleNormaliser.Normaliser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    private static final String INPUT = "Title";

    @Mock
    private Normaliser normaliser;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setup() {
        when(normaliser.normalise(INPUT)).thenReturn("Normalised Title");
    }

    @Test
    void testNormaliseTitleReturnsCorrectValue() {
        String result = jobService.normaliseTitle(INPUT);

        assertNotNull(result, "Result should not be null");
        assertTrue(StringUtils.isNotBlank(result), "Result should not be blank");
        assertEquals("Normalised Title", result, "Result should match the normalised title");

        // Verify the mock was called exactly once
        verify(normaliser, times(1)).normalise(INPUT);
    }
}

