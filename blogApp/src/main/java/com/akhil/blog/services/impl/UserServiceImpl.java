package com.akhil.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.blog.entity.User;
import com.akhil.blog.payloads.UserDto;
import com.akhil.blog.repositories.UserRepo;
import com.akhil.blog.services.UserService;
import com.akhil.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User saveduser = this.userRepo.save(user);
		return this.userToDto(saveduser);
	}

	@Override
	public UserDto upadteUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteuser(Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		this.userRepo.delete(user);
		
	} 
	
	
	private User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class); 
		return userDto;
	}

}
