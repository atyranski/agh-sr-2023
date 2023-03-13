package edu.agh.atyranski.server.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommitAnalyzerConfig {

    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Bean
    public AsyncHttpClient asyncHttpClientBean() {
        return asyncHttpClient;
    }

}
