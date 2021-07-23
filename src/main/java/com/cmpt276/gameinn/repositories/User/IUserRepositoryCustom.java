package com.cmpt276.gameinn.repositories.User;

import com.cmpt276.gameinn.models.User;

public interface IUserRepositoryCustom {
	User findUserBySub(String email);
}
