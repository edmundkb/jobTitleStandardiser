package com.edmund.org.jobTitleNormaliser.rest;

import com.edmund.org.jobTitleNormaliser.JobService;
import com.edmund.org.jobTitleNormaliser.model.NormalisedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobNormalisationController {

    private final JobService jobService;

    @GetMapping("/normalise")
    public NormalisedResponse normalise(@RequestParam String title) {
        log.info("Received normalise request: {}", title);

        String normalised = jobService.normaliseTitle(title);

        return new NormalisedResponse(title, normalised);
    }
}

