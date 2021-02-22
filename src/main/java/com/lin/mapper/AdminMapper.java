package com.lin.mapper;

import com.lin.model.Teacher;
import com.lin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AdminMapper {
    Integer find(Integer userid);
    Integer add(Integer userid,String name,String sex,String birthday,Integer phone,String addr);
    void userAdd(Integer userid, String password, String position, String name);
    Integer del(Integer userid);
    Integer teacherDel(Integer userid);
    Integer passwd(Integer userid,String password);
    List<User>show();
    Teacher teacher(Integer userid);
}
