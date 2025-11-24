package com.edmund.org.jobTitleNormaliser;

import com.edmund.org.jobTitleNormaliser.similarityStrategies.SimilarityStrategy;
import com.edmund.org.jobTitleNormaliser.model.JobDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TitleMatcher {

    private final List<SimilarityStrategy> strategies;

    public TitleMatcher(List<SimilarityStrategy> strategies) {
        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalArgumentException("At least one similarity strategy is required");
        }
        this.strategies = strategies;
    }

    public String findBestMatch(String input,
                                List<JobDefinition> jobDefinitions,
                                double threshold) {
        if (input == null || input.isBlank() || jobDefinitions == null || jobDefinitions.isEmpty()) {
            return null;
        }

        String bestTitle = null;
        double bestScore = -1.0;

        for (JobDefinition job : jobDefinitions) {
            double score = averageScore(input, job);

            // Boost: if input contains target title, treat as perfect match
            if (containsBoost(input, job.title())) {
                score = 1.0;
            }

            if (score > bestScore) {
                bestScore = score;
                bestTitle = job.title();
            }
        }
        log.debug(String.format("Best score: %s", bestScore));

        return bestScore >= threshold ? bestTitle : null;
    }

    private double averageScore(String input, JobDefinition job) {
        if (job == null) return 0.0;

        return strategies.stream()
                .mapToDouble(s -> s.scoreWithContext(
                        input,
                        job.title(),
                        job.contextKeywords() != null ? job.contextKeywords() : List.of()
                ))
                .average()
                .orElse(0.0);
    }

    /**
     * Boost score if input contains title or title contains input
     */
    private boolean containsBoost(String input, String title) {
        String lowerInput = input.toLowerCase();
        String lowerTitle = title.toLowerCase();
        return lowerInput.contains(lowerTitle) || lowerTitle.contains(lowerInput);
    }
}