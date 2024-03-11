package ru.otdel.doca.repo.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otdel.doca.model.entity.document.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {

    //заказчик ищет исполнителей своей карточки, их может быть много
    List<Order> findByCardCustomer_Id(UUID customerId);

    //исполнитель ищет заказчика своей карточки, заказчик только один
    Optional<Order> findByCardExecutor_Id(UUID executorId);


}
