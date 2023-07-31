package com.transport.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.transport.security.utils.SecurityConstants.AUTHORIZATION;


@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        requestTemplate.header(AUTHORIZATION, Objects.requireNonNull(requestAttributes).getRequest().getHeader(AUTHORIZATION));
    }
}
