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

    public User addUser(String sub, String name, String email, String photo, String role) {
        sub = sub.substring(sub.lastIndexOf('|') + 1);
        if (repository.findUserBySub(sub) == null) {
            User user = new User(sub, name, email, photo, role);
            repository.save(user);
            return user;
        }
        return repository.findUserBySub(sub);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with" + id));
    }

    public User getUserBySub(String sub) {
        return repository.findUserBySub(sub);
    }

    public User updateUser(User user) {
        User found = repository.findUserBySub(user.getEmail());
        found.setName(user.getName());
        found.setAbout(user.getAbout());
        found.setSocialAccountsList(user.getSocialAccountsList());

        return repository.save(found);
    }

    public void deleteUser(String email) {
        User found = repository.findUserBySub(email);
        repository.delete(found);
    }
}
