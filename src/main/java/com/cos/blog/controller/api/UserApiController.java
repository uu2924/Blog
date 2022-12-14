package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.BlogUser;
import com.cos.blog.model.RoleType;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userSerivce;
	
	/*
	 * @Autowired private HttpSession session;
	 */
	
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody BlogUser user) {
		System.out.println("UserApiController save 호출: " + user);
		
		user.setRole(RoleType.USER);
		userSerivce.signUp(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); //리턴될때 Jackson이 json으로 변경해서 리턴
	}
	
	
}
