package cn.itcast.core.service;

import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SecKillOrderServiceImpl implements SecKillOrderService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String,Object> ops;
    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 提交订单,未支付状态
     * @param seckillId
     * @param name
     */
    @Override
    public void submitOrder(Long seckillId,String name) {
        //redis存储key为商品Id,值为商品
        SeckillGoods seckillSKU =(SeckillGoods) redisTemplate.boundHashOps("seckillSKU").get(seckillId);
        if(seckillSKU==null){
            throw new RuntimeException("商品不存在");
        }if(seckillSKU.getStockCount()<=0){
            throw new RuntimeException("商品已售空");
        }
        //同步商品计数器
        ops.increment(String.valueOf(seckillSKU.getId()), 1);
        Integer count = Integer.valueOf(redisTemplate.boundValueOps(String.valueOf(seckillSKU.getId())).get(0, -1));
        System.out.println("计数器:"+count);
        if(count<=seckillSKU.getStockCount()){
            //有库存
            seckillSKU.setStockCount(seckillSKU.getStockCount()-1);
            //更新缓存库存
            redisTemplate.boundHashOps("seckillSKU").put(seckillId,seckillSKU);
        }
        //秒杀完毕
            seckillGoodsDao.updateByPrimaryKey(seckillSKU);
            redisTemplate.boundHashOps("seckillSKU").delete(seckillId);
        //保存订单
        long orderId = idWorker.nextId();
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(orderId);
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setSellerId(seckillSKU.getSellerId());
        //状态
        seckillOrder.setStatus("0");
        //价格
        seckillOrder.setMoney(seckillSKU.getCostPrice());
        //用户Id
        seckillOrder.setUserId(name);
        //保存到缓存中
        redisTemplate.boundHashOps("seckillOrder").put(name,seckillOrder);
    }
}
