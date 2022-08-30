package ru.sharipov.springcourse.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.sharipov.springcourse.models.Book;
import ru.sharipov.springcourse.models.Person;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Integer> {
	List<Book> findByOwner(Person owner);
//	List<Book> findByTitle(String title);
	Optional<Book> findByTitle(String title);
	List<Book> findByTitleStartingWith(String title);
}
