package de.neuefische.studentdbbackend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("stock");

        cacheManager.setCaffeine(
                newBuilder()
                        .expireAfterWrite(10, TimeUnit.SECONDS)
                        .weakKeys()
                        .recordStats());

        return cacheManager;
    }
}