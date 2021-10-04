package com.spring.mvc.single.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
			//�x�s���Ʈw(�]��interface��extend�ҥH����k)
			userRepository.saveAndFlush(user);
		}
		return "CreateSampleData" ;
	}
	//�d�߽d�Ҹ�� 1
	@GetMapping("/test/findall")
	@ResponseBody
	public List<User> testFindall() {
		List<User> users = userRepository.findAll();
		return users ;
	}
	
	//�d�߽d�Ҹ�� 2
		@GetMapping("/test/findall_sort")
		@ResponseBody
		public List<User> testFindallSort() {
			Sort sort = new Sort(Sort.Direction.ASC,"name"); //ASC �۵M�Ƨ�(�p��j) �Ϥ�DESC
			List<User> users = userRepository.findAll(sort);
			return users ;
		}
}
