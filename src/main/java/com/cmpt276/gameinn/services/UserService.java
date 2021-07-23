package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public User addUser(String sub, String role, String name, String picture) {
        sub = sub.substring(sub.lastIndexOf('|') + 1);
        System.out.println("User::AddUser");
        System.out.println("sub::" + sub);
        if (repository.findUserBySub(sub) == null) {
            System.out.println("User::AddUser::Create");
            User user = new User(sub, role, name, picture);
            repository.save(user);
            return user;
        }
        System.out.println("User::AddUser::Exist");
        return repository.findUserBySub(sub);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No User with " + id));
    }

    public User getUserBySub(String sub) {
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
