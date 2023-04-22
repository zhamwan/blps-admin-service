package com.example.blpsadminservice.services;

import com.example.blpsadminservice.exceptions.AuthorizeException;
import com.example.blpsadminservice.exceptions.InvalidDataException;
import com.example.blpsadminservice.exceptions.NoSuchUserException;
import com.example.blpsadminservice.model.User;
import com.example.blpsadminservice.model.dto.LoginRequestDTO;
import com.example.blpsadminservice.model.dto.RegisterRequestDTO;
import com.example.blpsadminservice.model.enums.RoleName;
import com.example.blpsadminservice.repositories.RoleRepository;
import com.example.blpsadminservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authUser(LoginRequestDTO loginRequestDTO) throws NoSuchUserException, AuthorizeException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new AuthorizeException("Ошибка авторизации");
        }
        User user = userRepository.findUserByEmail(loginRequestDTO.getEmail());
        if (user == null) {
            throw new NoSuchUserException("Пользователя с таким логином не существует");
        }
        return user;
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        validateRegisterDTO(registerRequestDTO);
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new InvalidDataException("Пользователь с таким email уже существует");
        }
        User user = new User(
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail(),
                passwordEncoder.encode(registerRequestDTO.getPassword()),
                roleRepository.findByName(RoleName.USER)
        );
        user = userRepository.save(user);
        return user;
    }

    private void validateRegisterDTO(RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().equals("")
                || registerRequestDTO.getPassword().length() < 5) {
            message.append("Пароль должен состоять минимум из 5 символов");
            valid = false;
        }
        if (registerRequestDTO.getName() == null || registerRequestDTO.getName().equals("")) {
            message.append("ФИО не может быть пустым");
            valid = false;
        }
        if (registerRequestDTO.getEmail() == null || registerRequestDTO.getEmail().equals("")) {
            message.append("Email не может быть пустым");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
