package org.smarterbalanced.itemviewerservice.app;
/*
** This needs to be rewritten once the RenderItemController is correctly implemented.


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import RenderItemController;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RenderItemControllerTest {

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = standaloneSetup(new RenderItemController()).build();
  }

  @Test
  public void testNoCodes() throws Exception {
    this.mockMvc.perform(get("/item/200-12344").accept(MediaType.parseMediaType("application/json")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("12344"))
        .andExpect(jsonPath("$.bank").value("200"))
        .andExpect(jsonPath("$.featureCodes").isEmpty());
  }

  @Test
  public void testWithCodes() throws Exception {
    this.mockMvc.perform(get("/item/200-12344?isaap=TDS_BC_ECN;TDS_WL_Glossary").accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("12344"))
            .andExpect(jsonPath("$.bank").value("200"))
            .andExpect(jsonPath("$.featureCodes").isNotEmpty());
  }

}*/
