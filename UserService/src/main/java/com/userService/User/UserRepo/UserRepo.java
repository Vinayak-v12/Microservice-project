package com.userService.User.UserRepo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.userService.User.UserModel.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
	
	@Query("select u from Users u  where  u.userName = :username")
	public Users findByName(String username);
	@Query("select u.id from Users u where u.userName=:username and u.email=:email")
	public UUID findID(String username,String email);
	@Query("select u.email from Users u where u.id=:id")
	public String getMailById(UUID id);

}
