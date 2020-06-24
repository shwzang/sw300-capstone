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

package com.example.demo.diary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "Diary")
public class Diary implements Serializable {
	public Diary() {
	}

	@Column(name = "ID", nullable = false, length = 20)
	@Id
	@GeneratedValue(generator = "DIARY_ID_GENERATOR")
	@GenericGenerator(name = "DIARY_ID_GENERATOR", strategy = "native")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@Cascade({ CascadeType.LOCK })
	@JoinColumns(value = {
			@JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false) }, foreignKey = @ForeignKey(name = "FKDiary492772"))
	private User user;

	@Column(name = "Title", nullable = true, length = 255)
	private String title;

	@Column(name = "`Date`", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "Content", nullable = true, length = 255)
	private String content;

	@OneToMany(mappedBy = "diary", targetEntity = DiaryGoal.class, cascade = javax.persistence.CascadeType.ALL)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.LOCK })
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<DiaryGoal> diaryGoals = new ArrayList();

	private void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return id;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return title;
	}

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return date;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return content;
	}

	public void setUser(User value) {
		this.user = value;
	}

	public User getUser() {
		return user;
	}

	public List<DiaryGoal> getDiaryGoals() {
		return diaryGoals;
	}

	public void setDiaryGoals(List<DiaryGoal> diaryGoals) {
		this.diaryGoals = diaryGoals;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
