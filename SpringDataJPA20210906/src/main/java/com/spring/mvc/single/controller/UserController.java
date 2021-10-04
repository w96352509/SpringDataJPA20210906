package com.spring.mvc.single.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.javafaker.Faker;
import com.spring.mvc.single.entity.User;
import com.spring.mvc.single.repository.UserRepository;


@Controller
@RequestMapping("/user")
public class UserController {
  
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test/create_sample_data")
	@ResponseBody
	public String testCreateSampleData() {
		Faker faker = new Faker();
		Random r = new Random();
		int count =50 ;
		for(int i=0 ; i<count;i++) {
			User user = new User();
			user.setName(faker.name().lastName());
			user.setPassword(String.format("%04d", r.nextInt(10000)));
			user.setBirth(faker.date().birthday());
			//儲存到資料庫(因有interface有extend所以有方法)
			userRepository.saveAndFlush(user);
		}
		return "CreateSampleData" ;
	}
}
