package com.dummy.authentication.auth;

import com.dummy.authentication.entity.User;
import com.dummy.authentication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * will be used in the DaoAuthenticationProvider bean
 */
@Service
public class AuthUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }

        return new AuthUserPrincipal(user);
    }
}
