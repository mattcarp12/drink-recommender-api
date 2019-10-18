package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}
