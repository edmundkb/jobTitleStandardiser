package com.edmund.org.jobTitleNormaliser.similarityStrategies;

import java.util.*;

public class CosineSimilarityStrategy implements SimilarityStrategy {

    @Override
    public double score(String input, String target) {
        return scoreWithContext(input, target, List.of());
    }

    @Override
    public double scoreWithContext(String input, String target, List<String> contextKeywords) {
        if (input == null || input.isBlank() || target == null || target.isBlank()) {
            return 0.0;
        }

        // Tokenize input
        List<String> inputTokens = tokenize(input);

        // Tokenize target and include context keywords
        List<String> targetTokens = new ArrayList<>(tokenize(target));
        if (contextKeywords != null && !contextKeywords.isEmpty()) {
            targetTokens.addAll(contextKeywords.stream()
                    .map(this::normalizeToken)
                    .toList());
        }

        return cosine(inputTokens, targetTokens);
    }

    private List<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        // Keep letters, numbers, and programming symbols (#, +)
        return Arrays.stream(text.toLowerCase().split("[^\\p{L}\\p{N}#+]+"))
                .filter(s -> !s.isBlank())
                .toList();
    }

    private String normalizeToken(String token) {
        return token == null ? "" : token.toLowerCase().trim();
    }

    private double cosine(List<String> a, List<String> b) {
        Map<String, Integer> freqA = frequency(a);
        Map<String, Integer> freqB = frequency(b);

        Set<String> allTokens = new HashSet<>();
        allTokens.addAll(freqA.keySet());
        allTokens.addAll(freqB.keySet());

        double dot = 0.0;
        for (String token : allTokens) {
            dot += freqA.getOrDefault(token, 0) * freqB.getOrDefault(token, 0);
        }

        double magA = magnitude(freqA.values());
        double magB = magnitude(freqB.values());

        return (magA == 0 || magB == 0) ? 0.0 : dot / (magA * magB);
    }

    private Map<String, Integer> frequency(List<String> tokens) {
        Map<String, Integer> map = new HashMap<>();
        for (String token : tokens) {
            map.put(token, map.getOrDefault(token, 0) + 1);
        }
        return map;
    }

    private double magnitude(Collection<Integer> values) {
        double sum = 0.0;
        for (int v : values) sum += v * v;
        return Math.sqrt(sum);
    }
}