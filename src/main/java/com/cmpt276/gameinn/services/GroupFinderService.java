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

    public GroupFinder addGroupFinder(String title, String gameTitle, RequiredLevel requiredLevel, int totalPlayers, int currentPlayers, String description, String writerid) {
        GroupFinder groupFinder = new GroupFinder(title, gameTitle, requiredLevel, totalPlayers, currentPlayers, description, writerid);
        return repository.save(groupFinder);
    }

    public List<GroupFinder> getGroupFinders() {
        return repository.findAll();
    }

    public GroupFinder getGroupFinderByID (Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
    }

    public GroupFinder updateGroupFinder(GroupFinder groupfinder) {
        GroupFinder found = repository.findById(groupfinder.getId()).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + groupfinder.getId()));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + groupfinder.getId());
        }

        found.setTitle(groupfinder.getTitle());
        found.setTitle(groupfinder.getGameTitle());
       
        repository.save(found);
        return found;

    }

    public void deleteGroupFinder(Long id){
        GroupFinder found = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + id);
        }

        repository.delete(found);
    }

}



// Implement basic Create, Read, Update, Delete methods first
// For reads, getAll() getById()
// Then implement custom methods