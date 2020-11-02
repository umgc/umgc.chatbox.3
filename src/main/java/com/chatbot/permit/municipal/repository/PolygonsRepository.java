package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.Polygons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PolygonsRepository extends JpaRepository<Polygons, Integer> {
  @Query("SELECT ZONE_CODE FROM Polygons WHERE POLYGON_ID = ?1")
  public String findZONECODEByPOLYGONID(int id);
}
