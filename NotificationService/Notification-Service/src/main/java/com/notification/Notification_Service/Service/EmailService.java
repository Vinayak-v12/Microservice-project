package com.notification.Notification_Service.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Task.TaskService.DTO.EmailDto;


@Service
public class EmailService {
	private JavaMailSender mailsender;
	
	public EmailService(JavaMailSender mailsender) {
		this.mailsender=mailsender;
	}
	public void send(EmailDto event) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(event.getUserEmail());
		message.setSubject("!🚨 Action needed ,Task reaching deadline ");
		message.setText(" Dear Recipient,\n\n\nThis is an auto generated message from vinayak's Task-Management app\n\n\n"+event.getMessage()+"\n\n\n\"***NOTE: Please do not reply to this automated message.***");
		mailsender.send(message);
		
	}

}
