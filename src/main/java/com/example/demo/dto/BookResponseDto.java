package com.example.demo.dto;

import java.util.List;

import com.example.demo.domain.model.Book;

import lombok.Data;

@Data
public class BookResponseDto {
	
	private List<Book> responseBookList;
    private String redirectPath;

}
