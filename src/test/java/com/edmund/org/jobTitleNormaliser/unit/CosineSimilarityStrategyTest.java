package com.edmund.org.jobTitleNormaliser.unit;

import com.edmund.org.jobTitleNormaliser.similarityStrategies.CosineSimilarityStrategy;
import com.edmund.org.jobTitleNormaliser.unit.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CosineSimilarityStrategyTest {

    private CosineSimilarityStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new CosineSimilarityStrategy();
    }

    @Test
    void identicalStringsReturnOne() {
        double score = strategy.score(Constants.SOFTWARE_ENGINEER, Constants.SOFTWARE_ENGINEER);
        assertEquals(1.0, score, 0.0001);
    }

    @Test
    void similarStringsReturnHighScore() {
        double score = strategy.score(Constants.JAVA_ENGINEER, Constants.SOFTWARE_ENGINEER);
        assertTrue(score > 0.3, "Expected similarity > 0.3 but was " + score);
    }

    @Test
    void completelyDifferentStringsReturnLowScore() {
        double score = strategy.score(Constants.ARCHITECT, Constants.ACCOUNTANT);
        assertTrue(score < 0.2, "Expected similarity < 0.2 but was " + score);
    }

    @Test
    void comparisonCaseInsensitive() {
        double score1 = strategy.score("Software Engineer", Constants.SOFTWARE_ENGINEER);
        double score2 = strategy.score(Constants.SOFTWARE_ENGINEER, "SOFTWARE ENGINEER");

        assertEquals(1.0, score1, 0.0001);
        assertEquals(1.0, score2, 0.0001);
    }

    @Test
    void scoreSymmetric() {
        double a = strategy.score(Constants.JAVA_ENGINEER, Constants.SOFTWARE_ENGINEER);
        double b = strategy.score(Constants.SOFTWARE_ENGINEER, Constants.JAVA_ENGINEER);

        assertEquals(a, b, 0.0001);
    }

    @Test
    void emptyStringsShouldReturnZero() {
        assertEquals(0.0, strategy.score(Constants.EMPTY, Constants.EMPTY), 0.0001);
        assertEquals(0.0, strategy.score("engineer", Constants.EMPTY), 0.0001);
        assertEquals(0.0, strategy.score(Constants.EMPTY, Constants.ARCHITECT), 0.0001);
    }

    @Test
    void nullInputsShouldReturnZero() {
        assertEquals(0.0, strategy.score(null, "engineer"), 0.0001);
        assertEquals(0.0, strategy.score("engineer", null), 0.0001);
    }

    @Test
    void contextKeywordsImproveScore() {
        double withoutContext = strategy.scoreWithContext(
                Constants.JAVA_DEVELOPER,
                Constants.SOFTWARE_ENGINEER,
                List.of()
        );

        double withContext = strategy.scoreWithContext(
                Constants.JAVA_DEVELOPER,
                Constants.SOFTWARE_ENGINEER,
                List.of("java", "python", "c#")
        );

        assertTrue(withContext > withoutContext,
                "Expected context to increase score");
    }
}

