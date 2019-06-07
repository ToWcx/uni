package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.User;
import edu.uni.userBaseInfo2.service.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {

    boolean insert(User user);

    boolean delete(long id);

    UserModel select(long id);

    boolean update(User user);

    List<UserModel> selectAll();
    /**
     * 根据userId查找user的学校id
     * @param id
     * @return
     */
    User selectUniIdById(long id);
}
