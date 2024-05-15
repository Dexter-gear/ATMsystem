package com.ks.ATMsystem.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.ks.ATMsystem.model.User;
import com.ks.ATMsystem.service.UserService;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  public void showLoginMenu() {
    System.out.println("=============ATMsystem==============");
    System.out.println("=============1: 账户登录============");
    System.out.println("=============2: 账户注册============");
    System.out.println("=============0: 退出系统============");
    System.out.println("====================================");
  }

  public void showMainMenu() {
    System.out.println("=============ATMsystem==============");
    System.out.println("=============1: 账户信息============");
    System.out.println("=============2: 存款================");
    System.out.println("=============3: 取款================");
    System.out.println("=============4: 转账================");
    System.out.println("=============5: 修改密码============");
    System.out.println("=============6: 注销账户============");
    System.out.println("=============0: 退出账户============");
    System.out.println("====================================");
  }

  public int login() {
    System.out.println("=============系统登录===============");
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.println("请输入登录卡号：");
      int cardId = sc.nextInt();
      List<User> allUsers = userService.getAll();
      for (User user : allUsers) {
        if (user.getId() == cardId) {
          while (true) {
            System.out.println("请您输入账户密码：");
            int password = sc.nextInt();
            User Cuser = userService.getUserInfo(cardId);
            if (Cuser.getPassword() == password) {
              System.out.println("恭喜您，" + Cuser.getName() + "先生/女士，您已登录成功，您的卡号是：" + Cuser.getId());
              return cardId;
            } else {
              System.out.println("对不起，密码错误！");
            }
          }
        }
      }
      System.out.println("对不起，系统不存在该账户卡号");

    }
  }

  public void signup() {
    System.out.println("===============系统开户操作===========");
    System.out.println("请您输入账户用户名：");
    Scanner sc = new Scanner(System.in);
    String userName = sc.next();
    int password = 0;

    while (true) {
      System.out.println("请您输入账户密码：");
      password = sc.nextInt();
      System.out.println("请您输入确认密码：");
      int okPassWord = sc.nextInt();

      if (password == okPassWord) {
        System.out.println("注册成功，请使用卡号登录！");
        break;
      } else {
        System.out.println("对不起，您输入的两次密码不一致，请重新输入");
      }
    }
    System.out.println("请您输入账户单次限额：");
    int quotaMoney = sc.nextInt();
    Random random = new Random();
    int cardId;
    List<User> allUsers = userService.getAll();
    boolean isDuplicate;
    do {
      cardId = random.nextInt(900000000) + 1000000000;
      isDuplicate = false;
      for (User user : allUsers) {
        if (user.getId() == cardId) {
          isDuplicate = true;
          break;
        }
      }
    } while (isDuplicate);
    userService.signup(cardId, userName, password, quotaMoney);
    System.out.println("恭喜您，" + userName + "先生/女士，您的账户开户成功，您的卡号是：" + cardId + "。请您妥善保管。");
  }

  public void showUserInfo(int cid) {
    User Cuser = userService.getUserInfo(cid);
    System.out.println("=======用户信息========");
    System.out.println("用户名：" + Cuser.getName());
    System.out.println("卡号：" + Cuser.getId());
    System.out.println("余额：" + Cuser.getBalance());
    System.out.println("转账限额：" + Cuser.getMaxWithdrawal());
  }

  public void depositOrDarw(int cid, int operater) {
    if (operater == 1) {
      System.out.println("======存钱操作======");
      System.out.println("请您输入存款金额：");
    } else if (operater == 2) {
      System.out.println("======取钱操作======");
      System.out.println("请您输入取款金额：");
    } else {
      System.out.println("需要正确的操作数(1,2)");
      return;
    }
    Scanner sc = new Scanner(System.in);
    int money = sc.nextInt();
    User Cuser = userService.getUserInfo(cid);
    if (money <= Cuser.getMaxWithdrawal()) {
      int balance = Cuser.getBalance();
      if (operater == 1) {
        balance += money;
        System.out.println("恭喜您，您存钱：" + money + "成功，存钱后余额是：" + balance);
      } else if (operater == 2) {
        balance -= money;
        System.out.println("恭喜您，您取钱：" + money + "成功，取钱后余额是：" + balance);
      }
      userService.updateBalance(cid, balance);
    } else {
      System.out.println("转账金额超出限额");
    }
  }

  public void transfer(int cid) {
    System.out.println("======用户转账=======");
    User Cuser = userService.getUserInfo(cid);
    if (Cuser.getBalance() == 0) {
      System.out.println("余额不足");
      return;
    }
    while (true) {
      System.out.println("请您输入对方的卡号：");
      Scanner sc = new Scanner(System.in);
      int cardId = sc.nextInt();

      User Tuser = userService.getUserInfo(cardId);
      if (Cuser == null) {
        System.out.println("您输入的对方的卡号不存在");
      } else {
        System.out.println("请输入对方的用户名");
        String preName = sc.next();
        if (Tuser.getName().equals(preName)) {
          while (true) {
            System.out.println("请您输入转账给对方的金额：");
            int money = sc.nextInt();
            if (Cuser.getBalance() >= money) {
              if (money <= Cuser.getMaxWithdrawal()) {
                int balance = Cuser.getBalance();
                balance -= money;
                userService.updateBalance(cid, balance);
                int Tbalance = Tuser.getBalance();
                Tbalance += money;
                userService.updateBalance(cardId, Tbalance);
                System.out.println("转账成功了");
                return;
              } else {
                System.out.println("转账金额超出限额");
              }
            } else {
              System.out.println("您余额不足，无法给对方转这么多钱");
            }
          }
        } else {
          System.out.println("对不起，您认证的用户名不正确");
        }
      }
    }
  }

  public void updataPassword(int cid) {
    System.out.println("==================用户密码修改==================");
    while (true) {
      System.out.println("请输入当前密码：");
      Scanner sc = new Scanner(System.in);
      int password = sc.nextInt();
      User Cuser = userService.getUserInfo(cid);
      if (password == Cuser.getPassword()) {
        while (true) {
          System.out.println("请您输入新密码：");
          int newPassWord = sc.nextInt();

          System.out.println("请您确认新密码：");
          int okPassWord = sc.nextInt();

          if (newPassWord == okPassWord) {
            userService.updatePassword(cid, newPassWord);
            System.out.println("密码修改成功！");
            return;
          } else {
            System.out.println("您输入的2次密码不一致！");
          }
        }
      } else {
        System.out.println("密码错误！");
        return;
      }
    }
  }

  public void deleteUser(int cid) {
    System.out.println("==================用户销户==================");
    System.out.println("您真的要销户？  y/n");
    Scanner sc = new Scanner(System.in);
    String rs = sc.next();
    switch (rs) {
      case "y":
        User Cuser = userService.getUserInfo(cid);
        if (Cuser.getBalance() > 0) {
          System.out.println("您的账户余额不为0，不允许销户！！");
        } else {
          userService.delete(cid);
          System.out.println("您的账户销户完成！");
        }
        break;
      default:
        System.out.println("好的，当前账户继续保留！");
        break;
    }
  }
}
