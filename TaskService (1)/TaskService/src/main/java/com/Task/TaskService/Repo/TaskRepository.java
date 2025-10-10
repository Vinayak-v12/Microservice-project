package com.Task.TaskService.Repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Task.TaskService.Model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, UUID> {
	@Query("select t from Task t where t.deadline>:currentTime and t.deadline<=:window and t.deadlineNotified=false")
	public List<Task> findTasksForDeadlineNotification(LocalDateTime currentTime,LocalDateTime window);

	@Modifying
	@Transactional
	@Query("Update Task t set t.deadlineNotified=true where t.id=:taskId")
	public void markNotified(UUID taskId);
}
