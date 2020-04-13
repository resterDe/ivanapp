package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class TPhoto {

  private int pid;
  private String photoName;
  private String coverImg;
  private String serviceInfo;
  private String productInfo;
  private String photoType;
  private int photoSize;
  private double minPrice;
  /**
   * 联表查询的信息
   */
  // 服务详情
  private String modelling;
  private String shoot;
  private String anaphase;
  private String plot;
  private String afterSale;
  private String explain;

}
