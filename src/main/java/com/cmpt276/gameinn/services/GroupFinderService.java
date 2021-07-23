package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.constant.EnumCollection.GameStyle;
import com.cmpt276.gameinn.constant.EnumCollection.RequiredLevel;
import com.cmpt276.gameinn.repositories.GroupFinder.IGroupFinderRepository;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service
public class GroupFinderService {
    @Autowired
    private IGroupFinderRepository groupFinderRepository;

    @Autowired
    private IUserRepository userRepository;

    public GroupFinder addGroupFinder(GroupFinder groupFinder, User user) {
        GroupFinder created = new GroupFinder(groupFinder.getTitle(), groupFinder.getGameTitle(), groupFinder.getGameStyle(),
                                                groupFinder.getRequiredLevel(), groupFinder.getTotalPlayers(), groupFinder.getCurrentPlayers(),
                                                groupFinder.getDescription(), groupFinder.getPassword(), user);
        return groupFinderRepository.save(created);
    }

    public List<GroupFinder> getGroupFinders() {
        return groupFinderRepository.findAll();
    }

    public GroupFinder getGroupFinderByID(Long id){
        return groupFinderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
    }

    public GroupFinder updateGroupFinder(GroupFinder groupFinder) throws Exception {
        GroupFinder found = groupFinderRepository.findById(groupFinder.getId()).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + groupFinder.getId()));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + groupFinder.getId());
        }

        if (groupFinder.getUser().getId() != found.getUser().getId()) {
            throw new Exception("You are not authorized to edit clip.");
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
