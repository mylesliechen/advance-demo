package com.example.parquet;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class demo {
    @Test
    public void test(){
        User user = new User();
//        user.setIterm(new Iterm("10","xiaoh"));
//        Iterm iterm = user.getIterm();
//        iterm.setName("cahnge");

        List<User> list = new ArrayList<>();
        list.add(new User());
        System.out.println(list.get(10));
//        System.out.println(user.getIterm().getName());
    }
}
