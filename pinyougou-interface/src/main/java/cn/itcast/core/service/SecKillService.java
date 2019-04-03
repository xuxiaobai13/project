package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface SecKillService {
    SeckillGoods findOneFromRedis(Long id);

    List<SeckillGoods> findList();
}
