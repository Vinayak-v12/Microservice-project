package com.userService.User.UserService;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userService.User.UserModel.Users;
import com.userService.User.UserRepo.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
	@Autowired
	private UserRepo repo;
	
	private static final String SECRET_KEY_BASE64 = "cJA9qVoXjVdLcGuuXc0QDdtaWmV5d8m+EZrclCxpfLU=";
	//private String secretKey="";
	
//	public JwtService() {
//	KeyGenerator keygen;
//	try {
//		keygen = KeyGenerator.getInstance("hmacSHA256"); // didnt generate key randomly
//		SecretKey sk=keygen.generateKey();  // if you have multiple instances users may login into to differnt instance which have differnt keys even though token is legit it dont allow
//		this.secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
//		System.out.println("Secret key in string: "+secretKey);
//	} catch (NoSuchAlgorithmException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	}
	
	
	private Key getKey() {
		
		byte[] arr=Decoders.BASE64.decode(SECRET_KEY_BASE64);
		return Keys.hmacShaKeyFor(arr);
	}

	public String getToken(Users users) {
		String username=users.getUserName();
		UUID id= repo.findID(users.getUserName(), users.getEmail());
		
		return Jwts.builder()
			.claim("userId", id.toString())
			.subject(username)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis()+ 2*60*60*1000))
			.signWith(getKey())
			.compact();
		
		
		
	}
	

}
