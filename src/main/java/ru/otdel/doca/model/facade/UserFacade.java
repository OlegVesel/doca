package ru.otdel.doca.model.facade;

import org.springframework.stereotype.Component;
import ru.otdel.doca.model.entity.user.Role;
import ru.otdel.doca.model.entity.user.UserEntity;
import ru.otdel.doca.model.request.UserRequest;
import ru.otdel.doca.model.response.UserResponse;

@Component
public class UserFacade implements BaseFacade<UserEntity, UserRequest, UserResponse> {
    @Override
    public UserEntity requestToEntity(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse entityToResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
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
}
