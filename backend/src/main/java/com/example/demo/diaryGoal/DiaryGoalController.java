package com.example.demo.diaryGoal;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DiaryGoalController {

	@Autowired
	DiaryGoalRepository diaryGoalRepo;
	
	@RequestMapping(value = "/diaryGoals")
	public @ResponseBody Collection<DiaryGoal> getAllDiaryGoals() {
		return diaryGoalRepo.findAll();
	}
}
