package br.com.kauesoares.simplespringsecurityproject.project.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(
                    final String name
            ) {
                return new ConcurrentMapCache(name,
                        Caffeine.newBuilder()
                                .expireAfterWrite(getExpiration(name), TimeUnit.HOURS)
                                .maximumSize(100)
                                .build().asMap(),
                        false);
            }
        };
    }

    private long getExpiration(String cacheName) {
        String[] parts = cacheName.split("-");
        String lastPart = parts[parts.length - 1];

        try {
            return Long.parseLong(lastPart);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

}
