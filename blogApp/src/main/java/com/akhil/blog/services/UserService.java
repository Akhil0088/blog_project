package com.akhil.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akhil.blog.payloads.UserDto;


@Service
public interface UserService {
	
	UserDto createUser(UserDto  user);
	UserDto upadteUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userrId);
	List<UserDto> getAllUser();
	void deleteuser(Integer userId);

}
