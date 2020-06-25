package com.example.demo.User;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value = "/users")
	public @ResponseBody Collection<User> getUsers() {
		return userRepo.findAll();
	}

	@RequestMapping(value = "/users/{id}")
	public @ResponseBody User getUser(@PathVariable("id") Long id) {

		return userRepo.findById(id).orElse(null);
	}

	@RequestMapping(value = "/users/userId/{userId}")
	public @ResponseBody User getUserByUserId(@PathVariable("userId") String userId, HttpServletResponse res) {
		return userRepo.findByUserId(userId);
	}

	@RequestMapping(value = "/login/{userId}/{password}")
	public @ResponseBody User logIn(@PathVariable("userId") String userId, @PathVariable("password") String password,
			HttpServletResponse res) {
		User user = userRepo.findByUserId(userId);
		if (user == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else if (!user.getPassword().equals(password)) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		} else {
			res.setStatus(HttpServletResponse.SC_OK);
			return user;
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody User addUser(@RequestBody User user) {

		return userRepo.save(user);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public @ResponseBody boolean modifyUser(@PathVariable("id") long id, @RequestBody User newUser,
			HttpServletResponse res) {

		User user = userRepo.findById(id).orElse(null);

		if (user == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			user.setPassword(newUser.getPassword());
			user.setName(newUser.getName());
			userRepo.save(user);
			res.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
	}

	// Deletes
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean removeUser(@PathVariable("id") long id, HttpServletResponse res) {

		User user = userRepo.findById(id).orElse(null);

		if (user == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else {
			userRepo.delete(user);
			return true;
		}
	}
}
