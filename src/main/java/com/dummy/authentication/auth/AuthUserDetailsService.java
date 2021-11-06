package com.dummy.authentication.auth;

import com.dummy.authentication.entity.AuthGroup;
import com.dummy.authentication.entity.User;
import com.dummy.authentication.repository.AuthGroupRepository;
import com.dummy.authentication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * will be used in the DaoAuthenticationProvider bean
 */
@Service
public class AuthUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public AuthUserDetailsService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }

        List<AuthGroup> authGroups = this.authGroupRepository.findByUsername(username);
        return new AuthUserPrincipal(user, authGroups);
    }
}
