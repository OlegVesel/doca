package ru.otdel.doca.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.user.Role;
import ru.otdel.doca.model.entity.user.UserEntity;
import ru.otdel.doca.repo.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userFromDB = userRepo.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Login not found"));
        return new User(
                userFromDB.getLogin(),
                userFromDB.getPassword(),
                getAuthorities(userFromDB.getRoles()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Role> roles){
        return roles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
