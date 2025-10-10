package com.userService.User.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.userService.User.UserModel.Users;
import com.userService.User.UserRepo.UserRepo;

@Service
public class MyuserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users=repo.findByName(username);
		if(users==null) {
			System.out.println("not found");
			throw new UsernameNotFoundException("Username not foung");
		}
		return new UserPrincipal(users); 
		
	}

}
