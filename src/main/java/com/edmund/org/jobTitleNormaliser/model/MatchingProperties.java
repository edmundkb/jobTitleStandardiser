package com.edmund.org.jobTitleNormaliser.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "normaliser")
public class MatchingProperties {
    private double threshold;
}

