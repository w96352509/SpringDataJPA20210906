package com.spring.mvc.single.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		int count = 50;
		for (int i = 0; i < count; i++) {
			User user = new User();
			user.setName(faker.name().lastName());
			user.setPassword(String.format("%04d", r.nextInt(10000)));
			user.setBirth(faker.date().birthday());
			// �x�s���Ʈw(�]��interface��extend�ҥH����k)
			userRepository.saveAndFlush(user);
		}
		return "CreateSampleData";
	}

	// �d�߽d�Ҹ�� 1 -�����
	@GetMapping("/test/findall")
	@ResponseBody
	public List<User> testFindall() {
		List<User> users = userRepository.findAll();
		return users;
	}

	// �d�߽d�Ҹ�� 2 -�Ƨǧ�k
	@GetMapping("/test/findall_sort")
	@ResponseBody
	public List<User> testFindallSort() {
		Sort sort = new Sort(Sort.Direction.ASC, "name"); // ASC �۵M�Ƨ�(�p��j) �Ϥ�DESC
		List<User> users = userRepository.findAll(sort);
		return users;
	}

	// �d�߽d�Ҹ�� 3-�d����wID�����
	@GetMapping("/test/findall_ids")
	@ResponseBody
	public List<User> testFindallIds() {
		// �d����wID�����
		Iterable<Long> ids = Arrays.asList(1L, 3L, 5L); // �ŦX135���
		List<User> users = userRepository.findAll(ids);
		return users;
	}

	// �d�߽d�Ҹ�� 4 �]�w�ݨD��k , �ھ� Example �Ҵ��Ѫ����(�Ҧp:user)�i��d��
	@GetMapping("/test/findall_example")
	@ResponseBody
	public List<User> testFindalExample() {
		User user = new User();
		user.setId(2L);

		Example<User> example = Example.of(user);
		List<User> users = userRepository.findAll(example);
		return users;
	}

	// �d�߽d�Ҹ�� 5 (���)
	@GetMapping("/test/findall_example2")
	@ResponseBody
	public List<User> testFindalExample2() {
		User user = new User();
		user.setName("a"); // ����withMatcher("name"~
		// name �����e�O�_�]�ta
		// �إ�Example��ﾹ
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",
				ExampleMatcher.GenericPropertyMatchers.contains());
		Example<User> example = Example.of(user, matcher);
		List<User> users = userRepository.findAll(example);
		return users;
	}

	// �d�߽d�Ҹ�� 6 (�浧)
	@GetMapping("/test/find_one")
	@ResponseBody
	public User getOne() {

		return userRepository.findOne(3L);
	}

	// �����d��
	@GetMapping("/test/page/{no}")
	@ResponseBody
	public List<User> testPage(@PathVariable("no") Integer no) {
		int pageNo = no;
		int pageSize = 10; // �C������
		// �Ƨ�
		Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "name");

		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "id");

		Sort sort = new Sort(order1, order2);

		// �����ШD
		PageRequest pageRequest = new PageRequest(pageNo, pageSize, sort);
		Page<User> page = userRepository.findAll(pageRequest);

		return page.getContent();
	}

	@GetMapping("/test/name")
	@ResponseBody // ?name=
	public List<User> getByName(@RequestParam("name") String name) {

		return userRepository.getByName(name);
	}

	// ���� url : /mvc/user/test/name/id/S/50
	@GetMapping("/test/name/id/{name}/{id}")
	@ResponseBody // ?name=
	public List<User> getByNameAndID(@PathVariable("name") String name, @PathVariable("id") Long id) {

		return userRepository.getByNameStartingWithAndIdGreaterThanEqual(name, id);
	}

	// ���� url : /mvc/user/test/ids?ids=5,10,15
	@GetMapping("/test/ids")
	@ResponseBody // ?name=
	public List<User> getByIds(@RequestParam("ids") List<Long> ids) {

		return userRepository.getByIdIn(ids);
	}

	// ���� url : /mvc/user/test/birth?birth=2000-9-9(�榡:ISO.DATE)
	@GetMapping("/test/birth")
	@ResponseBody // ?name=

	public List<User> getByBirthLessThan(@RequestParam("birth") @DateTimeFormat(iso = ISO.DATE) Date birth) {

		return userRepository.getByBirthLessThan(birth);
	}

	// ���� url : /mvc/user/test/birth_between?begin=1965-9-9&end=1970-12-31
	@GetMapping("/test/birth_between")
	@ResponseBody 

	public List<User> getByBirthBetween(@RequestParam("begin") @DateTimeFormat(iso = ISO.DATE) Date begin
			                           ,@RequestParam("end") @DateTimeFormat(iso = ISO.DATE) Date end) {

		return userRepository.getByBirthBetween(begin, end);
	}
}
