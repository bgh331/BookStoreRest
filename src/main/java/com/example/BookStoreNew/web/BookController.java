package com.example.BookStoreNew.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.BookStoreNew.domain.Book;
import com.example.BookStoreNew.domain.BookRepository;
import com.example.BookStoreNew.domain.CategoryRepository;



@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	@Autowired
	private CategoryRepository Crepository;

	@RequestMapping(value = { "/", "booklist" })
	public String Booklist(Model model) {
		model.addAttribute("books", repository.findAll());
		return "booklist";
	}
	
	 @RequestMapping(value="/books", method = RequestMethod.GET)
	    public @ResponseBody List<Book> bookListRest() {	
	        return (List<Book>) repository.findAll();
	    } 
	 
	 @RequestMapping(value="/book/{id}", method = RequestMethod.GET)
	    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {	
	    	return repository.findById(bookId);
	    } 
	
	@RequestMapping(value = { "/add" })
	public String addbook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categories", Crepository.findAll());
		return "addbook";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Book book) {
		repository.save(book);
		return "redirect:booklist";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String DeleteBook(@PathVariable("id") Long Bookid, Model model) {
		repository.deleteById(Bookid);
		return "redirect:../booklist";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editBook(@PathVariable("id") Long bookId, Model model) {
		// Haetaan tietokannasta SQL-lauseella kirja, jolla on tämä id
		// ja lisätään modeliin
		model.addAttribute("book", repository.findById(bookId));
		model.addAttribute("categories", Crepository.findAll());
		return "editbook";
	}

}
