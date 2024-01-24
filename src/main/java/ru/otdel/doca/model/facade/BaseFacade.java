package ru.otdel.doca.model.facade;

public interface BaseFacade<E, RQ, RS>{
    E requestToEntity(RQ request);

    RS entityToResponse(E entity);
}
