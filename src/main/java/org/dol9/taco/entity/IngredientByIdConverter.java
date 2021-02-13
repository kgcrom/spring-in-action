package org.dol9.taco.entity;


import org.dol9.taco.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private IngredientRepository ingredientRepository;

  public IngredientByIdConverter(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Ingredient convert(String id) {
    Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
    return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
  }
}
