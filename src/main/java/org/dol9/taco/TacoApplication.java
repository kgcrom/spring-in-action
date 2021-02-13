package org.dol9.taco;

import org.dol9.taco.entity.Ingredient;
import org.dol9.taco.entity.User;
import org.dol9.taco.repository.IngredientRepository;
import org.dol9.taco.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TacoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TacoApplication.class, args);
  }

  // TODO command line runner에 대해 학습하고 다른 대체할것은 없는지 리서치하기
  // TODO crud repository와 jpa repository의 차이점은?
  // TODO QueryDSL도 한번 사용해보기
  @Bean
  @Profile("local")
  public CommandLineRunner dataLoader(IngredientRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        repo.save(new Ingredient("TMTO", "Diced Tmatoes", Ingredient.Type.VEGGIES));
        repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
      }
    };
  }

  @Bean
  @Profile("!prod")
  public CommandLineRunner dataLoader1(UserRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new User("user1", new BCryptPasswordEncoder().encode("password1"), "user1.kang", "jisung", "suwon", "gyung-gi", "4321", "12345678"));
      }
    };
  }

}
