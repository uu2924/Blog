package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.BlogUser;

// DAO
// 자동으로 bean으로 등록이 된다.
// @Repository 생략가능
public interface UserRepository extends JpaRepository<BlogUser, Integer>{

	//Select * from bloguser Where username=?;
	Optional<BlogUser> findByUsername(String username);
	
}



//JPA Naming 전략
	//Select * From bloguser Where username=?1 and password=?2;
	//BlogUser findByUsernameAndPassword(String username, String password);
	
//	@Query(value="Select * From bloguser Where username=?1 and password=?2", nativeQuery=true)
//	BlogUser login(String username, String password);