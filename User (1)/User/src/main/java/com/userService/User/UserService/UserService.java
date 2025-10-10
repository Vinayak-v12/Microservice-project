package com.userService.User.UserService;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.userService.User.UserModel.Users;
import com.userService.User.UserRepo.UserRepo;

@Service
public class UserService {
	
	private JwtService  jwtService;
	private AuthenticationManager authenticationManager;
	private UserRepo repo;
	private BCryptPasswordEncoder bcrypt;
	
	public UserService(UserRepo repo,AuthenticationManager authenticationManager,JwtService  jwtService) {
		this.repo=repo;
		this.authenticationManager=authenticationManager;
		this.bcrypt= new BCryptPasswordEncoder(12);
		this.jwtService=jwtService;
	}
	

	public void register(Users user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
		repo.save(user);
	}

	public String validate(Users users) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUserName(), users.getPassword()));
		if(authentication.isAuthenticated()) {
			System.out.println(users.getUserName()+"  "+repo.findID(users.getUserName(), users.getEmail()));
			return jwtService.getToken(users);
			
		}
		else {
			return "failed";
		}
	}
	
	public String getMail(UUID userid) {
		return repo.getMailById(userid);
	
	}

}
