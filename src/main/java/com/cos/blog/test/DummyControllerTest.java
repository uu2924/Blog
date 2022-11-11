package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.BlogUser;
import com.cos.blog.model.RoleType;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired  //DI 의존성 주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제실패하였습니다. 해당 id는 존재하지 않습니다.";
		}
		return "삭제되었습니다.";
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public BlogUser updateUser(@PathVariable int id, @RequestBody BlogUser requestUser) { //RequestBody: json을 자바 object로 변환
		System.out.println("id: "+ id);
		System.out.println("password: "+ requestUser.getPassword());
		System.out.println("email: "+ requestUser.getEmail());
		
		BlogUser user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
	
		// userRepository.save(user);
		
		// 더티 체킹 - 변경감지
		// @Tansactional 함수 종료시 자동 커밋
		
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<BlogUser> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("dummy/user")
	public List<BlogUser> pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<BlogUser> paginUsers = userRepository.findAll(pageable);
		List<BlogUser> users = paginUsers.getContent();
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
