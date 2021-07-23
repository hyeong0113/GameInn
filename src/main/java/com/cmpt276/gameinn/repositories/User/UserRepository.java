package com.cmpt276.gameinn.repositories.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmpt276.gameinn.models.User;

public class UserRepository implements IUserRepositoryCustom {
	@PersistenceContext private EntityManager entityManager;

	public User findUserBySub(String sub) {
		User found = (User)entityManager.find(User.class, sub);
		return found;
	}
}
