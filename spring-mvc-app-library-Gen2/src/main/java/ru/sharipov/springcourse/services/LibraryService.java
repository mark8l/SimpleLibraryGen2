package ru.sharipov.springcourse.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sharipov.springcourse.models.Book;
import ru.sharipov.springcourse.models.Person;
import ru.sharipov.springcourse.repositories.LibraryRepository;

@Service
@Transactional(readOnly = true)
public class LibraryService {

	private final LibraryRepository libraryRepository;

	@Autowired
	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}

	public List<Book> findAll(boolean sortByYear) {
		if (sortByYear) {
			return libraryRepository.findAll(Sort.by("yearOfPublishing"));
		} else {
			return libraryRepository.findAll();
		}
	}

	public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
		if (sortByYear) {
			return libraryRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublishing")))
					.getContent();
		} else {
			return libraryRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
		}
	}

	public List<Book> searchByTitle(String querry) {
		return libraryRepository.findByTitleStartingWith(querry);
	}

	public Book findOne(int id) {
		Optional<Book> foundBook = libraryRepository.findById(id);
		return foundBook.orElse(null);
	}

	public Book findByTitle(String title) {
		Optional<Book> bookOptional = libraryRepository.findByTitle(title);
		return bookOptional.orElse(null);
	}

	public List<Book> findByOwner(Person owner) {
		return libraryRepository.findByOwner(owner);
	}

	public Optional<Book> show(Book book) {
		return libraryRepository.findByTitle(book.getTitle());
	}

	public Person getBookOwner(int id) {

		return libraryRepository.findById(id).map(Book::getOwner).orElse(null);

	}

	@Transactional
	public void save(Book book) {
		libraryRepository.save(book);
	}

	@Transactional
	public void update(int id, Book updatedBook) {
		Book bookToBeUpdated = libraryRepository.findById(id).get();
		updatedBook.setBookId(id);
		updatedBook.setOwner(bookToBeUpdated.getOwner());
		libraryRepository.save(updatedBook);
	}

	@Transactional
	public void delete(int id) {
		libraryRepository.deleteById(id);
	}

	@Transactional
	public void setPersonToBook(Person owner, int book_id) {
		libraryRepository.findById(book_id).ifPresent(book -> {
			book.setOwner(owner);
			book.setTakenAt(new Date());
		});
	}

	@Transactional
	public void setBookFree(int id) {
		libraryRepository.findById(id).ifPresent(book -> {
			book.setOwner(null);
			book.setTakenAt(null);
		});
	}
}
