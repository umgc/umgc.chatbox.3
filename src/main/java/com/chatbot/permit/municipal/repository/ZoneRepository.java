package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {
}
