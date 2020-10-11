package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.DevelopmentStandards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevelopmentStandardsRepository extends JpaRepository<DevelopmentStandards, Integer> {
}
