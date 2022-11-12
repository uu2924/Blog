package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) { //컨트롤러에서 세션을 어떤게 접근할까?
		model.addAttribute("boards", boardService.posts(pageable));
		//System.out.println(model);
		return "index"; //viewResolve작동시 model정보를 가지고 jsp 호출
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.postDetail(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		//System.out.println("글 수정하기 "+ id);
		model.addAttribute("board", boardService.postDetail(id));
		return "board/updateForm";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() { //컨트롤러에서 세션을 어떤게 접근할까?
		return "board/saveForm";
	}
}
