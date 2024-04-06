package ru.otdel.doca.model.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.user.Role;
import ru.otdel.doca.model.entity.user.UserEntity;
import ru.otdel.doca.model.request.user.UserRequest;
import ru.otdel.doca.model.request.user.UserShortRequest;
import ru.otdel.doca.model.response.user.ShortUserResponse;
import ru.otdel.doca.model.response.user.UserResponse;
import ru.otdel.doca.repo.UserRepo;

@Component
@RequiredArgsConstructor
public class UserFacade implements BaseFacade<UserEntity, UserRequest, UserResponse> {
    private final UserRepo userRepo;
    @Override
    public UserEntity requestToEntity(UserRequest request) {
        UserEntity entity;
        if (request.getId() != null)
            entity = userRepo.findById(request.getId()).orElse(new UserEntity());
        else
            entity = new UserEntity();
        entity.setLogin(request.getLogin());
        entity.setFirstName(request.getFirstName());
        entity.setPatronymic(request.getPatronymic());
        entity.setLastName(request.getLastName());
        return entity;
    }

    @Override
    public UserResponse entityToResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setLogin(entity.getLogin());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setPatronymic(entity.getPatronymic());
        response.setRoles(entity.getRoles()
                .stream()
                .map(Role::getName)
                .toList()
        );
        return response;
    }

    public ShortUserResponse entityToShortResponse(UserEntity entity){
        ShortUserResponse response = new ShortUserResponse();
        response.setLogin(entity.getLogin());
        StringBuilder builder = new StringBuilder();
        if (entity.getLastName() != null && !entity.getLastName().isEmpty()){
            response.setLastName(entity.getLastName());
            builder.append(entity.getLastName()).append(" ");
        }
        if (entity.getFirstName() != null && !entity.getFirstName().isEmpty()){
            response.setFirstName(entity.getFirstName());
            builder.append(entity.getFirstName().charAt(0)).append(".");
        }
        if (entity.getPatronymic() != null && !entity.getPatronymic().isEmpty()){
            response.setPatronymic(entity.getPatronymic());
            builder.append(entity.getPatronymic().charAt(0)).append(".");
        }
        response.setFullName(
                builder.toString()
        );

        return response;
    }
}
