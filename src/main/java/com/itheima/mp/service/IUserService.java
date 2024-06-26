package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;

public interface IUserService extends IService<User> {
    //PageDTO<UserVO> queryUsersPage(PageQuery query);

    void deductBalance(Long id, Integer money);

    UserVO queryUserAndAddressById(Long userId);

    PageDTO<UserVO> queryUsersPage(UserQuery query);
}
