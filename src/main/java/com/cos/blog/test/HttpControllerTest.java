package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//만약 사용자 요청에의해 html파일을 응답할 경우는 @Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest:";
	
//	@GetMapping("/http/lombok")
//	public String getlombok() {
//		Member m = new Member(1,"ssar","123","email");
//		
//		System.out.println(TAG+"getter:"+m.getId());
//		m.setId(5000);
//		System.out.println(TAG+"getter:"+m.getId());
//
//		return "lombok 테스트 완료";
//	}
	
	@GetMapping("/http/lombok")
	public String getlombok() {
		Member m = Member.builder().username("ss").password("123").email("test").build();
		
		System.out.println(TAG+"getter:"+m.getId());
		m.setId(5000);
		System.out.println(TAG+"getter:"+m.getId());

		return "lombok 테스트 완료";
	}
	
	//http://localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청" + m.getId() + ", " + m.getUsername();
	}

	//http://localhost:8080/http/post
//	@PostMapping("/http/post")
//	public String postTest(Member m) {
//		return "post 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", "+ m.getEmail();
//	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter가 자동으로 파싱
		return "post 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", "+ m.getEmail();
	}

	//http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"+ m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", "+ m.getEmail();
	}

	//http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
