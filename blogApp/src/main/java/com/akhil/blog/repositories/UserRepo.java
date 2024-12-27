package com.akhil.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.blog.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	
	Optional<User> findByEmail(String email);
	

}
