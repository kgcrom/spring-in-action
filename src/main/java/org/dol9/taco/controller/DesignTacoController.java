package org.dol9.taco.controller;

import lombok.extern.slf4j.Slf4j;
import org.dol9.taco.entity.Ingredient;
import org.dol9.taco.entity.Order;
import org.dol9.taco.entity.Taco;
import org.dol9.taco.repository.IngredientRepository;
import org.dol9.taco.repository.TacoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;
  private TacoRepository tacoRepository;

  public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
    this.ingredientRepository = ingredientRepository;
    this.tacoRepository = tacoRepository;
  }

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignFrom(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepository.findAll().forEach(v -> ingredients.add(v));

    Ingredient.Type[] types = Ingredient.Type.values();
    for (Ingredient.Type type : types) {
      model.addAttribute(type.name().toLowerCase(), filterByType(ingredients, type));
    }

    model.addAttribute("taco", new Taco());
    return "design";
  }

  @PostMapping
  public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
    if (errors.hasErrors()) {
      return "design";
    }
    log.info("Processing design: " + design);
    Taco taco = tacoRepository.save(design);
    order.addDesign(taco);

    return "redirect:/orders/current";
  }

  private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
    return ingredients.stream()
        .filter(v -> v.getType().equals(type))
        .collect(Collectors.toList());
  }
}
