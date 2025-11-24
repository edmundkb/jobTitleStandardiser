package com.edmund.org.jobTitleNormaliser.similarityStrategies;

import java.util.List;

public interface SimilarityStrategy {
    double score(String input, String target);

    default double scoreWithContext(String input, String target, List<String> contextKeywords) {
        return score(input, target);
    }
}
