package vo;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.seller.Seller;

import java.io.Serializable;
import java.util.List;

/***
 * 订单包装类
 */
public class OrderVo implements Serializable {

    //订单对象集合
    private List<Order> orderList;

    //订单详情对象集合
    private List<OrderItem> orderItemList;

    //商家对象集合
    private List<Seller> sellerList;

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
