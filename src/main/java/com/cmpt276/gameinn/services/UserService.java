package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public User addUser(String sub, String role, String name, String picture) {
        sub = sub.substring(sub.lastIndexOf('|') + 1);
        if (repository.findUserBySub(sub) == null) {
            User user = new User(sub, role, name, picture);
            repository.save(user);
            return user;
        }
        return repository.findUserBySub(sub);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with " + id));
    }

    public User getUserBySub(String sub) {
        if (sub == null) {
            throw new IllegalArgumentException("No User with " + sub);
        }
        return repository.findUserBySub(sub);
    }

    public User updateUser(User user) {
        User found = repository.findUserBySub(user.getSubId());
        if (found == null) {
            throw new IllegalArgumentException("No User with " + user.getSubId());
        }

        found.setRole(user.getRole());
        found.setSocialAccountsList(user.getSocialAccountsList());

        repository.save(found);
        return found;
    }

    public void deleteUser(String sub) {
        User found = repository.findUserBySub(sub);
        if (found == null) {
            throw new IllegalArgumentException("No User with " + sub);
        }

        repository.delete(found);
    }
}
