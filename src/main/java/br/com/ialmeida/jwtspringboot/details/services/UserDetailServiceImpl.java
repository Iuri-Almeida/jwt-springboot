package br.com.ialmeida.jwtspringboot.details.services;

import br.com.ialmeida.jwtspringboot.details.UserDetail;
import br.com.ialmeida.jwtspringboot.entities.User;
import br.com.ialmeida.jwtspringboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        return new UserDetail(user.get());
    }
}
