package com.techacademy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;//追加

import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository repository) {
		this.userRepository = repository;
	}

	/** 全件を検索して返す */
	public List<User> getUserList(){
		// リポジトリのfindAllメソッドを呼び出す
		return userRepository.findAll();
	}

	// ----- User更新機能、追加2:ここから -----
    /** Userを1件検索して返す */
	public User getUser(Integer id) {
		return userRepository.findById(id).get();
	}
	// ----- 追加2:ここまで -----

	// ----- User登録機能、追加1:ここから -----
    /** Userの登録を行なう */
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	// ----- 追加1:ここまで -----
}
