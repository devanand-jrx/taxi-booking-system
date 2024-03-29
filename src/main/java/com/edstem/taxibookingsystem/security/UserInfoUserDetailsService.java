package com.edstem.taxibookingsystem.security;

import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);

        if (user != null) {
            return new UserInfoUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
