package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {
	
	@GetMapping({"","/"})
	public String index() { //컨트롤러에서 세션을 어떤게 접근할까?
		// /WEB-INF/views/index.jsp
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() { //컨트롤러에서 세션을 어떤게 접근할까?
		return "board/saveForm";
	}
}
