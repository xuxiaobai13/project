package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SecKillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckillGoods")
public class secKillCartController {
    @Reference
    private SecKillService secKillService;

    //回显
    @RequestMapping("/findList")
    public List<SeckillGoods> findList(){
        //游客状态可以秒杀
        return secKillService.findList();
    }

    @RequestMapping("/findOneFromRedis")
    public SeckillGoods findOneFromRedis(Long id){
        return secKillService.findOneFromRedis(id);
    }

}
