package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Book;
import com.example.demo.domain.repository.BookRepository;

@Service
@Transactional
public class BookService {
	@Autowired
	BookRepository repository;

	/**
	 * 登録処理
	 * 
	 * @param book
	 */
	public void save(Book book) {
		String userId = "userId";
		Date now = new Date();
		book.setCreated_user(userId);
		book.setCreated_at(now);
		book.setUpdated_user(userId);
		book.setUpdated_at(now);
		repository.save(book);
	}

	/**
	 * 存在チェック処理
	 * 
	 * @param isbn
	 * @return
	 */
	public boolean existsIsbn(String isbn) {
		return repository.existsIsbn(isbn) == null ? false : true;
	}
	
	/**
	 * 検索処理
	 * 
	 * @param params
	 * @return
	 */
	public ArrayList<Book> selectMany(Map<String, String> params) {
		return repository.selectMany(params);
	}

}
