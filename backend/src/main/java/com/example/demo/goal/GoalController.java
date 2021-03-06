package com.example.demo.goal;

import java.util.Collection;
import java.util.List;

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

@Controller
public class GoalController {

	@Autowired
	GoalRepository goalRepo;

	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/goals")
	public @ResponseBody Collection<Goal> getAllGoals() {
		return goalRepo.findAll();
	}

	@RequestMapping(value = "/goals/{userId}")
	public @ResponseBody Collection<Goal> getGoals(@PathVariable("userId") Long userId) {
		return goalRepo.findByUser_Id(userId);
	}

	@RequestMapping(value = "/goals/{userId}", method = RequestMethod.POST)
	public @ResponseBody Goal addGoal(@RequestBody Goal goal, @PathVariable("userId") long userId,
			HttpServletResponse res) {

		User user = userRepo.findById(userId).orElse(null);
		
		if (user == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			goal.setUser(user);
			goal.setInProgress(true);
			
			return goalRepo.save(goal);
		}
	}

	@RequestMapping(value = "/goals/{id}", method = RequestMethod.PUT)
	public @ResponseBody boolean modifyGoal(@PathVariable("id") long id, @RequestBody Goal newGoal, HttpServletResponse res) {

		Goal goal = goalRepo.findById(id).orElse(null);

		if (goal == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			goal.setName(newGoal.getName());
			goal.setDescription(newGoal.getDescription());
			goal.setInProgress(newGoal.isInProgress());
			goalRepo.save(goal);
			res.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
	}

	// Deletes
	@RequestMapping(value = "/goals/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean removeGoal(@PathVariable("id") long id, HttpServletResponse res) {

		Goal goal = goalRepo.findById(id).orElse(null);

		if (goal == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			goalRepo.delete(goal);
			return true;
		}
	}
}
