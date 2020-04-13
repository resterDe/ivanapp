package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TSpecification {

  private int speId;
  private int pId;
  private String speName;
  private double price;

}
