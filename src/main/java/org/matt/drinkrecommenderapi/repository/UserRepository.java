package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
}
