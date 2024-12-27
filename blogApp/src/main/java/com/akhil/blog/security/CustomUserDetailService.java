package com.akhil.blog.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akhil.blog.entity.User;
import com.akhil.blog.exceptions.ResourceNotFoundException;
import com.akhil.blog.repositories.UserRepo;


@Service
public class CustomUserDetailService  implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = this.userRepo.findByEmail(username).orElseThrow(
				()-> new ResourceNotFoundException("username", "email: "+username  ,0));
		return user;
	}

}
