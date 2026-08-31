package com.oauth.dto;

import lombok.Data;

@Data
public class CompleteProfileRequest {

    private String phone;

    private String department;

    private String designation;
}