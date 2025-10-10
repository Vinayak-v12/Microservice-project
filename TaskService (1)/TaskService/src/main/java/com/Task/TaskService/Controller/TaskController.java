package com.Task.TaskService.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import com.Task.TaskService.Model.Task;
import com.Task.TaskService.Service.TaskService;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.POST,RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TaskController {
	

	private TaskService taskservice;
	
	public TaskController(TaskService taskservice) {
		this.taskservice=taskservice;
	}
	@PostMapping("/add")
	public ResponseEntity<Task> save_update(@RequestBody Task task){
		return taskservice.save_update(task);
		
	}
	@DeleteMapping("/del/{id}")
	public ResponseEntity<Task> del(@RequestHeader("X-USER-ID") String userId,@PathVariable UUID id){
		UUID userID=UUID.fromString(userId);
		return taskservice.del(id,userID); 
	}
	@GetMapping("/demo")
	public String demo(@RequestHeader("X-USER-ID") String userId){
		System.out.println("userId:"+userId);
		return "token working"+userId;
	}

}
