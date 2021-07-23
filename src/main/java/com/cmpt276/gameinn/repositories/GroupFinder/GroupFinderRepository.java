package com.cmpt276.gameinn.repositories.GroupFinder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmpt276.gameinn.models.GroupFinder;

public class GroupFinderRepository implements IGroupFinderRepositoryCustom {
    @PersistenceContext private EntityManager entityManager;
	
    public GroupFinder findGroupFinderByTitle(String title) {
		GroupFinder found = (GroupFinder)entityManager.find(GroupFinder.class, title);
		return found;
	}
}
