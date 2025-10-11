package com.userService.User.UserController;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userService.User.UserModel.Users;
import com.userService.User.UserService.UserService;

@RestController
public class UserController {
	
	private UserService userservice;
	
	
	public UserController(UserService userservice){
		this.userservice=userservice;
	}
	
	
	
	@PostMapping("/login")
	public String validate(@RequestBody Users user) {
		return userservice.validate(user); // returns jwt token
		
	}
	@PostMapping("/register")
	public void register(@RequestBody Users user) {
		 try {
		        userservice.register(user);
		        System.out.println("user registarintion 200");
		    } catch (Exception e) {
		        e.printStackTrace(); // log the real error
		        
		    }
		
	}
	@GetMapping("/userEmail")
	public String getMail(@RequestParam String uuid) {
		UUID userid=UUID.fromString(uuid);
		return userservice.getMail(userid);
		
	}

}
