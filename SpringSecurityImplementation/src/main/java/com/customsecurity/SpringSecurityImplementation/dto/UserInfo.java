package com.customsecurity.SpringSecurityImplementation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	
	private String firstName;
	private String lastName;
	private String userName;
	
	private Object roles;

	
	
}
