package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({"ID", "書籍名", "ISBN", "書籍説明", "出版", "値段", "出版日付", "バージョン"})
@Data
public class CsvBookDto {
	
    @JsonProperty("ID")
    private int id;
    
    @JsonProperty("書籍名")
    private String name;
    
    @JsonProperty("ISBN")
    private String isbn;
    
    @JsonProperty("書籍説明")
    private String description;
    
    @JsonProperty("出版")
    private String publisher;
    
    @JsonProperty("値段")
    private Integer price;
    
    @JsonProperty("出版日付")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String publication_date;
    
    @JsonProperty("バージョン")
    private int version;
//    
//    @JsonProperty("更新日時")
//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//    private Date modified;

    public CsvBookDto() {}

//    public CsvBookDto(Long id, String name, String desc, Date modified) {
//        this.id = id;
//        this.name = name;
//        this.desc = desc;
//        this.modified = modified;
//    }
}
