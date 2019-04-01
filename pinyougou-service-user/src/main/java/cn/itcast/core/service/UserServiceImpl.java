package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import cn.itcast.core.pojo.user.User;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;
import vo.OrderVo;

import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理
 */
@Service
@Transactional
public class UserServiceImpl implements  UserService {


    //发消息 Map
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Destination smsDestination;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private SellerDao sellerDao;

    @Override
    public void sendCode(final String phone) {
        //1:生成验证码
        final String randomNumeric = RandomStringUtils.randomNumeric(6);
        //2:保存验证码到缓存中
        redisTemplate.boundValueOps(phone).set(randomNumeric);
        //redisTemplate.boundValueOps(phone).expire(1, TimeUnit.MINUTES);
        //2:发消息 Map
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                MapMessage map = session.createMapMessage();
                //手机号
                map.setString("iphone",phone);//"17801040609"
                //验证码
                map.setString("templateParam","{'number':'"+randomNumeric+"'}");
                //签名
                map.setString("signName","品优购商城");
                //模板ID
                map.setString("templateCode","SMS_126462276");


                return map;
            }
        });
    }

    //添加
    @Override
    public void add(User user, String smscode) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        //判断验证码是否失效
        if(null == code){
            throw new RuntimeException("验证码失败");
        }

        if(code.equals(smscode)){
            //保存用户信息
            user.setCreated(new Date());
            user.setUpdated(new Date());
            //密码加密


            userDao.insertSelective(user);


        }else{
            throw new RuntimeException("验证码不正确");
        }



    }

    //查询订单列表
    @Override
    public OrderVo findOrderList(String name) {
        OrderVo orderVo = new OrderVo();
        if (null!=name && !"".equals(name)){
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.createCriteria().andUserIdEqualTo(name);
            List<Order> orderList = orderDao.selectByExample(orderQuery);
            orderVo.setOrderList(orderList);
            if (null!=orderList && orderList.size()>0){
                for (Order order : orderList) {
                    if ("1".equals(order.getStatus())){
                     order.setStatus(order.getStatus().replace("1","未付款"));
                    }
                    if ("2".equals(order.getStatus())){
                        order.setStatus(order.getStatus().replace("2","已付款"));
                    }
                    if ("3".equals(order.getStatus())){
                        order.setStatus( order.getStatus().replace("3","未发货"));
                    }
                    if ("4".equals(order.getStatus())){
                        order.setStatus( order.getStatus().replace("4","已发货"));
                    }
                    if ("5".equals(order.getStatus())){
                        order.setStatus(order.getStatus().replace("5","交易成功"));
                    }
                    if ("6".equals(order.getStatus())){
                        order.setStatus(order.getStatus().replace("6","交易关闭"));
                    }
                    if ("7".equals(order.getStatus())){
                        order.setStatus(order.getStatus().replace("7","待评价"));
                    }
                    OrderItemQuery orderItemQuery = new OrderItemQuery();
                    orderItemQuery.createCriteria().andOrderIdEqualTo(order.getOrderId());

                    List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
                    orderVo.setOrderItemList(orderItemList);
                    if (null!=orderItemList && orderItemList.size()>0){
                        for (OrderItem orderItem : orderItemList) {
                        SellerQuery sellerQuery = new SellerQuery();
                        sellerQuery.createCriteria().andSellerIdEqualTo(orderItem.getSellerId());
                            List<Seller> sellerList = sellerDao.selectByExample(sellerQuery);
                            orderVo.setSellerList(sellerList);
                        }
                    }
                }
            }
        }


        return orderVo;
    }
}
