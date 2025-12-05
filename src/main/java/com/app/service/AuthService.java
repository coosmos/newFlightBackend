package com.app.service;


import com.app.dto.AuthResponse;
import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.entity.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // handle user signup
    public Mono<AuthResponse> signup(SignupRequest request) {
        // check if email already exists
        return userRepository.existsByEmail(request.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        // email already taken, return error
                        return Mono.error(new RuntimeException("email already exists"));
                    }

                    // create new user
                    User user = new User();
                    user.setName(request.getName());
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));  // hash password
                    user.setRole(User.Role.USER);

                    return userRepository.save(user)
                            .map(savedUser -> {

                                String token = jwtService.generateToken(savedUser);

                                return new AuthResponse(
                                        token,
                                        savedUser.getEmail(),
                                        savedUser.getName(),
                                        savedUser.getRole().name()
                                );
                            });
                });
    }

    // handle user login
    public Mono<AuthResponse> login(LoginRequest request) {
        // find user by email
        return userRepository.findByEmail(request.getEmail())
                .switchIfEmpty(Mono.error(new BadCredentialsException("invalid email or password")))
                .flatMap(user -> {

                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return Mono.error(new BadCredentialsException("invalid email or password"));
                    }

                    String token = jwtService.generateToken(user);

                    return Mono.just(new AuthResponse(
                            token,
                            user.getEmail(),
                            user.getName(),
                            user.getRole().name()
                    ));
                });
    }
}