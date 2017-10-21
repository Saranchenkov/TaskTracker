package com.saranchenkov.taskTracker;

import com.saranchenkov.taskTracker.domain.Project;
import com.saranchenkov.taskTracker.domain.Role;
import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.repository.ProjectRepository;
import com.saranchenkov.taskTracker.repository.TaskRepository;
import com.saranchenkov.taskTracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TaskTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository){
		return args -> {
//			User roma1 = new User(
//					"roma.karpik@gmail.com",
//					"12345",
//					"Roman",
//					"Karpik",
//					Role.DEVELOPER
//			);
//			userRepository.save(roma1);
//
//			User roma2 = new User(
//					"roma.lunkov@gmail.com",
//					"12345",
//					"Roman",
//					"Lunkov",
//					Role.DEVELOPER
//			);
//			userRepository.save(roma2);
//
//			User vlad = new User(
//					"vlad.michno@gmail.com",
//					"12345",
//					"Vladislav",
//					"Michno",
//					Role.DEVELOPER
//			);
//			userRepository.save(vlad);
//
//			System.out.println("Users were saved!");
		};
	}
}
