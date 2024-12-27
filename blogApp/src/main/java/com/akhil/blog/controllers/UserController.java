package com.akhil.blog.controllers;

import java.util.List;

import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.blog.payloads.ApiResponse;
import com.akhil.blog.payloads.UserDto;
import com.akhil.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	 @Autowired
	 private UserService userService;
	 
	 
	 // Post --> Creating a user in the user entity:
	 @PostMapping("/")
	 public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
		 UserDto createUserDto = this.userService.createUser(userDto);
		 
		 return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	 }
	 
	 // Put --> updating the user data 
	 @PutMapping("/{userId}")
	 public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userTd") Integer id){
		 UserDto updatedUser= this.userService.upadteUser(userDto, id);
		 
		 return  ResponseEntity.ok(updatedUser) ;
		 }

	 
	 // Delete a particular User
	 @DeleteMapping("/{userId}")
	 public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	 {
		 this.userService.deleteuser(uid);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true),HttpStatus.OK);
	 }
	 
	 @GetMapping("/")
	 public ResponseEntity<List<UserDto>> getAllUsers(){
		 return ResponseEntity.ok(this.userService.getAllUser());
	 }
	 

	 @GetMapping("/{userId}")
	 public ResponseEntity<UserDto> getAllUsers(@PathVariable Integer userId){
		 return ResponseEntity.ok(this.userService.getUserById(userId));
	 }
	 
	 
}
