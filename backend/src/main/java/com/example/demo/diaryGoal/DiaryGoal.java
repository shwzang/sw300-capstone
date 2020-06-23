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
package com.example.demo.diaryGoal;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import com.example.demo.diary.Diary;
import com.example.demo.goal.Goal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Proxy(lazy = false)
@Table(name = "DiaryGoal")
public class DiaryGoal implements Serializable {
	public DiaryGoal() {
	}

	@Column(name = "ID", nullable = false, length = 20)
	@Id
	@GeneratedValue(generator = "DIARYGOAL_ID_GENERATOR")
	@GenericGenerator(name = "DIARYGOAL_ID_GENERATOR", strategy = "native")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = Diary.class, fetch = FetchType.LAZY)
	@Cascade({ CascadeType.LOCK })
	@JoinColumns(value = {
			@JoinColumn(name = "DiaryID", referencedColumnName = "ID", nullable = false) }, foreignKey = @ForeignKey(name = "FKDiaryGoal199357"))
	private Diary diary;

	@ManyToOne(targetEntity = Goal.class, fetch = FetchType.LAZY)
	@Cascade({ CascadeType.LOCK })
	@JoinColumns(value = {
			@JoinColumn(name = "GoalID", referencedColumnName = "ID", nullable = false) }, foreignKey = @ForeignKey(name = "FKDiaryGoal282636"))
	private Goal goal;

	@Column(name = "IsAchieved", nullable = false, length = 1)
	private boolean isAchieved;

	private void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return id;
	}

	public boolean isAchieved() {
		return isAchieved;
	}

	public void setAchieved(boolean isAchieved) {
		this.isAchieved = isAchieved;
	}

	public void setGoal(Goal value) {
		this.goal = value;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setDiary(Diary value) {
		this.diary = value;
	}

	public Diary getDiary() {
		return diary;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
