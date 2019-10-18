package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponseRepository extends JpaRepository<UserResponse, Integer> {
}
