package edu.uni.userBaseInfo2.controller.viewObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.userBaseInfo2.service.model.UserModel;

import java.util.Date;

public class UserVO {
    private UserModel userModel;

    @Override
    public String toString() {
        return "UserVO{" +
                "userModel=" + userModel +
                '}';
    }
}
