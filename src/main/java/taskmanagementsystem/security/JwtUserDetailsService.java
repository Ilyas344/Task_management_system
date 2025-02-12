package taskmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import taskmanagementsystem.model.user.User;
import taskmanagementsystem.service.UserService;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = userService.getByEmail(email);
        return JwtEntityFactory.create(user);
    }

}
