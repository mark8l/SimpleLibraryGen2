package ru.sharipov.springcourse.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sharipov.springcourse.models.Book;
import ru.sharipov.springcourse.models.Person;
import ru.sharipov.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	
	private final PeopleRepository peopleRepository;
	
	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}
	
	public List<Person> findAll(){
		return peopleRepository.findAll();
	}
	
	public Person findOne(int id) {
		Optional<Person> foundPerson = peopleRepository.findById(id);
		return foundPerson.orElse(null);
	}
	
	@Transactional
	public void save(Person person) {
		peopleRepository.save(person);
	}
	
	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setPerson_id(id);
		peopleRepository.save(updatedPerson);
	}
	
	@Transactional
	public void delete(int id) {
		peopleRepository.deleteById(id);
	}
	
	public Optional<Person> findByNameAndSurnameAndPatronymic(Person person) {
		return peopleRepository.findByNameAndSurnameAndPatronymicAndBirthdate(person.getName(),
																			  person.getSurname(),
																			  person.getPatronymic(),
																			  person.getBirthdate());
	}
	
	public Optional<Person> findByNameAndSurname(Person person) {
		return peopleRepository.findByNameAndSurnameAndBirthdate(person.getName(),
																 person.getSurname(),
																 person.getBirthdate());
	}
	
	public List<Book> getBooksByPersonId(int id) {
		Optional<Person> person = peopleRepository.findById(id);
		
		if (person.isPresent()) {
			Hibernate.initialize(person.get().getBooks());
			
			person.get().getBooks().forEach(book -> {
				long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
				if (diffInMillis > 864000000) {
					book.setExpired(true);
				}
				
			});
			return person.get().getBooks();
		}
		else {
			return Collections.emptyList();
		}
	}

}
