package com.demo.challenge.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 * 
 * @author Prashant
 */
public final class HeaderUtil {

	private HeaderUtil() {
	}

	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-challengeApp-alert", message);
		headers.add("X-challengeApp-params", param);
		return headers;
	}
}
