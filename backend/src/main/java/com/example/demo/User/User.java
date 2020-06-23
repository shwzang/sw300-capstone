/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Naviworks10
 * License Type: Purchased
 */
package com.example.demo.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import com.example.demo.diary.Diary;
import com.example.demo.goal.Goal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Proxy(lazy = false)
@Table(name = "`User`")
public class User implements Serializable {
	public User() {
	}

	@Column(name = "ID", nullable = false, length = 20)
	@Id
	@GeneratedValue(generator = "USER_ID_GENERATOR")
	@GenericGenerator(name = "USER_ID_GENERATOR", strategy = "native")
	private Long id;

	@Column(name = "UserId", nullable = true, length = 20)
	private String userId;

	@JsonIgnore
	@Column(name = "Password", nullable = true, length = 20)
	private String password;

	@Column(name = "Name", nullable = true, length = 255)
	private String name;

	@OneToMany(mappedBy = "user", targetEntity = Diary.class)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.LOCK })
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Diary> diaries = new ArrayList();

	@OneToMany(mappedBy = "user", targetEntity = Goal.class)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.LOCK })
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Goal> goals = new ArrayList();

	private void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}

	public List<Diary> getDiaries() {
		return diaries;
	}

	public void setDiaries(List<Diary> diaries) {
		this.diaries = diaries;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
