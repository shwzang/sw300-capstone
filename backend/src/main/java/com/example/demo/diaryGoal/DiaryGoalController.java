package com.example.demo.diaryGoal;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.diary.Diary;

@Controller
public class DiaryGoalController {

	@Autowired
	DiaryGoalRepository diaryGoalRepo;

	@RequestMapping(value = "/diaryGoals")
	public @ResponseBody Collection<DiaryGoal> getAllDiaryGoals() {
		return diaryGoalRepo.findAll();
	}

	// deleteDiary
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
