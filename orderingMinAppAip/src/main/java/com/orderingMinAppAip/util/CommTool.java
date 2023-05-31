package com.orderingMinAppAip.util;

import java.util.Random;

public class CommTool {

    public static Integer RandomNumberGenerator (){
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        return  randomNumber;
    }
}
