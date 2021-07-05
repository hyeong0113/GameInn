package com.cmpt276.gameinn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpt276.gameinn.models.User;

@Repository
public interface  IUserRepository extends JpaRepository<User, Long>, IUserRepositoryCustom {
}