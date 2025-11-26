package com.example.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.context.annotation.Bean;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${internal.api.key}")
    private String internalApiKey;

    @Bean
    public WebClient productWebClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .responseTimeout(Duration.ofSeconds(4))
                .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(4))
                        .addHandlerLast(new WriteTimeoutHandler(4))
            );

        return builder
            .baseUrl(productServiceUrl)
            .defaultHeader("X-INTERNAL-API-KEY", internalApiKey)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .codecs(configurer -> 
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
            .build();
    }
    
}
