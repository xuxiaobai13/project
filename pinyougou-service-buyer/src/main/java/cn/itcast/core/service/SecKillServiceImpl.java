package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class SecKillServiceImpl implements SecKillService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Override
    public SeckillGoods findOneFromRedis(Long id) {
        return (SeckillGoods)redisTemplate.boundHashOps("seckillSKU").get(id);
    }

    @Override
    public List<SeckillGoods> findList() {
        //先从缓存中查
       List<SeckillGoods> list= redisTemplate.boundHashOps("seckillSKU").values();
       if(list==null&&list.size()==0){
           SeckillGoodsQuery query = new SeckillGoodsQuery();
           SeckillGoodsQuery.Criteria criteria = query.createCriteria();
           //起始时间and库存
           criteria.andStatusEqualTo("1").andStockCountGreaterThan(0).andStartTimeLessThan(new Date()).andEndTimeGreaterThan(new Date());
           List<SeckillGoods> list1 = seckillGoodsDao.selectByExample(query);
           for (SeckillGoods seckillGoods : list1) {

               redisTemplate.boundHashOps("seckillSKU").put(seckillGoods.getId(),seckillGoods);
               //设置存活时间
               redisTemplate.expire(seckillGoods.getId(),1, TimeUnit.DAYS);
           }
       }
        return list;
    }
}
