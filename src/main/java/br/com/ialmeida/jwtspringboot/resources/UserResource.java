package br.com.ialmeida.jwtspringboot.resources;

import br.com.ialmeida.jwtspringboot.entities.User;
import br.com.ialmeida.jwtspringboot.resources.util.URL;
import br.com.ialmeida.jwtspringboot.services.UserService;
import br.com.ialmeida.jwtspringboot.services.exceptions.BlankFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    private final UserService userService;

    private final PasswordEncoder encoder;

    public UserResource(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/validate")
    public ResponseEntity<Boolean> validatePassword(
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "password", defaultValue = "") String password) {
        login = URL.decodeParam(login);
        password = URL.decodeParam(password);

        User user = userService.findByLogin(login);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = encoder.matches(password, user.getPassword());
        HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);

    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        if (user.getLogin().isBlank() || user.getPassword().isBlank()) {
            throw new BlankFieldException("Some fields can not be blank.");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        user = userService.update(id, user);
        return ResponseEntity.ok().body(user);
    }

}
