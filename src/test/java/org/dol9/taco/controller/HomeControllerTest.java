package org.dol9.taco.controller;

import org.dol9.taco.config.SecurityConfig;
import org.dol9.taco.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringJUnitWebConfig(WebConfig.class)
@WebMvcTest(excludeAutoConfiguration = SecurityConfig.class)
class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHomePage() throws Exception {
    // TODO webconfig로 하니 테스트 실패함, 고칠것
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home"));
  }

}
