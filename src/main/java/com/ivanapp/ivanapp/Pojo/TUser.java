package com.ivanapp.ivanapp.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TUser {

  private int uId;
  private String openId;
  private String userName;
  private String sex;
  private String birthday;

}
