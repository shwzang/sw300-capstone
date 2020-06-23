package com.example.demo.goal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GoalRepository extends JpaRepository<Goal, Long>{
	List<Goal> findByUser_Id(@Param(value="userId") Long userId);
}
