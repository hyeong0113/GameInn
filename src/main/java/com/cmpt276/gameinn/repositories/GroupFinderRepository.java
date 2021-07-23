package com.cmpt276.gameinn.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;

public class GroupFinderRepository implements IGroupFinderRepositoryCustom {
    @PersistenceContext private EntityManager entityManager;

    public GroupFinder findGroupFinderByTitle(String title) {
		GroupFinder found = (GroupFinder)entityManager.find(GroupFinder.class, title);
		return found;
	}

	public List<GroupFinder> findAllGroupFinderByWriterId(Long writerId) {
		User foundUser = (User)entityManager.find(User.class, writerId);
		List<GroupFinder> found = foundUser.getGroupFinders();

		return found;
	}

	public GroupFinder findGroupFinderByWriterId(Long writerId, Long groupFinderId) {
		GroupFinder found = (GroupFinder)entityManager.find(GroupFinder.class, groupFinderId);
		if (found.getUser().getId() != writerId) {
			throw new NullPointerException("writerId is not matched with writerId from groupFinderId.");
		}
		return found;
	}

}
