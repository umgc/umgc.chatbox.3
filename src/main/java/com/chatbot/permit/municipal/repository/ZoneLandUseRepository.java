package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.ZoneLandUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneLandUseRepository extends JpaRepository<ZoneLandUse, Integer> {
}
