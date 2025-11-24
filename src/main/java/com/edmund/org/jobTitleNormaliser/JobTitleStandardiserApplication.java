package com.edmund.org.jobTitleNormaliser;

import com.edmund.org.jobTitleNormaliser.model.JobProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(JobProperties.class)
public class JobTitleStandardiserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobTitleStandardiserApplication.class, args);
	}
}
