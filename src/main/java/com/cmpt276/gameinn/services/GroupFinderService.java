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

    public GroupFinder addGroupFinder(String title, String gameTitle, GameStyle gameStyle, RequiredLevel requiredLevel, int totalPlayers, int currentPlayers, String description, Long writerId, String password) {
        User user = userRepository.findById(writerId).orElseThrow(() -> new IllegalArgumentException("No User with " + writerId));
        GroupFinder groupFinder = new GroupFinder(title, gameTitle, gameStyle, requiredLevel, totalPlayers, currentPlayers, description, user, password);
        return groupFinderRepository.save(groupFinder);
    }

    public List<GroupFinder> getGroupFinders() {
        return groupFinderRepository.findAll();
    }

    public GroupFinder getGroupFinderByID (Long id){
        return groupFinderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
    }

    public GroupFinder updateGroupFinder(GroupFinder groupfinder) throws Exception {
        GroupFinder found = groupFinderRepository.findById(groupfinder.getId()).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + groupfinder.getId()));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + groupfinder.getId());
        }

        if (groupfinder.getUser().getId() != found.getUser().getId()) {
            throw new Exception("You are not authorized to edit clip.");
        }

        found.setTitle(groupfinder.getTitle());
        found.setGameTitle(groupfinder.getGameTitle());
        found.setGameStyle(groupfinder.getGameStyle());
        found.setRequiredLevel(groupfinder.getRequiredLevel());
        found.setTotalPlayers(groupfinder.getTotalPlayers());
        found.setCurrentPlayers(groupfinder.getCurrentPlayers());
        found.setDescription(groupfinder.getDescription());
        found.setPassword(groupfinder.getPassword());

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
