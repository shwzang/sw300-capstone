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
package com.example.demo.goal;

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

import com.example.demo.User.User;
import com.example.demo.diaryGoal.DiaryGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Proxy(lazy = false)
@Table(name = "Goal")
public class Goal implements Serializable {
	public Goal() {
	}

	@Column(name = "ID", nullable = false, length = 20)
	@Id
	@GeneratedValue(generator = "GOAL_ID_GENERATOR")
	@GenericGenerator(name = "GOAL_ID_GENERATOR", strategy = "native")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@Cascade({ CascadeType.LOCK })
	@JoinColumns(value = {
			@JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false) }, foreignKey = @ForeignKey(name = "FKGoal292244"))
	private User user;

	@Column(name = "Name", nullable = true, length = 255)
	private String name;

	@Column(name = "Description", nullable = true, length = 255)
	private String description;

	@Column(name = "InProgress", nullable = false, length = 1)
	private boolean inProgress;

	@JsonIgnore
	@OneToMany(mappedBy = "goal", targetEntity = DiaryGoal.class)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.LOCK })
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<DiaryGoal> diaryGoal = new ArrayList();

	private void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return id;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return description;
	}

	public void setInProgress(boolean value) {
		this.inProgress = value;
	}

	public boolean getInProgress() {
		return inProgress;
	}

	public void setUser(User value) {
		this.user = value;
	}

	public User getUser() {
		return user;
	}

	public void setDiaryGoal(List value) {
		this.diaryGoal = value;
	}

	public List getDiaryGoal() {
		return diaryGoal;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
