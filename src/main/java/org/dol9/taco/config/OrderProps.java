package org.dol9.taco.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
@Valid
public class OrderProps {

  @Min(value = 5, message = "must be between 5 and 25")
  @Max(value = 25, message = "must be between 5 and 25")
  private int pageSize = 20;
}
