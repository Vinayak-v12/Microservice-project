package com.Task.TaskService.Service;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.Task.TaskService.Model.Task;
import com.Task.TaskService.Repo.TaskRepository;


@Service
public class TaskService {
	
	private TaskRepository taskrepo;
	
	public TaskService(TaskRepository taskrepo) {
		this.taskrepo=taskrepo;	
	} 
	
	
	public ResponseEntity<Task> save_update(Task task){
		Task tsk=taskrepo.save(task);
		return new ResponseEntity<Task>(tsk,HttpStatus.OK);
			
	}
	
	public ResponseEntity<Task> del(UUID id,UUID userid){
		
		Optional<Task> opt=taskrepo.findById(id);
		if(opt.isPresent()) {
			Task taskToDel=opt.get();
			if(taskToDel.getUserId().equals(userid)) {
				taskrepo.delete(opt.get());
				return new ResponseEntity<Task>(opt.get(),HttpStatus.OK);	
			}
			else {
				return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
			}
			
		}
		else {
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
			
		}
		
	
			
	}

}
