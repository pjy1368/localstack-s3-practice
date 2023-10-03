package com.jayon.awss3practice;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.localstack.LocalStackContainer;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Profile(value = "test")
@Configuration
public class S3Config {

    public static final String BUCKET_NAME = "test-bucket";

    @Bean(initMethod = "start", destroyMethod = "stop")
    public LocalStackContainer localStackContainer(){
        return new LocalStackContainer()
                .withServices(LocalStackContainer.Service.S3);
    }

    @Bean
    public AmazonS3 amazonS3(LocalStackContainer localStackContainer){
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(localStackContainer.getEndpointConfiguration(S3))
                .withCredentials(localStackContainer.getDefaultCredentialsProvider())
                .build();
        amazonS3.createBucket(BUCKET_NAME);
        return amazonS3;
    }
}
