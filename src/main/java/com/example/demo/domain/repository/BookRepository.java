package com.example.demo.domain.repository;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Book;

@Mapper
public interface BookRepository {
	
	/**
	 * 登録処理
	 * 
	 * @param book
	 * @return
	 */
	public int save(Book book);

	/**
	 * 存在チェック処理
	 * 存在する場合、重複エラーでフロン側にエラーを表示
	 * @param isbn
	 * @return
	 */
	public Book existsIsbn(String isbn);

	/**
	 * 検索処理
	 * 
	 * @param params
	 * @return
	 */
	public ArrayList<Book> selectMany(Map<String, String> params);
	
}
