package com.sii.conference.services;

import com.sii.conference.data.User;
import com.sii.conference.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateOrCreateUser(User user) {
        userRepository.save(user);
    }
    public Optional<User> findUserById(Integer id) {
        return userRepository.findUserById(id);
    }
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdAsc();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
