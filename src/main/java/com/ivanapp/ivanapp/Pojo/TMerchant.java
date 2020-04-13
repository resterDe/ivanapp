package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TMerchant {

  private int mId;
  private String merAccount;
  private String merPassword;
  private String merAddress;
  private String email;
  private String wxCode;
  private String merPhone;
  private String merInfo;

}
