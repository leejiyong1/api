package com.example.configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate

@Configuration
class RestClientConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}