package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.jwt.JwtTokenProvider;
import com.cos.blog.model.BlogUser;
import com.cos.blog.model.RoleType;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//스프링 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IOC를 해준다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public void signUp(BlogUser user) {
		String rawPassword = user.getPassword(); // 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	public String loginMember(BlogUser requestUser){
		BlogUser user = userRepository.findByUsername(requestUser.getUsername()).orElseThrow(() -> {
			return new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		});

		if (!passwordEncoder.matches(requestUser.getPassword(), user.getPassword()))
			return null;

		return jwtTokenProvider.createToken(requestUser.getUsername());
	}
}
