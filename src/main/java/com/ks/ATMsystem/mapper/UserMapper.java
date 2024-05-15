
package com.ks.ATMsystem.mapper;

import java.util.List;

import com.ks.ATMsystem.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

  @Select("SELECT cid,name FROM users")
  @Results({
      @Result(property = "cid", column = "cid"),
      @Result(property = "name", column = "name"),
  })
  List<User> getAll();

  @Select("SELECT * FROM users WHERE cid = #{cid}")
  @Results({
      @Result(property = "cid", column = "cid"),
      @Result(property = "name", column = "name"),
      @Result(property = "password", column = "password"),
      @Result(property = "balance", column = "balance"),
      @Result(property = "maxWithdrawal", column = "maxWithdrawal"),
  })
  User getUserInfo(int cid);

  @Insert("INSERT INTO users(cid,name,password,balance,maxWithdrawal) VALUES(#{cid},#{name}, #{password},0, #{maxWithdrawal})")
  void signup(int cid, String name, int password, int maxWithdrawal);

  @Update("UPDATE users SET password=#{password} WHERE cid =#{cid}")
  void updatePassword(int cid, int password);

  @Update("UPDATE users SET balance=#{balance} WHERE cid =#{cid}")
  void updateBalance(int cid, int balance);

  @Delete("DELETE FROM users WHERE cid =#{cid}")
  void delete(int cid);

}
