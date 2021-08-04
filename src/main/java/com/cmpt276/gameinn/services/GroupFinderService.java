package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.GroupFinder.IGroupFinderRepository;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service
public class GroupFinderService {
    @Autowired
    private IGroupFinderRepository groupFinderRepository;

    public GroupFinder addGroupFinder(GroupFinder groupFinder, User user) {
        GroupFinder created = new GroupFinder(groupFinder.getTitle(), groupFinder.getGameTitle(), groupFinder.getGameStyle(),
                                                groupFinder.getRequiredLevel(), groupFinder.getTotalPlayers(), groupFinder.getCurrentPlayers(),
                                                groupFinder.getDescription(), groupFinder.getPassword(), user);
        return groupFinderRepository.save(created);
    }

    public List<GroupFinder> getGroupFinders() {
        List<GroupFinder> groupFinders = groupFinderRepository.findAll();
        groupFinders.sort(Comparator.comparing(GroupFinder::getPostedTime).reversed());
        return groupFinders;
    }

    public GroupFinder getGroupFinderByID(Long id){
        return groupFinderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
    }

    public GroupFinder updateGroupFinder(Long id, GroupFinder groupFinder) throws Exception {
        GroupFinder found = groupFinderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + groupFinder.getId());
        }

        found.setTitle(groupFinder.getTitle());
        found.setGameTitle(groupFinder.getGameTitle());
        found.setGameStyle(groupFinder.getGameStyle());
        found.setRequiredLevel(groupFinder.getRequiredLevel());
        found.setTotalPlayers(groupFinder.getTotalPlayers());
        found.setCurrentPlayers(groupFinder.getCurrentPlayers());
        found.setDescription(groupFinder.getDescription());
        found.setPassword(groupFinder.getPassword());

        groupFinderRepository.save(found);
        return found;

    }

    public void deleteGroupFinder(Long id){
        GroupFinder found = groupFinderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + id);
        }

        groupFinderRepository.delete(found);
    }

}
