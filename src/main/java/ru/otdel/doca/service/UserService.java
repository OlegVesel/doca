package ru.otdel.doca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otdel.doca.model.entity.user.Role;
import ru.otdel.doca.model.entity.user.UserEntity;
import ru.otdel.doca.model.facade.UserFacade;
import ru.otdel.doca.model.request.UserRequest;
import ru.otdel.doca.model.response.user.ShortUserResponse;
import ru.otdel.doca.model.response.user.UserResponse;
import ru.otdel.doca.repo.RoleRepo;
import ru.otdel.doca.repo.UserRepo;
import ru.otdel.doca.security.jwt.JwtGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;



    public UserResponse register(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        //todo: проверка на существующее имя пользователя
        Role userRole = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        userEntity.setLogin(request.getLogin());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRoles(userRoles);
        userEntity.setIsDeleted(false);
        //todo add log 'user saved'
        return userFacade.entityToResponse(userRepo.save(userEntity));
    }

    public List<UserResponse> getAll(){
        return userRepo.findAll().stream().map(
                userFacade::entityToResponse
        ).collect(Collectors.toList());
    }

    public UserResponse findByLogin(String login){
        UserEntity userEntity = userRepo.findByLogin(login).orElse(null);
        if (userEntity != null)
            return userFacade.entityToResponse(userEntity);
        return null;
    }

    public UserEntity findById(UUID id){
        return userRepo.findById(id).orElse(null);
    }

    public void deleteById(UUID id){

    }


    public UserResponse login(UserRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserEntity userEntity = userRepo.findByLogin(request.getLogin()).orElse(null);
        if (userEntity != null){
            UserResponse response = userFacade.entityToResponse(userEntity);

            response.setAccessToken(
                    jwtGenerator.createToken(authenticate)
            );
            return response;
        }
        return null;
    }

    public List<ShortUserResponse> getAllShort() {
        return userRepo
                .findAll()
                .stream()
                .map(userFacade::entityToShortResponse)
                .toList();
    }
}
