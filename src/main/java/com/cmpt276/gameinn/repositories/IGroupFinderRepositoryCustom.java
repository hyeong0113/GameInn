package com.cmpt276.gameinn.repositories;

import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;

public interface IGroupFinderRepositoryCustom {
    GroupFinder findGroupFinderByTitle(String title);
    List<GroupFinder> findAllGroupFinderByWriterId(Long writerId);
    GroupFinder findGroupFinderByWriterId(Long writerId, Long groupFinderId);
}
