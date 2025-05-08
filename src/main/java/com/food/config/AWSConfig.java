package com.food.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
//this class become configuration class for s3 
public class AWSConfig {
	
	private String accessKey="AKIA4VDBLWTWCDGZBJ4R";

	private String secretKey="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";
	//@Value("${AWS_REGION}")
	private String region="eu-north-1";
	 
	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
		        .build();
	}

}
