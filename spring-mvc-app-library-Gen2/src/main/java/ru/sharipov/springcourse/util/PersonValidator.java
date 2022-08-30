package ru.sharipov.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.sharipov.springcourse.models.Person;
import ru.sharipov.springcourse.services.PeopleService;

@Component
public class PersonValidator implements Validator {

	private final PeopleService peopleService;
	
	@Autowired
	public PersonValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Person person = (Person) target;
		
		if (person.getPatronymic() != null) {
			if (peopleService.findByNameAndSurnameAndPatronymic(person).isPresent()) {
				if (!(peopleService.findByNameAndSurnameAndPatronymic(person).get()).equals(person)) {
					errors.rejectValue("name", "", "Person with this name, surname, patronymic and birthdate is already exist!");
					errors.rejectValue("surname", "", "Person with this name, surname, patronymic and birthdate is already exist!");
					errors.rejectValue("patronymic", "", "Person with this name, surname, patronymic and birthdate is already exist!");
					errors.rejectValue("birthdate", "", "Person with this name, surname, patronymic and birthdate is already exist!");
				}
			}
		}
		else {
			if (peopleService.findByNameAndSurname(person).isPresent()) {
				if (!(peopleService.findByNameAndSurname(person).get()).equals(person)) {
					errors.rejectValue("name", "", "Person with this name, surname and birthdate is already exist!");
					errors.rejectValue("surname", "", "Person with this name, surname and birthdate is already exist!");
					errors.rejectValue("birthdate", "", "Person with this name, surname and birthdate is already exist!");
				}
			}
		}
	}

}
