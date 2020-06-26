package com.example.demo.diary;

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

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.diaryGoal.DiaryGoal;

@Controller
public class DiaryController {

	@Autowired
	DiaryRepository diaryRepo;

	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/diaries")
	public @ResponseBody Collection<Diary> getAllDiaries() {
		return diaryRepo.findAll();
	}

	@RequestMapping(value = "/diaries/{userId}")
	public @ResponseBody Collection<Diary> getDiaries(@PathVariable("userId") Long userId) {
		return diaryRepo.findByUser_Id(userId);
	}

	@RequestMapping(value = "/diaries/{userId}", method = RequestMethod.POST)
	public @ResponseBody Diary addDiary(@RequestBody Diary diary, @PathVariable("userId") long userId,
			HttpServletResponse res) {

		User user = userRepo.findById(userId).orElse(null);

		if (user == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			diary.setUser(user);
			diary.setDate(new Date());
			
			for(DiaryGoal diaryGoal : diary.getDiaryGoals()) {
				diaryGoal.setDiary(diary);
			}

			return diaryRepo.save(diary);
		}
	}

	@RequestMapping(value = "/diaries/{id}", method = RequestMethod.PUT)
	public @ResponseBody boolean modifyDiary(@PathVariable("id") long id, @RequestBody Diary newDiary,
			HttpServletResponse res) {

		Diary diary = diaryRepo.findById(id).orElse(null);

		if (diary == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			diary.setTitle(newDiary.getTitle());
			diary.setContent(newDiary.getContent());
			diaryRepo.save(diary);
			res.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
	}

	// deleteDiary
	@RequestMapping(value = "/diaries/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean removeDiary(@PathVariable("id") long id, HttpServletResponse res) {

		Diary diary = diaryRepo.findById(id).orElse(null);

		if (diary == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			diaryRepo.delete(diary);
			return true;
		}
	}
}
