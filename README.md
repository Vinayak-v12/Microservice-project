# 🧠 Task Management Microservice Project

A full-stack offline-first(Indexed DB)**Task Management Application** built using **Spring Boot Microservices** and a custom **HTML + CSS frontend**.  
It includes user authentication, task management, and email notifications for task reaching deadline via Redis Streams.Both frontend and backend deployed on render cloud

---

## 🌍 Live Links

| Service | URL |
|----------|-----|
| 🧩 API Gateway | https://api-gateway-rgim.onrender.com |
| 👤 User Service | https://user-service-heey.onrender.com |
| 📋 Task Service | https://task-service-mkjc.onrender.com |
| 📬 Notification Service | https://notification-service-yourname.onrender.com |
| 💻 Frontend | https://microservice-project-frontend.onrender.com |

---

## ⚙️ Tech Stack

**Backend:** Spring Boot, Spring Cloud (Eureka, Gateway), Spring Security, JWT, Redis Cloud, PostgreSql  
**Frontend:** HTML, CSS, JavaScript  
**Deployment:** Docker, Render Cloud  

---

## 🚀 Features

- offline first setup
- when synced updates tasks to cloud
- User registration & login (JWT authentication)  
- Email notifications before task deadlines  
- Fully deployed using Render  

---
> ⚠️ **Important:** Backend services are deployed on Render Free Tier and may be asleep. 
> Open all backend service URLs once to wake them up so that they gets registerd on eureka server(service registry) before using the frontend.
> otherwise app wont work properly as expected


## 🧩 Microservices Overview

| Service | Description |
|----------|--------------|
| Service Registry | Eureka Server |
| API Gateway | Routes all service requests, validates jwt |
| User Service | Handles user authentication, issues jwt |
| Task Service | Manages tasks and sends notifications to Redis |
| Notification Service | Reads from Redis Stream and sends emails |
| Frontend | Simple UI connected to API Gateway |

---

