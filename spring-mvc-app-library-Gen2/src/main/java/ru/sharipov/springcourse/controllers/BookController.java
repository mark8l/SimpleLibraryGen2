package ru.sharipov.springcourse.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sharipov.springcourse.models.Book;
import ru.sharipov.springcourse.models.Person;
import ru.sharipov.springcourse.services.LibraryService;
import ru.sharipov.springcourse.services.PeopleService;
import ru.sharipov.springcourse.util.BookValidator;

@Controller
@RequestMapping("/book")
public class BookController {

	private final LibraryService libraryService;
	private final PeopleService peopleService;
	private BookValidator bookValidator;

	@Autowired
	public BookController(LibraryService libraryService, PeopleService peopleService, BookValidator bookValidator) {
		this.libraryService = libraryService;
		this.bookValidator = bookValidator;
		this.peopleService = peopleService;
	}

	@GetMapping()
	public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
			@RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

		if (page == null || booksPerPage == null) {
			model.addAttribute("books", libraryService.findAll(sortByYear));
		} else {
			model.addAttribute(libraryService.findWithPagination(page, booksPerPage, sortByYear));
		}

		return "book/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {

		Book book = libraryService.findOne(id);
		model.addAttribute("book", book);
		if (book.getOwner() != null) {
			model.addAttribute("person", book.getOwner());
		} else {
			model.addAttribute("people", peopleService.findAll());
		}
		model.addAttribute("book", book);
		return "book/show";
	}

	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "book/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

		bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors()) {
			return "book/new";
		}
		libraryService.save(book);
		return "redirect:/book";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", libraryService.findOne(id));
		return "book/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
			@PathVariable("id") int id) {

		if (bindingResult.hasErrors()) {
			return "book/edit";
		}
		libraryService.update(id, book);
		return "redirect:/book";

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {

		libraryService.delete(id);
		return "redirect:/book";

	}

	@PatchMapping("/take/{id}")
	public String takeBook(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
		libraryService.setPersonToBook(person, id);
		return "redirect:/book";

	}

	@PatchMapping("/free")
	public String freeBook(@RequestParam("book_id") int book_id) {
		libraryService.setBookFree(book_id);
		return "redirect:/book";

	}
	
	@GetMapping("/search")
	public String searchPage() {
		return "book/search";
	}
	
	@PostMapping("/search")
	public String makeSearch(Model model, @RequestParam("querry") String querry) {
		model.addAttribute("books", libraryService.searchByTitle(querry));
		return "book/search";
	}

}
