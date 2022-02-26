package br.com.ialmeida.jwtspringboot.services.exceptions;

public class DuplicateLoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateLoginException(Object login) {
        super("Duplicate login. Login: " + login);
    }

}
