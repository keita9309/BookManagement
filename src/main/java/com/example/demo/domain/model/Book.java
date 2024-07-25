package com.example.demo.domain.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.example.demo.form.BookForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"ID", "書籍名", "ISBN", "書籍説明", "出版", "値段", "出版日付", "バージョン"})
public class Book {
	// MybatisのresultMap用にデフォルトコンストラクタを用意しておきます。
	public Book() {}	
	
	// BookFormの値を格納するためのコンストラクタ
	// （Controller内でSetterを使って格納するよりこちらの方がスマート）
	public Book(BookForm form) {
		this.id = form.getId();
		this.name = form.getName();
		this.isbn = form.getIsbn();
		this.description = form.getDescription();
		this.publisher = form.getPublisher();
		this.price = form.getPrice();
		this.publication_date = form.getPublication_date();
		this.version = form.getVersion();
	}

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
	private int price;
	@JsonProperty("出版日付")
	private Date publication_date;
	@JsonProperty("バージョン")
	private int version;
	private String created_user;
	private Date created_at;
	private String updated_user;
	private Date updated_at;
	private String deleted_user;
	private Date deleted_at;
	
	// ISBNコードをハイフンで区切って表示するメソッド
	public String isbnFormat() {
		String prefix = isbn.substring(0,3);			// 接頭記号(3桁)
		String group = isbn.substring(3,4);				// グループ記号(1桁)
		String publisher = isbn.substring(4,10);		// 出版者記号(6桁)
		String bookName = isbn.substring(10,12);		// 書名記号(2桁)
		String checkDigit = isbn.substring(12,13);		// チェックディジット(1桁)
		return String.join("-", prefix, group, publisher, bookName, checkDigit);
	}
	 
	// ゼロを3つごとにカンマで区切るメソッド
	public String priceCommaOf1000() {
		return String.format("%,d", price);
	}
	 
	// 日付を和暦(年月日)表示にするメソッド
	public String publicationDateOfYyyymmdd() {
		if(publication_date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(publication_date).toString();
	}
	
	// Comparatorを使って複数の項目で降順にソートするComparatorを定義する
	public static Comparator<Book> getDescendingComparator() {
		return Comparator.comparing(Book::getPublication_date, Comparator.reverseOrder()).thenComparing(Book::getIsbn,
				Comparator.reverseOrder());
	}
}
