package com.wxj.service;


import com.wxj.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
