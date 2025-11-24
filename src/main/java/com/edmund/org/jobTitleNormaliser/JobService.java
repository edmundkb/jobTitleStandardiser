package com.edmund.org.jobTitleNormaliser;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final Normaliser normaliser;

    public JobService(Normaliser normaliser) {
        this.normaliser = normaliser;
    }

    @Cacheable("job-normaliser-cache")
    public String normaliseTitle(String input) {
        return normaliser.normalise(input);
    }
}

