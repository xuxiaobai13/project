package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import vo.OrderVo;

public interface UserService {
    void sendCode(String phone);

    void add(User user, String smscode);

    //查询订单列表
    OrderVo findOrderList(String name);
}
