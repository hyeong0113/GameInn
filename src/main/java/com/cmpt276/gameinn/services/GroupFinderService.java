package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.GroupFinder.IGroupFinderRepository;

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

    public Page<GroupFinder> getGroupFindersPaginated(Pageable pageable, String query) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GroupFinder> groupFinders = groupFinderRepository.findAll();
        
        List<GroupFinder> filteredGroupFinders = search(query, groupFinders);

        filteredGroupFinders.sort(Comparator.comparing(GroupFinder::getPostedTime).reversed());

        List<GroupFinder> temp = filteredGroupFinders;

        if (temp.size() >= startItem) {
            int toIndex = Math.min(startItem + pageSize, temp.size());
            filteredGroupFinders = temp.subList(startItem, toIndex);
        }
        Page<GroupFinder> groupFinderPage = new PageImpl<GroupFinder>(filteredGroupFinders, PageRequest.of(currentPage, pageSize), temp.size());
        return groupFinderPage;
    }

    /////
    public List<GroupFinder> searchGroupFinders(Pageable pageable, String query) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GroupFinder> groupFinders = groupFinderRepository.findAll();
        
        groupFinders.sort(Comparator.comparing(GroupFinder::getPostedTime).reversed());

        List<GroupFinder> temp = groupFinders;

        if (temp.size() >= startItem) {
            int toIndex = Math.min(startItem + pageSize, temp.size());
            groupFinders = temp.subList(startItem, toIndex);
        }

        List<GroupFinder> filteredGroupFinder = new ArrayList();
        for (GroupFinder groupfinder : groupFinders){
            if (groupfinder.getTitle() == query || groupfinder.getGameTitle() == query){
                filteredClips.add(groupfinder);
            }
        }

        //Page<GroupFinder> groupFinderPage = new PageImpl<GroupFinder>(groupFinders, PageRequest.of(currentPage, pageSize), temp.size());
        return filteredGroupFinder;
    }


    ////////
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
        // found.setPassword(groupFinder.getPassword());
        found.setPostedTime(found.getPostedTime());

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

    public Page<GroupFinder> searchGroupFinders(Pageable pageable, String query) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GroupFinder> groupFinders = groupFinderRepository.findAll();

        List<GroupFinder> filteredGroupFinder = new ArrayList<GroupFinder>();
        for (GroupFinder groupfinder : groupFinders){
            if (groupfinder.getTitle() == query || groupfinder.getGameTitle() == query){
                filteredGroupFinder.add(groupfinder);
            }
        }

        List<GroupFinder> temp = filteredGroupFinder;
        filteredGroupFinder.sort(Comparator.comparing(GroupFinder::getPostedTime).reversed());

        if (temp.size() >= startItem) {
            int toIndex = Math.min(startItem + pageSize, temp.size());
            filteredGroupFinder = temp.subList(startItem, toIndex);
        }


        Page<GroupFinder> filteredGroupFinderPage = new PageImpl<GroupFinder>(filteredGroupFinder, PageRequest.of(currentPage, pageSize), temp.size());

        return filteredGroupFinderPage;
    }

    private List<GroupFinder> search(String query, List<GroupFinder> list) {
        if (query != null && !query.isEmpty()) {
            List<GroupFinder> filteredList = new ArrayList<GroupFinder>();
            for (GroupFinder element : list){
                if (element.getTitle().toLowerCase().contains(query.toLowerCase()) || element.getGameTitle().toLowerCase().contains(query.toLowerCase())){
                    filteredList.add(element);
                }
            }
            return filteredList;
        }
        return list;
    }
}
