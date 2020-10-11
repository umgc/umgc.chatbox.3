package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.CityUsesr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityUserRepository extends JpaRepository<CityUsesr, Integer> {
}
