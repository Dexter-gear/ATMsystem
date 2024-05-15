package com.ks.ATMsystem.model;

import java.io.Serializable;

public class User implements Serializable {

  private int cid;
  private String name;
  private int password;
  private int balance;
  private int maxWithdrawal;

  public User() {
    super();
  }

  public int getId() {
    return cid;
  }

  public String getName() {
    return name;
  }

  public int getPassword() {
    return password;
  }

  public int getBalance() {
    return balance;
  }

  public int getMaxWithdrawal() {
    return maxWithdrawal;
  }

}
