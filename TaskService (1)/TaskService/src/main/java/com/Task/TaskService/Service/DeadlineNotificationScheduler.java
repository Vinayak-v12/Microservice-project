package com.Task.TaskService.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Task.TaskService.DTO.EmailDto;
import com.Task.TaskService.Model.Task;
import com.Task.TaskService.Repo.TaskRepository;

@Service
public class DeadlineNotificationScheduler {
	
		@Autowired
		private UserInterface userInterface;
	
		private final TaskRepository repo;
		private final NotificationProducer producer;
		

	    // Define the notification window (e.g., 24 hours)
	    private static final long NOTIFICATION_WINDOW_HOURS = 24; 
		
	    
		public DeadlineNotificationScheduler(TaskRepository repo, NotificationProducer producer) {
			this.repo = repo;
			this.producer = producer;
		}
		
		//Runs every 20 seconds. Ensure this frequency is appropriate for production.
		@Scheduled(fixedRate = 20000) 
		public void publishNotifications(){
			
	     
			LocalDateTime currentTime = LocalDateTime.now();
			LocalDateTime notificationWindowEnd = currentTime.plus(NOTIFICATION_WINDOW_HOURS, ChronoUnit.HOURS);

	    

	        List<Task> approachingTasks = repo.findTasksForDeadlineNotification(currentTime, notificationWindowEnd);

	        if (approachingTasks.isEmpty()) {
	            System.out.println("No tasks found needing a deadline notification.");
	            return;
	        }

	        System.out.println("Found " + approachingTasks.size() + " tasks to notify.");

	        // Loop through all found tasks, send event, and mark as notified
	        for (Task tsk : approachingTasks) {
	            
	            UUID userId = tsk.getUserId();
	            String userEmail=userInterface.getMail(userId.toString());
	          
	            EmailDto event = new EmailDto(); // Instantiate a new DTO for each message
	            event.setUserEmail(userEmail);
	            event.setDeadline(tsk.getDeadline());
	            event.setMessage("Your task '" + tsk.getTask() + "' is reaching its deadline!");
	            
	            // Publish the event
	            producer.send(event);
	            
	            //  Mark the task as notified in the database
	            repo.markNotified(tsk.getId()); 
	            System.out.println("Notified and marked task: " + tsk.getId());
	        }
		}
	}


