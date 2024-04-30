package com.nerdcoder.blog.exception;

import org.springframework.http.HttpStatusCode;

public class BlogApiException extends RuntimeException {

	private HttpStatusCode httpStatus;
	private String message;
	
	public BlogApiException(HttpStatusCode httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatusCode getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatusCode httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
