package com.telusko.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.data.domain.AppUser;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
		AppUser findByUserName(String username);
}
