package com.Task.TaskService.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USERSERVICE")
public interface UserInterface {
	
	@GetMapping("/userEmail")
	public String getMail(@RequestParam String uuid);
		
}
