package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TOrder {

  private int oId;
  private int pId;
  private String openId;
  private String speName;
  private String makeDate;
  private int photoState;
  private int postState;
  private int tfetch;
  private String makeName;
  private String makePhone;
  private String makeSex;
  private double payPrice;
  private String sendSite;
  private String evaluate;

  // 封装信息
  private TPhoto photos;

  private String photoName;

}
