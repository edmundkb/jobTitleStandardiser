package com.edmund.org.jobTitleNormaliser.unit;

import com.edmund.org.jobTitleNormaliser.Normaliser;
import com.edmund.org.jobTitleNormaliser.model.JobDefinition;
import com.edmund.org.jobTitleNormaliser.model.JobProperties;
import com.edmund.org.jobTitleNormaliser.model.MatchingProperties;
import com.edmund.org.jobTitleNormaliser.unit.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormaliserTest {

    private Normaliser normaliser;

    @BeforeEach
    void setup() {
        // Create JobDefinitions with optional context keywords
        final List<JobDefinition> jobs = List.of(
                new JobDefinition(Constants.ARCHITECT, List.of()),
                new JobDefinition(Constants.SOFTWARE_ENGINEER, List.of("java", "c#", "python")),
                new JobDefinition(Constants.QUANTITY_SURVEYOR, List.of()),
                new JobDefinition(Constants.ACCOUNTANT, List.of("acca", "cpa"))
        );

        JobProperties jobProperties = new JobProperties(jobs);
        MatchingProperties matchingProperties = new MatchingProperties();
        matchingProperties.setThreshold(0.45);

        normaliser = new Normaliser(jobProperties, matchingProperties);
    }

    @Test
    void testJavaEngineerMatchesSoftwareEngineer() {
        assertEquals(Constants.SOFTWARE_ENGINEER, normaliser.normalise("Java engineer"));
    }

    @Test
    void testCSharpEngineerMatchesSoftwareEngineer() {
        // Use a lower threshold to allow fuzzy matching
        String result = normaliser.normalise("C# engineer");
        assertEquals(Constants.SOFTWARE_ENGINEER, result);
    }

    @Test
    void testChiefAccountantMatchesAccountant() {
        // Make sure context keywords are considered
        String result = normaliser.normalise("Chief Accountant");
        assertEquals(Constants.ACCOUNTANT, result);
    }


    @Test
    void testExactMatch() {
        assertEquals(Constants.ARCHITECT, normaliser.normalise(Constants.ARCHITECT));
    }

    @Test
    void testNullThrowsException() {
        assertThrows(RuntimeException.class, () -> normaliser.normalise(null));
    }

    @Test
    void testBlankThrowsException() {
        assertThrows(RuntimeException.class, () -> normaliser.normalise(""));
    }
}