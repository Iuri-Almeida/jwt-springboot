package br.com.ialmeida.jwtspringboot.services;

import br.com.ialmeida.jwtspringboot.entities.User;
import br.com.ialmeida.jwtspringboot.repositories.UserRepository;
import br.com.ialmeida.jwtspringboot.services.exceptions.DatabaseException;
import br.com.ialmeida.jwtspringboot.services.exceptions.DuplicateLoginException;
import br.com.ialmeida.jwtspringboot.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isEmpty() ? null : user.get();
    }

    public User insert(User user) {
        User obj = findByLogin(user.getLogin());

        if (obj != null) {
            throw new DuplicateLoginException(user.getLogin());
        }

        return userRepository.save(user);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User user) {
        User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, user);
        return userRepository.save(entity);
    }

    private void updateData(User entity, User user) {
        entity.setLogin(user.getLogin());
    }

}
