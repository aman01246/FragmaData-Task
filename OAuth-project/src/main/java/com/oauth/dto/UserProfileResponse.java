package com.oauth.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {

    private Long id;

    private String name;

    private String email;

    private String profilePicture;

    private String phone;

    private String department;

    private String designation;

    List<UserIdentityResponse> identities;
}