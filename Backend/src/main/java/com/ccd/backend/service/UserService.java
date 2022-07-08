package com.ccd.backend.service;

import com.ccd.backend.entity.User;
import com.ccd.backend.framework.exception.MyException;
import com.ccd.backend.mapper.UserMapper;
import com.ccd.backend.utils.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;



    @Value("${spring.datasource.username}")
    public String databaseUserName;
    @Value("${spring.datasource.password}")
    public String databasePassword;

//    public User insecureLogin(String username, String password) {
//        Connection connection = null;
//        //Database password info
//        String url = "jdbc:mysql://localhost:3306/bank?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
//        String name = databaseUserName; //"bankDev";
//        String pwd = databasePassword; //"dev123";
//        User user = null;
//
//        try {
//            //Load JDBC Driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(url, name, pwd);
//            /* START BAD CODE */
//            if(innerValidUser(username, connection)){
//                user = innerValidUser(username, password, connection);
//                if(user == null){
//                    throw new MyException("Login Failed - incorrect password");
//                }
//            }
//            else{
//                throw new MyException("Login Failed - unknown username");
//            }
//            /* END BAD CODE */
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
//
//
//    private boolean innerValidUser(String username, Connection connection) throws SQLException {
//        User user = null;
//        /* START BAD CODE */
//        String sqlMyEvents = "select * from USER where username ='" + username
//                 + "' limit 1; ";
//        System.out.println(sqlMyEvents);
//        Statement sqlStatement = null;
//        sqlStatement = connection.createStatement();
//        ResultSet rs = sqlStatement.executeQuery(sqlMyEvents);
//        /* END BAD CODE */
//        return rs.next();
//    }
//
//
//
//    private User innerValidUser(String username, String password, Connection connection) throws SQLException {
//        User user = null;
//        /* START BAD CODE */
//        String sqlMyEvents = "select * from USER where username ='" + username
//                + "' AND password = '" + password + "' limit 1; ";
//        System.out.println(sqlMyEvents);
//        Statement sqlStatement = null;
//        sqlStatement = connection.createStatement();
//        ResultSet rs = sqlStatement.executeQuery(sqlMyEvents);
//        /* END BAD CODE */
//        if(rs.next()){
//            user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("username"));
//        }
//        return user;
//    }


    //Secure Login by using Mybatis that uses PreparedStatement
    public User login(String username, String password){
        return userMapper.login(Maps.build().put("username",username).put("password",password).getMap());
    }
    public int create(User user){
        return userMapper.create(user);
    }
    public User query(Integer id){
        return userMapper.query(id);
    }


}
