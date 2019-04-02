import cn.itcast.core.pojo.seckill.SeckillOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class Redis {

    @Autowired
    private RedisTemplate redisTemplate;
    //引用操作类
    @Resource(name = "redisTemplate")
    private ValueOperations<String,Object> ops;
    @Test
    public void testRedis() {
        redisTemplate.boundHashOps("ceshi").put("StockCount",1);
        try {
            //此方法会先检查key是否存在，存在+1，不存在先初始化，再+1
            ops.increment("StockCount", 1);
            //防止JDK序列化之后，反序列化失败
            Integer stockCount = Integer.valueOf(redisTemplate.boundValueOps("StockCount").get(0, -1));
            System.out.println(stockCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /* @Autowired
    private RedisTemplate redisTemplate;
   @Test
    public void test(){
       redisTemplate.boundHashOps("ceshi").put("StockCount",1);
       Integer o =(Integer) redisTemplate.boundHashOps("ceshi").get("StockCount");
       System.out.println(o);
      Long count= redisTemplate.opsForValue().increment("success",1);
       Integer o1 =(Integer) redisTemplate.boundHashOps("ceshi").get("StockCount");
       System.out.println(o1);
       redisTemplate.opsForValue().increment("StockCount",1);
       Integer o2 =(Integer) redisTemplate.boundHashOps("ceshi").get("StockCount");
       System.out.println(o2);

   }
   @Test
    public void test01(){
       String key = "StockCount";
       Long delta = 1L;
       if(delta<0){
           throw new RuntimeException("递增因子必须大于0");
       }
       redisTemplate.opsForValue().increment(key, delta);


   }*/
}
