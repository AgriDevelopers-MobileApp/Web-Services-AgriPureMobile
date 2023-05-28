package com.agripure.agripurebackend.security.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class UpdatedUser {
    @NotNull
    private String name;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    private Set<String> roles = new HashSet<>();
}
