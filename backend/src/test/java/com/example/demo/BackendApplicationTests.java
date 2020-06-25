package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.demo.User.User;
import com.example.demo.User.UserController;
import com.example.demo.User.UserRepository;
import com.example.demo.diary.Diary;
import com.example.demo.diary.DiaryController;
import com.example.demo.diaryGoal.DiaryGoal;
import com.example.demo.diaryGoal.DiaryGoalController;
import com.example.demo.goal.Goal;
import com.example.demo.goal.GoalController;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		BackendApplication.main(new String[0]);
	}

	MockHttpServletResponse res = new MockHttpServletResponse();

	// use case flow 1: 회원가입을 한다.
	// use case post-condition: 회원 가입한 정보를 기반으로 유저 데이터가 추가된다.
	@Test
	void testAddandDeleteUser(@Autowired UserController controller, @Autowired UserRepository repo) {
		User user = new User();
		user.setId(new Long(11111));
		user.setUserId("Hong");
		user.setPassword("12341234");
		user.setName("홍길동");

		// Add User
		long beforeParentCounts = repo.count();
		User returnedUser = controller.addUser(user);
		Assertions.assertEquals(beforeParentCounts + 1, repo.count());

		// User ID, 이름, 비밀번호는 입력한 값과 같아야 함
		Assertions.assertEquals(user.getUserId(), returnedUser.getUserId());
		Assertions.assertEquals(user.getName(), returnedUser.getName());
		Assertions.assertEquals(user.getPassword(), returnedUser.getPassword());

		// ID, toString은 입력한 정보와 다름
		Assertions.assertNotNull(returnedUser.getId());
		Assertions.assertNotNull(returnedUser.toString());

		// -----------
		// Delete User
		controller.removeUser(returnedUser.getId(), res);
	}

	@Test
	void testGetUsers(@Autowired UserController controller) {
		ArrayList<User> userList = (ArrayList<User>) controller.getUsers();
		Assertions.assertNotNull(userList);
	}

	@Test
	void testGetUser(@Autowired UserController controller) {

		ArrayList<User> userList = (ArrayList<User>) controller.getUsers();
		Assertions.assertNotNull(userList);

		String userId = userList.get(0).getUserId();

		User user = controller.getUserByUserId(userId, res);
		Long id = user.getId();

		User returnedUser = controller.getUser(id);
		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(returnedUser.getUserId(), userId);
	}

	@Test
	void testGetUserByUserId(@Autowired UserController controller) {

		ArrayList<User> userList = (ArrayList<User>) controller.getUsers();
		Assertions.assertNotNull(userList);

		String userId = userList.get(0).getUserId();
		String noUserId = "~~~";

		User user = controller.getUserByUserId(userId, res);
		Assertions.assertNotNull(user);

		User noUser = controller.getUserByUserId(noUserId, res);
		Assertions.assertNull(noUser);
	}

	@Test
	void testLogin(@Autowired UserController controller) {
		ArrayList<User> userList = (ArrayList<User>) controller.getUsers();
		Assertions.assertNotNull(userList);

		String userId = userList.get(0).getUserId();
		String userPassword = userList.get(0).getPassword();
		String wrongUserPassword = userList.get(0).getUserId() + "!";

		User user = controller.logIn(userId, userPassword, res);
		Assertions.assertNotNull(user);

		User noUser = controller.logIn("-1", userPassword, res);
		Assertions.assertNull(noUser);

		User wrongUser = controller.logIn(userId, wrongUserPassword, res);
		Assertions.assertNull(wrongUser);
	}

	@Test
	void testModifyAndDeleteUser(@Autowired UserController controller, @Autowired UserRepository repo) {

		// 사전 준비 - User 생성
		User testUser = new User();
		testUser.setId(new Long(11111));
		testUser.setUserId("Hwang");
		testUser.setPassword("12341234");
		testUser.setName("황길동");
		String userId = testUser.getUserId();

		User returnedUser = controller.addUser(testUser);
		User user = controller.getUserByUserId(userId, res);
		Assertions.assertNotNull(user);

		// ------------------------------------------------------
		User newUser = new User();
		newUser.setPassword("12344321");
		newUser.setName("고길동");

		controller.modifyUser(user.getId(), newUser, res);

		Long wrongId = new Long(-1);
		controller.modifyUser(wrongId, newUser, res);

		returnedUser = controller.getUserByUserId(userId, res);

		Assertions.assertNotNull(returnedUser);
		Assertions.assertEquals(newUser.getPassword(), returnedUser.getPassword());
		Assertions.assertEquals(newUser.getName(), returnedUser.getName());

		// ----------------------------------
		// Delete User
		long beforeParentCounts = repo.count();

		boolean isRemoved = controller.removeUser(returnedUser.getId(), res);
		User removedUser = controller.getUserByUserId(userId, res);
		Assertions.assertNull(removedUser);
		Assertions.assertEquals(isRemoved, true);
		Assertions.assertEquals(beforeParentCounts - 1, repo.count());

		isRemoved = controller.removeUser(returnedUser.getId(), res);
		Assertions.assertEquals(isRemoved, false);
	}

	@Test
	void testAddandDeleteDiary(@Autowired DiaryController controller, @Autowired UserController userController) {
		ArrayList<User> userList = (ArrayList<User>) userController.getUsers();
		Assertions.assertNotNull(userList);

		User user = userList.get(0);

		Long diaryId = new Long(11111);
		Diary diary = new Diary();
		diary.setId(diaryId);
		diary.setTitle("Test");
		diary.setContent("Content");
		diary.setUser(user);
		diary.setDate(new Date());

		Collection<Diary> beforeDiaries = controller.getDiaries(user.getId());
		user.setDiaries((ArrayList<Diary>) beforeDiaries);

		Diary returnedDiary = controller.addDiary(diary, user.getId(), res);

		int AfterCreation = controller.getDiaries(user.getId()).size();
		Assertions.assertEquals(beforeDiaries.size() + 1, AfterCreation);
		Assertions.assertNotNull(returnedDiary);
		Assertions.assertNotNull(returnedDiary.getId());
		Assertions.assertNotNull(returnedDiary.getUser());
		Assertions.assertNotNull(returnedDiary.getDate());
		Assertions.assertNotNull(returnedDiary.toString());

		Assertions.assertEquals(diary.getTitle(), returnedDiary.getTitle());
		Assertions.assertEquals(diary.getContent(), returnedDiary.getContent());

		Diary wrongAddedDiary = controller.addDiary(diary, -1, res);
		Assertions.assertNull(wrongAddedDiary);

		// -------------------------------
		// Remove
		boolean isRemoved = controller.removeDiary(returnedDiary.getId(), res);
		Assertions.assertEquals(isRemoved, true);
		int AfterRemove = controller.getDiaries(user.getId()).size();
		Assertions.assertEquals(AfterCreation - 1, AfterRemove);

		boolean wrongDiaryRemoved = controller.removeDiary(-1, res);
		Assertions.assertEquals(wrongDiaryRemoved, false);
	}

	@Test
	void testGetAllDiary(@Autowired DiaryController controller) {
		ArrayList<Diary> diaryList = (ArrayList<Diary>) controller.getAllDiaries();
		Assertions.assertNotNull(diaryList);
	}

	@Test
	void testGetDiaryAndDiaryGoal(@Autowired DiaryController controller, @Autowired UserController userController) {
		ArrayList<User> userList = (ArrayList<User>) userController.getUsers();
		Assertions.assertNotNull(userList);

		User user = userList.get(0);

		ArrayList<Diary> diaryList = (ArrayList<Diary>) controller.getDiaries(user.getId());
		Assertions.assertNotNull(diaryList);
	}

	@Test
	void testModifyDiary(@Autowired DiaryController controller, @Autowired UserController userController) {
		ArrayList<Diary> diaryList = (ArrayList<Diary>) controller.getAllDiaries();
		Assertions.assertNotNull(diaryList);

		Diary diary = diaryList.get(0);

		Diary newDiary = new Diary();
		newDiary.setContent(diary.getContent() + "!");
		newDiary.setTitle(diary.getTitle() + "!");
		newDiary.setId(diary.getId());
		newDiary.setDate(diary.getDate());
		newDiary.setUser(diary.getUser());

		boolean isModified = controller.modifyDiary(diary.getId(), newDiary, res);
		Assertions.assertEquals(isModified, true);

		ArrayList<Diary> modifiedDiaryList = (ArrayList<Diary>) controller.getAllDiaries();
		Assertions.assertNotNull(modifiedDiaryList);

		Diary returnedDiary = modifiedDiaryList.get(0);

		Assertions.assertEquals(newDiary.getContent(), returnedDiary.getContent());
		Assertions.assertEquals(newDiary.getTitle(), returnedDiary.getTitle());

		boolean wrongModified = controller.modifyDiary(-1, newDiary, res);
		Assertions.assertEquals(wrongModified, false);

		// 원상 복구
		controller.modifyDiary(diary.getId(), diary, res);
	}

	@Test
	void testGetAllGoals(@Autowired GoalController controller) {
		ArrayList<Goal> goals = (ArrayList<Goal>) controller.getAllGoals();
		Assertions.assertNotNull(goals);
	}

	@Test
	void testGetGoals(@Autowired GoalController controller, @Autowired UserController userController) {
		ArrayList<User> userList = (ArrayList<User>) userController.getUsers();
		Assertions.assertNotNull(userList);
		User user = userList.get(0);
		Assertions.assertNotNull(user);

		ArrayList<Goal> goals = (ArrayList<Goal>) controller.getGoals(user.getId());
		Assertions.assertNotNull(goals);
	}

	@Test
	void testAddGoal(@Autowired GoalController controller, @Autowired UserController userController) {
		Goal goal = new Goal();
		goal.setId(new Long(111));
		goal.setDescription("Description");
		goal.setInProgress(true);
		goal.setName("Name");

		ArrayList<User> userList = (ArrayList<User>) userController.getUsers();
		Assertions.assertNotNull(userList);
		User user = userList.get(0);

		goal.setUser(user);

		Goal returnedGoal = controller.addGoal(goal, user.getId(), res);
		Assertions.assertNotNull(returnedGoal.getId());
		Assertions.assertNotNull(returnedGoal.getUser());
		Assertions.assertNotNull(returnedGoal.toString());

		Assertions.assertEquals(returnedGoal.getDescription(), goal.getDescription());
		Assertions.assertEquals(returnedGoal.getName(), goal.getName());
		Assertions.assertEquals(returnedGoal.isInProgress(), goal.isInProgress());

		Goal wrongGoal = controller.addGoal(goal, -1, res);
		Assertions.assertNull(wrongGoal);

		// --------------------------------------------------------
		// Remove Goal

		boolean isRemoved = controller.removeGoal(returnedGoal.getId(), res);
		Assertions.assertEquals(isRemoved, true);

		boolean wrongRemoved = controller.removeGoal(-1, res);
		Assertions.assertEquals(wrongRemoved, false);
	}

	@Test
	void testModifyGoal(@Autowired GoalController controller, @Autowired UserController userController) {
		ArrayList<Goal> goals = (ArrayList<Goal>) controller.getAllGoals();
		Assertions.assertNotNull(goals);

		Goal oldGoal = goals.get(0);

		Goal newGoal = new Goal();
		newGoal.setId(oldGoal.getId());
		newGoal.setDescription(oldGoal.getDescription() + "1");
		newGoal.setName(oldGoal.getName() + "1");
		newGoal.setInProgress(false);

		boolean isModified = controller.modifyGoal(oldGoal.getId(), newGoal, res);
		Assertions.assertEquals(isModified, true);

		ArrayList<Goal> modifiedGoals = (ArrayList<Goal>) controller.getAllGoals();
		Assertions.assertNotNull(modifiedGoals);

		Goal goal = modifiedGoals.get(0);
		Assertions.assertEquals(newGoal.getDescription(), goal.getDescription());
		Assertions.assertEquals(newGoal.getName(), goal.getName());
		Assertions.assertEquals(newGoal.isInProgress(), goal.isInProgress());

		boolean wrongModified = controller.modifyGoal(-1, newGoal, res);
		Assertions.assertEquals(wrongModified, false);

		// 원상복구
		controller.modifyGoal(oldGoal.getId(), oldGoal, res);
	}

	@Test
	void testGetAllDiaryGoals(@Autowired DiaryGoalController controller) {
		ArrayList<DiaryGoal> goals = (ArrayList<DiaryGoal>) controller.getAllDiaryGoals();
		Assertions.assertNotNull(goals);

		DiaryGoal diaryGoal = goals.get(0);
		Assertions.assertNotNull(diaryGoal);
		Assertions.assertNotNull(diaryGoal.toString());
		Assertions.assertNotNull(diaryGoal.getGoal());
		Assertions.assertNotNull(diaryGoal.getDiary());
		Assertions.assertNotNull(diaryGoal.isAchieved());
	}
}
