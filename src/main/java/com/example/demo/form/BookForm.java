package com.example.demo.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.domain.model.Book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
 
@Data
public class BookForm {
	
	/**
	 * id
	 */
	private int id;
	
	/**
	 * 氏名
	 */
	@Size(min=1, max=100)
	private String name;
	
	/**
	 * International Standard Book Number
	 * 世界共通で書籍を特定するための番号
	 */
	@Pattern(regexp = "^[0-9]{13}$")
	private String isbn;
	
	/**
	 * 説明
	 */
	private String description;
	
	/**
	 * 出版
	 */
	@Size(max=100)
	private String publisher;
	
	/**
	 * 値段
	 */
	@NotNull
	@Min(0)
	private Integer price;
	
	/**
	 * 出版日付
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date publication_date;
	
	/**
	 * バージョン
	 */
	private int version;
	
	public BookForm() {}
	 
	public BookForm(Book book) {
		this.id = book.getId();
		this.name = book.getName();
		this.isbn = book.getIsbn();
		this.description = book.getDescription();
		this.publisher = book.getPublisher();
		this.publication_date = book.getPublication_date();
		this.price = book.getPrice();
		this.version = book.getVersion();
	}
}
