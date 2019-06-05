package idu.cs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.entity.QuestionEntity;

@Repository
public interface QuestionRepository 
	extends JpaRepository<QuestionEntity, Long> {
	
	QuestionEntity findByWriter(String writer);
}
