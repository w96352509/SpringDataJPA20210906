package com.spring.mvc.single.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.mvc.single.entity.User;
/*
 * ��k�R�W�W�h
 * 1.�d�ߤ�k�H find | read | get �}�Y
 * 2. �A�α���d�߮�  , �����ݩ� (���r���j�g) �α�������r (�Ҧp:And , Or) �s��
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    //�ھ� name �Ө��o User (�h��)
	List<User> getByName(String name);
	
	//Where name Like ? AND id >=?
	List<User> getByNameStartingWithAndIdGreaterThanEqual(String name , Long id);
	
	
	//Where id in (? , ? , ?)
	
	List<User> getByIdIn(List<Long> ids);
	
	//Where birth <?
	
	List<User> getByBirthLessThan(Date birth);
	
	//Where birth >= ? AND birth <=?     ����H�U
	//Where birth between ?(�t) AND ?(�t)
	List<User> getByBirthBetween(Date birthBegin , Date birthEnd);
}
