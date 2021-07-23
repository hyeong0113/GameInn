package com.cmpt276.gameinn.repositories.GroupFinder;

import com.cmpt276.gameinn.models.GroupFinder;

public interface IGroupFinderRepositoryCustom {
    GroupFinder findGroupFinderByTitle(String title);
}
