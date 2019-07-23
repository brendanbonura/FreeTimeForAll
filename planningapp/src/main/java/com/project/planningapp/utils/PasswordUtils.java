package com.project.planningapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
    public static void main(String[] args) {
        String password = "test123";
        String encrytedPassword = encryptPassword(password);
 
        System.out.println("Encryted Password: " + encrytedPassword);
    }

}
