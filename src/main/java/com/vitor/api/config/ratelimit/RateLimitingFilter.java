package com.vitor.api.config.ratelimit;

import com.vitor.api.exceptions.ExceptionDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int REQUEST_AMOUNT = 5;
    private static final int DURATION = 1;
    private final Map<String, Bucket> buckets =  new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(REQUEST_AMOUNT)
                .refillGreedy(REQUEST_AMOUNT, Duration.ofMinutes(DURATION))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().startsWith("/weather")) {
           String clientIp = request.getRemoteAddr();
           Bucket bucket = buckets.computeIfAbsent(clientIp, key -> createNewBucket());

            if(!bucket.tryConsume(1)){
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ExceptionDTO errorDto = new ExceptionDTO(
                        LocalDateTime.now(),
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
                        "Too Many Requests. Please try again later.",
                        request.getRequestURI());
                new ObjectMapper().writeValue(response.getWriter(), errorDto);
                return;
            }
       }
        filterChain.doFilter(request, response);
    }
}
