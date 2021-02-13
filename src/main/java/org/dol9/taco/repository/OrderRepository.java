package org.dol9.taco.repository;

import org.dol9.taco.entity.Order;
import org.dol9.taco.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
