package ru.sharipov.springcourse.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Book")
public class Book {

	@NotEmpty(message = "Title shouldn't be empty")
	@Size(min = 2, max = 100, message = "Title should be between 1 and 50 characters")
	@Pattern(regexp = "[A-Z][\\d\\D.]+", message = "The first character of title should be capital")
	@Column(name = "title")
	private String title;

	@NotEmpty(message = "Name shouldn't be empty")
	@Size(min = 2, max = 30, message = "Name should be between 1 and 30 characters")
	@Pattern(regexp = "[A-Z]\\w+", message = "The first character of name should be capital")
	@Column(name = "author_name")
	private String author_name;

	@NotEmpty(message = "Surname shouldn't be empty")
	@Size(min = 2, max = 30, message = "Surname should be between 1 and 30 characters")
	@Pattern(regexp = "[A-Z]\\w+", message = "The first character of surname should be capital")
	@Column(name = "author_surname")
	private String author_surname;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy")
	@Column(name = "year_of_publishing")
	private Date year_of_publishing;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int book_id;

	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "person_id")
	private Person owner;

	@Column(name = "taken_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date takenAt;
	
	@Transient
	private boolean expired;

	public Book() {
	}

	public Book(String title, String author_name, String author_surname, Date year_of_publishing) {
		this.title = title;
		this.author_name = author_name;
		this.author_surname = author_surname;
		this.year_of_publishing = year_of_publishing;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_surname() {
		return author_surname;
	}

	public void setAuthor_surname(String author_surname) {
		this.author_surname = author_surname;
	}

	public Date getYear_of_publishing() {
		return year_of_publishing;
	}

	public void setYear_of_publishing(Date year_of_publishing) {
		this.year_of_publishing = year_of_publishing;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int id) {
		this.book_id = id;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Date getTakenAt() {
		return takenAt;
	}

	public void setTakenAt(Date takenAtDate) {
		this.takenAt = takenAtDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author_name=" + author_name + ", author_surname=" + author_surname
				+ ", year_of_publishing=" + year_of_publishing + ", book_id=" + book_id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author_name, author_surname, title, year_of_publishing);
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
		Book other = (Book) obj;
		return Objects.equals(author_name, other.author_name) && Objects.equals(author_surname, other.author_surname)
				&& Objects.equals(title, other.title) && Objects.equals(year_of_publishing, other.year_of_publishing);
	}

}
