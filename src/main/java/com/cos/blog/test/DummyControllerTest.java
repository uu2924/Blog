package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.BlogUser;
import com.cos.blog.model.RoleType;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired  //DI 의존성 주입
	private UserRepository userRepository;
	
	@GetMapping("/dummy/users")
	public List<BlogUser> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("dummy/user")
	public Page<BlogUser> pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<BlogUser> users = userRepository.findAll(pageable);
		return users;
	}
	
	//{id}주소로 파라미터를 전달 받을 수 있다.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public BlogUser detail(@PathVariable int id) {
		//디비에서 못찾아오면 null이니까 null인지 판단 해서 BlogUser객체로 감싸서 리턴
//		BlogUser user = userRepository.findById(id).orElseGet(new Supplier<BlogUser>() {
//			@Override
//			public BlogUser get() {
//				return new BlogUser();
//			}
//		});
		BlogUser user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당유저는 없습니다. id: " + id);
			}
		});
		// 요청: 웹브라우저
		// user 객체: 자바오브젝트
		// 브라우저가 이해할 수있게 변환 Json
		// 스프링부트 MessageConvert가 응답시에 자동 작동
		// MessageConverter가 Jackson라이브러리를 호출해서 변경
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(BlogUser user) {
		System.out.println("id:"+user.getId());
		System.out.println("username:"+user.getUsername());
		System.out.println("password:"+user.getPassword());
		System.out.println("email:"+user.getEmail());
		System.out.println("date:"+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
