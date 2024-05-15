package com.ks.ATMsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.ATMsystem.model.User;
import com.ks.ATMsystem.mapper.UserMapper;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  public List<User> getAll() {
    List<User> users = userMapper.getAll();
    return users;
  }

  public User getUserInfo(int cid) {
    User user = userMapper.getUserInfo(cid);
    return user;
  }

  public void signup(int cid, String name, int password, int maxWithdrawal) {
    userMapper.signup(cid, name, password, maxWithdrawal);
  }

  public void updatePassword(int cid, int password) {
    userMapper.updatePassword(cid, password);
  }

  public void updateBalance(int cid, int balance) {
    userMapper.updateBalance(cid, balance);
  }

  public void delete(int cid) {
    userMapper.delete(cid);
  }

}
