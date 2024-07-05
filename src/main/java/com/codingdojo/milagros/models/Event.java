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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Event name is required.")
	@Size(min=2, message="Event name needs at least 2 chars")
	private String eventName;
	
	@Future //fecha sea en el futuro
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message="event date is required")
	private Date eventDate;
	
	@NotEmpty(message="location is required")
	private String eventLocation;
	@NotEmpty(message="province is required")
	private String eventProvince;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User host;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_join_events",
			joinColumns=@JoinColumn(name="event_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			
			)
	private List<User> joinedUsers;
	
	
	@OneToMany(mappedBy="event",fetch=FetchType.LAZY)
	private List<Message> eventMessages;
	
	public Event () {}

	public Long getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public String getEventProvince() {
		return eventProvince;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public void setEventProvince(String eventProvince) {
		this.eventProvince = eventProvince;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	public User getHost() {
		return host;
	}

	public List<User> getJoinedUsers() {
		return joinedUsers;
	}

	public List<Message> getEventMessages() {
		return eventMessages;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public void setJoinedUsers(List<User> joinedUsers) {
		this.joinedUsers = joinedUsers;
	}

	public void setEventMessages(List<Message> eventMessages) {
		this.eventMessages = eventMessages;
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
