package com.example.demo.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.model.Book;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.form.BookForm;
import com.example.demo.helper.DownloadHelper;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jakarta.servlet.http.HttpSession;
 
@Controller
public class BookController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private BookService service;
	
	@GetMapping("book/add")
	public String getAdd(BookForm form, Model model) {
		model.addAttribute("bookForm", form);
		return "book/add";
	}
	
	@GetMapping("book/list")
	public String getList(Model model) {
		return "book/list";
	}
	
	@PostMapping("book/create")
	public String postCreate(@Validated BookForm form, BindingResult result, Model model) {
		// ISBNコードは登録されているデータ内で一意である必要があるため、
		// 既に登録済みであることをチェックしています。
		if(service.existsIsbn(form.getIsbn())) {
			// ISBNコードが既に使われていれば、
			// エラーとなるフィールド(isbn)とエラーコード(existsIsbn)を設定します。
			result.rejectValue("isbn", "existsIsbn");
		}
	 
		// エラーがあれば登録フォームを再表示させます。
		if(result.hasErrors()) {
			return getAdd(form, model);
		}
		// formで受け取った値をエンティティであるBookに格納します。
		Book book = new Book(form);
		// 登録処理をServiceに委譲します。
		service.save(book);
		// 検索一覧画面に遷移します。
		return "book/list";
	}
	
	@GetMapping("book/search")
	public String getSearch(@RequestParam(required = false) Map<String,String> params, Model model) {
//		public ResponseEntity<BookResponseDto> getSearch(@RequestParam(required = false) Map<String,String> params, Model model) {
		// 取得したリクエストパラメータを引数として、サービス層に検索処理を委譲
		List<Book> bookList = service.selectMany(params);

        // 「出版年月日」と「ISBN」で降順にソート
        Collections.sort(bookList, Book.getDescendingComparator());
        
        // セッションに登録
        session.setAttribute("bookList", bookList);
        
		// 検索結果のオブジェクトをmodelに格納
		model.addAttribute("books", bookList);
		
	     // 画面遷移用のパス
        String redirectPath = "/book/list";
        
        // レスポンス内容をDtoに設定
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setResponseBookList(bookList);
        bookResponseDto.setRedirectPath(redirectPath);
        
		// 取得結果をReturn
//        return ResponseEntity.ok(bookResponseDto);
		return redirectPath;
	}
	
    @Autowired
    DownloadHelper downloadHelper;

    /**
     * CsvMapperで、csvを作成する。
     * @return csv(String)
     * @throws JsonProcessingException
     */
    public String getCsvText() throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        //文字列にダブルクオートをつける
        mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
        //ヘッダをつける
        CsvSchema schema = mapper.schemaFor(Book.class).withHeader();
        @SuppressWarnings("unchecked")
		List<Book> bookList = (List<Book>)session.getAttribute("bookList");
        return mapper.writer(schema).writeValueAsString(bookList);
    }

    /**
     * csvをダウンロードする。
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download/csv", method = RequestMethod.POST)
    public ResponseEntity<byte[]> download() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        downloadHelper.addContentDisposition(headers, "book_list.csv");
        return new ResponseEntity<>(getCsvText().getBytes("MS932"), headers, HttpStatus.OK);
    }
}
