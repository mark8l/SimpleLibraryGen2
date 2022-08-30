package ru.sharipov.springcourse.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private int person_id;
	
	@NotEmpty(message = "Name shouldn't be empty")
	@Size(min = 2, max = 30, message = "Name should be between 1 and 30 characters")
	@Pattern(regexp = "[A-Z]\\w+", message = "The first character of name should be capital")
	@Column(name = "name")
	private String name;
	
	@NotEmpty(message = "Surname shouldn't be empty")
	@Size(min = 2, max = 30, message = "Surname should be between 1 and 30 characters")
	@Pattern(regexp = "[A-Z]\\w+", message = "The first character of surname should be capital")
	@Column(name = "surname")
	private String surname;
	
	@Size(min = 0, max = 30, message = "Patronymic should be between 1 and 30 characters")
	@Pattern(regexp = "[A-Z]*\\w*", message = "The first character of patronymic should be capital")
	@Column(name = "patronymic")
	private String patronymic;

	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthdate")
	private Date birthdate;
	
	@OneToMany(mappedBy = "owner")
	private List<Book> books;
	
	public Person(String name, String surname, String patronymic, Date birthdate) {
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
		this.birthdate = birthdate;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Person [person_id=" + person_id +
					 ", name=" + name +
					 ", surname=" + surname +
					 ", patronymic=" + patronymic +
					 ", birthdate=" + birthdate +
					 "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthdate, name, patronymic, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Person other = (Person) obj;
		return Objects.equals(birthdate, other.birthdate) && Objects.equals(name, other.name)
				&& Objects.equals(patronymic, other.patronymic) && Objects.equals(surname, other.surname);
	}
}
