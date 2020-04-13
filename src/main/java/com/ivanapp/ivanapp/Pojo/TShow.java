package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TShow {

  private int sId;
  private int pId;
  private String imgUrl;

}
