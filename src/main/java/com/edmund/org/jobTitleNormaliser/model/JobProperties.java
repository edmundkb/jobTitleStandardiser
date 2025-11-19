package com.edmund.org.jobTitleNormaliser.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "")
public record JobProperties(List<JobDefinition> jobs) {}


