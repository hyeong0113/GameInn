package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public void addUser(String sub) {
        if (repository.findUserBySub(sub) == null) {
            User user = new User(sub);
            repository.save(user);
        }
        return;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with" + id));
    }

    // public User getUserById(String sub) {
    //     return repository;
    // }

    // public User updateUserSocialLinks(User user, Long id) {
    //     User found = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with" + id));
    //     found.setsocialAccountsList(user.getsocialAccountsList());

    //     return repository.save(found);
    // }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
