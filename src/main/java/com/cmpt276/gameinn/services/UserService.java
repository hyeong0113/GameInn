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

    public User addUser(String sub, String name, String email, String photo) {
        if (repository.findUserByEmail(email) == null) {
            sub = sub.substring(sub.lastIndexOf('|') + 1);
            System.out.println("sub:            :   " + sub);
            User user = new User(sub, name, email, photo);
            repository.save(user);
            return user;
        }
        return repository.findUserByEmail(email);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with" + id));
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User updateUser(User user) {
        User found = repository.findUserByEmail(user.getEmail());
        found.setName(user.getName());
        found.setAbout(user.getAbout());
        found.setSocialAccountsList(user.getSocialAccountsList());

        return repository.save(found);
    }

    public void deleteUser(String email) {
        User found = repository.findUserByEmail(email);
        repository.delete(found);
    }
}
