package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.BlogUser;
import com.cos.blog.model.Board;
import com.cos.blog.repository.BoardRepository;

//스프링 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IOC를 해준다.
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void write(Board board, BlogUser user) {
		board.setCount(0);
		board.setBlogUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> posts(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board postDetail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패: 글 정보를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void remove(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패: 글 정보를 찾을 수 없습니다.");
		}); //영속화
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//서비스가 종료될 때 트랜잭션 종료, 더티체킹이 되며 자동 업데이트된다.
	}
}
