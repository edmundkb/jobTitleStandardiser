package com.edmund.org.jobTitleNormaliser.unit;

import com.edmund.org.jobTitleNormaliser.similarityStrategies.CosineSimilarityStrategy;
import com.edmund.org.jobTitleNormaliser.TitleMatcher;
import com.edmund.org.jobTitleNormaliser.model.JobDefinition;
import com.edmund.org.jobTitleNormaliser.unit.util.Constants;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleMatcherTest {

    @Test
    void testFindBestMatch() {

        TitleMatcher matcher = new TitleMatcher(
                List.of(new CosineSimilarityStrategy())
        );

        List<JobDefinition> jobs = List.of(
                new JobDefinition(Constants.ARCHITECT, List.of()),
                new JobDefinition(Constants.SOFTWARE_ENGINEER, List.of("java", "c#", "python")),
                new JobDefinition(Constants.ACCOUNTANT, List.of())
        );

        String input = "C# engineer";

        // Run matcher with a lower threshold for fuzzy matching
        String result = matcher.findBestMatch(input, jobs, 0.3);

        // Assert
        assertEquals(Constants.SOFTWARE_ENGINEER, result);
    }
}