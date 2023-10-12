package com.sii.conference.services;

import com.sii.conference.data.User;
import com.sii.conference.data.repositories.UserRepository;
import com.sii.conference.exceptions.user.UserWithThisLoginExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Transactional
    public User createUser(User user) throws UserWithThisLoginExistsException {
        validateIfLoginIsUnique(user.getLogin());
        return userRepository.save(user);
    }
    @Transactional
    public Optional<User> updateUser(Integer id, User user) {
        final Optional<User> userOptional = findUserById(id);

        User oldUser = null;
        if (userOptional.isPresent()) {
            oldUser = userOptional.get();
            if (user.getLogin() != null) oldUser.setLogin(user.getLogin());
            if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
            return Optional.of(userRepository.save(oldUser));
        }
        else return Optional.empty();
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

    private void validateIfLoginIsUnique(String login) {
        findUserByLogin(login)
                .ifPresent(
                        existingUser -> {
                            throw new UserWithThisLoginExistsException("This login is already taken!");
                        }
                );
    }
}
