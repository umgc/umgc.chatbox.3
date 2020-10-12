package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.Polygons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolygonsRepository extends JpaRepository<Polygons, Integer> {
}
