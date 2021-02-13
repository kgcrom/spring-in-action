package org.dol9.taco.controller;

import lombok.extern.slf4j.Slf4j;
import org.dol9.taco.config.OrderProps;
import org.dol9.taco.entity.Order;
import org.dol9.taco.entity.User;
import org.dol9.taco.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

  private OrderRepository orderRepository;

  private OrderProps orderProps;

  public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
    this.orderRepository = orderRepository;
    this.orderProps = orderProps;
  }

  @GetMapping
  public String orderForUser(@AuthenticationPrincipal User user, Model model) {
    Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
    model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
    return "orderList";
  }


  @GetMapping("/current")
  public String orderForm(@ModelAttribute Order order) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    if (order.getDeliveryName() == null) {
      order.setDeliveryName(user.getFullName());
    }

    if (order.getDeliveryStreet() == null) {
      order.setDeliveryStreet(user.getStreet());
    }

    if (order.getDeliveryCity() == null) {
      order.setDeliveryCity(user.getCity());
    }

    if (order.getDeliveryState() == null) {
      order.setDeliveryState(user.getState());
    }

    if (order.getDeliveryZip() == null) {
      order.setDeliveryZip(user.getZip());
    }

    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    order.setUser(user);
    orderRepository.save(order);
    sessionStatus.setComplete();
    return "redirect:/";
  }
}
