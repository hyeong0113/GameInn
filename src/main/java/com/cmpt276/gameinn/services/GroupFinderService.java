package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.GroupFinder.RequiredLevel;
import com.cmpt276.gameinn.repositories.IGroupFinderRepository;

@Service
public class GroupFinderService {
    @Autowired
    private IGroupFinderRepository repository;

    public GroupFinder addGroupFinder(String title, RequiredLevel requiredLevel) {
        GroupFinder groupFinder = new GroupFinder(title, requiredLevel);
        return repository.save(groupFinder);
    }

    public List<GroupFinder> getGroupFinders() {
        return repository.findAll();
    }

}

// Implement basic Create, Read, Update, Delete methods first
// For reads, getAll() getById()
// Then implement custom methods