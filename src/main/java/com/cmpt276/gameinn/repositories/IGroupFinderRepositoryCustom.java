package com.cmpt276.gameinn.repositories;

import com.cmpt276.gameinn.models.GroupFinder;

public interface IGroupFinderRepositoryCustom {
    GroupFinder findGroupFinderByTitle(String title);
}
