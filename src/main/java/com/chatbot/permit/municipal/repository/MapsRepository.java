package com.chatbot.permit.municipal.repository;

import com.chatbot.permit.municipal.model.Maps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapsRepository extends JpaRepository<Maps, Integer> {
    List<Maps> findByFKPOLYGONID(int id);
    @Query(value = "SELECT DISTINCT FK_POLYGON_ID FROM maps", nativeQuery = true)
    List<Integer> findMapsDistinctBy();
}
