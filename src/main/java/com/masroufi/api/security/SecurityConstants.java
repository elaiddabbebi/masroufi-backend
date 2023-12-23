package com.masroufi.api.security;

public class SecurityConstants {
	public static final long ACCESS_TOKEN_EXPIRATION_TIME = 86400000; // 10 days
	public static final long REFRESH_TOKEN_EXPIRATION_TIME = 864000000; // 100 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_SECRET = "q4sd56qsz53d5qssdz7ez58 ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_IN_URL = "/login";
	public static final String RECOVERY_URL = "/recovery";
	public static final String RESET_PASSWORD_URL = "/reset-password";
}
