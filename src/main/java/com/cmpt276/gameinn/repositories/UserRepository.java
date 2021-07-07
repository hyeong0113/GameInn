package com.cmpt276.gameinn.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmpt276.gameinn.models.User;

// import com.cmpt276.gameinn.repositories.*;

public class UserRepository implements IUserRepositoryCustom {
	@PersistenceContext
    private EntityManager entityManager;

    public User findUserByEmail(String email) {
        User found = (User)entityManager.find(User.class, email);
        return found;
    }
}
