package com.edmund.org.jobTitleNormaliser;

import com.edmund.org.jobTitleNormaliser.exceptions.InvalidInputException;
import com.edmund.org.jobTitleNormaliser.model.MatchingProperties;
import com.edmund.org.jobTitleNormaliser.similarityStrategies.CosineSimilarityStrategy;
import com.edmund.org.jobTitleNormaliser.model.JobDefinition;
import com.edmund.org.jobTitleNormaliser.model.JobProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Normaliser {

    private final List<JobDefinition> jobDefinitions;
    private final TitleMatcher matcher;
    private final double threshold;

    public Normaliser(JobProperties properties, MatchingProperties matchingProperties) {
        this.jobDefinitions = properties.jobs();
        this.threshold = matchingProperties.getThreshold();

        //Add additional strategies here
        this.matcher = new TitleMatcher(
                List.of(
                        new CosineSimilarityStrategy()
                )
        );

        log.debug("Loaded job definitions: {}", jobDefinitions);
    }

    public String normalise(String input) {
        if (input == null || input.isBlank()) {
            throw new InvalidInputException("Input job title cannot be null or empty");
        }

        String result = matcher.findBestMatch(input, jobDefinitions, threshold);

        if (result == null) {
            throw new InvalidInputException("Unable to determine job title: " + input);
        }

        return result;
    }
}



