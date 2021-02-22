package com.lin.mapper;

import com.lin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginMapper {
    String login(Integer userid,String password);
    String name(Integer userid);
}
