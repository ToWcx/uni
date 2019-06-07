package edu.uni;

import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UniApplicationTests {



//    @Autowired
//    StudentMapper stuMapper;
//
//    @Test
//    public void contextLoads() throws IOException {
//        System.out.println("********************XXXXXXXXXXXXXXXXXX**********************");
//        //注册一个16级的学生
//        Student stu = new Student();
//        stu.setStuNo("201624101134");
//        stu.setBeginLearnDate(new Date());
//        stu.setGrade("2016");
//        stu.setSpecialtyId((long)90011);
//        stu.setClassId((long)1011);
//        stu.setDatetime(new Date());
//        stu.setDeleted(false);
//        stu.setByWho((long)9999);
//        stuMapper.insert(stu);
//        System.out.println(stu.getId());
//
//        //获取刚注册的student
//        stuMapper.selectByExample(null).stream().forEach(e ->{
//            System.out.println(e);
//        });
//    }

}
