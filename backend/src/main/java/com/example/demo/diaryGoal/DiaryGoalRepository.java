package com.example.demo.diaryGoal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DiaryGoalRepository extends JpaRepository<DiaryGoal, Long>{
	List<DiaryGoal> findByDiary_Id(@Param(value="diaryId") Long diaryId);
	
	DiaryGoal findByDiary_IdAndGoal_Id(@Param(value="diaryId") Long diaryId, @Param(value="goalId") Long goalId);
}
