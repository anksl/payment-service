package com.transport.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    private String password;
    @NotBlank
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    private Integer rating;
    private Boolean enabled;
    private Set<RoleDto> roles;
}
