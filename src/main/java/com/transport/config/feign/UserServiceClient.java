package com.transport.config.feign;

import com.transport.api.dto.UserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/users/current")
    @Headers("Content-Type: application/json")
    UserDto getCurrentUser();
}
