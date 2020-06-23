package com.example.demo.diaryGoal;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.diary.Diary;
import com.example.demo.diary.DiaryRepository;
import com.example.demo.goal.Goal;
import com.example.demo.goal.GoalRepository;

@Controller
public class DiaryGoalController {

	@Autowired
	DiaryGoalRepository diaryGoalRepo;

	@Autowired
	GoalRepository goalRepo;

	@Autowired
	DiaryRepository diaryRepo;

	@RequestMapping(value = "/diaryGoals/{diaryId}")
	public @ResponseBody Collection<DiaryGoal> getDiaryGoals(@PathVariable("diaryId") Long diaryId) {
		return diaryGoalRepo.findByDiary_Id(diaryId);
	}

	@RequestMapping(value = "/diaryGoals/{diaryId}/{goalId}", method = RequestMethod.POST)
	public @ResponseBody DiaryGoal addDiaryGoal(@RequestBody DiaryGoal diaryGoal, @PathVariable("diaryId") long diaryId,
			@PathVariable("goalId") long goalId, HttpServletResponse res) {

		Diary diary = diaryRepo.findById(diaryId).orElse(null);
		Goal goal = goalRepo.findById(goalId).orElse(null);
		
		DiaryGoal duplicated = diaryGoalRepo.findByDiary_IdAndGoal_Id(diaryId, goalId);
		
		if (diary == null || goal == null || duplicated != null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			diaryGoal.setDiary(diary);
			diaryGoal.setGoal(goal);

			return diaryGoalRepo.save(diaryGoal);
		}
	}

	@RequestMapping(value = "/diaryGoals/{id}", method = RequestMethod.PUT)
	public @ResponseBody boolean modifyDiaryGoal(@PathVariable("id") long id, @RequestBody boolean isAchieved,
			HttpServletResponse res) {

		DiaryGoal diaryGoal = diaryGoalRepo.findById(id).orElse(null);

		if (diaryGoal == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			diaryGoal.setAchieved(isAchieved);
			diaryGoalRepo.save(diaryGoal);
			res.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
	}

	// ªË¡¶
	@RequestMapping(value = "/diaryGoals/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean removeDiaryGoal(@PathVariable("id") long id, HttpServletResponse res) {

		DiaryGoal diaryGoal = diaryGoalRepo.findById(id).orElse(null);

		if (diaryGoal == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			diaryGoalRepo.delete(diaryGoal);
			return true;
		}
	}
}
