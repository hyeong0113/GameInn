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

    public GroupFinder getGroupFinderByID (Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
    }

    public GroupFinder updateGroupFinder(GroupFinder groupfinder) {
        GroupFinder found = repository.findGroupFinderByTitle(GroupFinder.getById());
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + GroupFinder.getById());
        }

        found.setTitle(GroupFinder.getTitle());
        found.setTitle(GroupFinder.getGameTitle());
       
        repository.save(found);
        return found;

    }

    public void deleteGroupFinder(String sub){
        GroupFinder found = repository.findGroupFinderByTitle(title);
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + title);
        }

        repository.delete(found);
    }

}



// Implement basic Create, Read, Update, Delete methods first
// For reads, getAll() getById()
// Then implement custom methods