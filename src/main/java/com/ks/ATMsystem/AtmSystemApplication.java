package com.ks.ATMsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.ks.ATMsystem.controller.UserController;

import java.util.Scanner;

import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ks.ATMsystem.mapper")
public class AtmSystemApplication implements CommandLineRunner {

  @Autowired
  private UserController userController;

  public static void main(String[] args) {
    SpringApplication.run(AtmSystemApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Scanner sc = new Scanner(System.in);
    int input = 0;
    while (true) {
      userController.showLoginMenu();
      if (sc.hasNextInt()) {
        input = sc.nextInt();
      } else {
        System.out.println("=====输入非法=====");
        sc.next();
        continue;
      }
      switch (input) {
        case 1:
          int cid = userController.login();
          if (cid != 0) {
            Boolean ifOut = true;
            while (ifOut) {
              userController.showMainMenu();
              input = 0;
              if (sc.hasNextInt()) {
                input = sc.nextInt();
              } else {
                System.out.println("=====输入非法=====");
                sc.next();
                continue;
              }
              switch (input) {
                case 1:
                  userController.showUserInfo(cid);
                  break;
                case 2:
                  userController.depositOrDarw(cid, 1);
                  break;
                case 3:
                  userController.depositOrDarw(cid, 2);
                  break;
                case 4:
                  userController.transfer(cid);
                  break;
                case 5:
                  userController.updataPassword(cid);
                  break;
                case 6:
                  userController.deleteUser(cid);
                  break;
                case 0:
                  ifOut = false;
                  break;
                default:
                  System.out.println("=====输入非法=====");
                  break;
              }
            }
          } else {
            System.out.println("=============登录错误===============");
          }
          break;
        case 2:
          userController.signup();
          break;
        case 0:
          sc.close();
          System.out.println("感谢使用本系统！");
          System.exit(0);
        default:
          System.out.println("=====输入非法=====");
          break;
      }
    }
  }

}
