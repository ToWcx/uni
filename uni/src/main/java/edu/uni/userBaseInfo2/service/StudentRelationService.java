package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.StudentRelation;
import edu.uni.userBaseInfo2.controller.approvalUtil.StudentRelationAU;
import edu.uni.userBaseInfo2.service.model.StudentRelationModel;

import java.util.List;

public interface StudentRelationService {
    boolean insert(StudentRelation studentRelation);

    boolean delete(Long id);

    boolean update(StudentRelation studentRelation);

    StudentRelationModel select(Long id);

    List<StudentRelationModel> selectUser(Long id);

    List<StudentRelationModel> selectRela(Long id,String relation);
}
