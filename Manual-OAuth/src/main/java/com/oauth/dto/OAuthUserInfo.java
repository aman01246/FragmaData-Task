package com.oauth.dto;

import lombok.Data;

@Data
public class OAuthUserInfo {

	private String sub;

	private String name;

	private String given_name;

	private String family_name;

	private String picture;

	private String email;

	private Boolean email_verified;
}
