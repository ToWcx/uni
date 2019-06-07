package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.UserUploadFile;

public interface UserUploadFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserUploadFile record);

    int insertSelective(UserUploadFile record);

    UserUploadFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserUploadFile record);

    int updateByPrimaryKey(UserUploadFile record);
}