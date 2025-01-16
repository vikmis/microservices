package com.in28minutes.microservices.ApiGateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                // Example route for HTTPBin
                .route(p -> p
                        .path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                
                // Route for Currency Exchange Service
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange-service")) // Matches Eureka name
                
                // Route for Currency Conversion Service
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion-service")) // Matches Eureka name
                
                // Route for Currency Conversion Service with Feign
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion-service")) // Matches Eureka name
                
                // Route for Currency Conversion Service with Path Rewrite
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)", 
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion-service")) // Matches Eureka name
                
                .build();
    }
}