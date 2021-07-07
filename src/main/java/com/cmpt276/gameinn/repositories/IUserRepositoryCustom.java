package com.cmpt276.gameinn.repositories;

import com.cmpt276.gameinn.models.User;

public interface IUserRepositoryCustom {
    User findUserBySub(String email);
}
