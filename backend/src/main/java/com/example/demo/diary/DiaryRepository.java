package com.example.demo.diary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	List<Diary> findByUser_Id(@Param(value = "userId") Long userId);

}
