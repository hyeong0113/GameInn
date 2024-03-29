package com.cmpt276.gameinn.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service public class UserService {
	@Autowired private IUserRepository repository;
	private String apiRole = "https://gameinn:us:auth0:com/api/v2/roles";

	public User addUser(String sub, String role, String name, String picture,
		String email) {
		sub = sub.substring(sub.lastIndexOf('|') + 1);

		if (repository.findUserBySub(sub) == null) {
			User user = new User(sub, role, name, picture, email);
			repository.save(user);
			return user;
		}

		return repository.findUserBySub(sub);
	}

	public List<User> getUsers() {
		return repository.findAll();
	}

	public User getUserById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("No User with " + id));
	}

	public User getUserBySub(String sub) {
		if (sub == null)
			throw new IllegalArgumentException("No User with " + sub);

		return repository.findUserBySub(sub);
	}

	public User updateUser(String sub, User user) {
		User found = repository.findUserBySub(sub);

		if (found == null)
			throw new IllegalArgumentException("No User with " +
				user.getSubId());

		found.setAbout(user.getAbout());

		found.setFacebook(user.getFacebook());
		found.setTwitter(user.getTwitter());
		found.setDiscord(user.getDiscord());
		found.setGuilded(user.getGuilded());
		found.setTwitch(user.getTwitch());
		found.setYoutube(user.getYoutube());
		found.setYoutube_name(user.getYoutube_name());
		found.setSteam(user.getSteam());
		found.setXbox(user.getXbox());
		found.setPsn(user.getPsn());
		found.setBattle_net(user.getBattle_net());

		repository.save(found);
		return found;
	}

	public void deleteUser(String sub) {
		User found = repository.findUserBySub(sub);

		if (found == null)
			throw new IllegalArgumentException("No User with " + sub);

		repository.delete(found);
	}

	public String getRoleFromResponse(OidcUser principal) {
		String role = principal.getClaims().get(apiRole).toString();
		StringBuilder role_refined = new StringBuilder(role);
		role_refined.deleteCharAt(role.length() - 1);
		role_refined.deleteCharAt(0);
		return role_refined.toString();
	}
}
