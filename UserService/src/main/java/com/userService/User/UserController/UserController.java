package com.userService.User.UserController;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Map<String, String>> validate(@RequestBody Users user) {
	    String token = userservice.validate(user);
	    if (token == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body(Map.of("error", "Invalid username or password"));
	    }
	    return ResponseEntity.ok(Map.of("token", token));
	}
	

	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> register(@RequestBody Users user) {
		 try {
		        userservice.register(user);
		        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered"));
		    } catch (Exception e) {
		        e.printStackTrace(); // log the real error
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ERROR","Cannot be regsistered"));
		        
		    }
		
	}
	@GetMapping("/userEmail")
	public String getMail(@RequestParam String uuid) {
		UUID userid=UUID.fromString(uuid);
		return userservice.getMail(userid);
		
	}

}
