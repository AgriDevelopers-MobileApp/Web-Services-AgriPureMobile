package com.agripure.agripurebackend.security.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePassword {
    @NotNull
    private String username;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
