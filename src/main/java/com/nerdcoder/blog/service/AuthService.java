package com.nerdcoder.blog.service;

import com.nerdcoder.blog.payload.LoginDto;
import com.nerdcoder.blog.payload.RegisterDto;

public interface AuthService {
	
	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
