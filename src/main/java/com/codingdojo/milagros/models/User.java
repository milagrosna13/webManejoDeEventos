package com.codingdojo.milagros.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="First name is required.")
	@Size(min=2, message="First name needs at least 2 chars")
	private String firstName;
	
	@NotEmpty(message="Last name is required.")
	@Size(min=2, message="Last name needs at least 2 chars")
	private String lastName;
	
	@NotEmpty(message="Email is required.")
	@Email(message="Invalid email") //Verifica que sea un correo electrónico válido
	private String email;
	
	@NotEmpty(message="Password is required.")
	@Size(min=6, message="Password needs at least 6 chars")
	private String password;
	
	@Transient //No se guarde el dato en BD
	@NotEmpty(message="Confirmation is required.")
	@Size(min=6, message="Confirmation needs at least 6 chars")
	private String confirm;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@NotEmpty(message="location is required")
	private String location;
	@NotEmpty(message="province is required")
	private String province;
	
	@OneToMany(mappedBy="host",fetch=FetchType.LAZY)
	private List<Event> plannedEvents;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_join_events",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="event_id")
			)
	private List<Event> joinedEvents;
	
	
	@OneToMany(mappedBy="author",fetch=FetchType.LAZY)
	private List<Message> messages;
	
	
	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	public String getLocation() {
		return location;
	}

	public String getProvince() {
		return province;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	
	
	
	public List<Event> getPlannedEvents() {
		return plannedEvents;
	}

	public List<Event> getJoinedEvents() {
		return joinedEvents;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setPlannedEvents(List<Event> plannedEvents) {
		this.plannedEvents = plannedEvents;
	}

	public void setJoinedEvents(List<Event> joinedEvents) {
		this.joinedEvents = joinedEvents;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
