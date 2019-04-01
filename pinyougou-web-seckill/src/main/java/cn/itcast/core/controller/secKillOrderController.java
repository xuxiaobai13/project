package cn.itcast.core.controller;

import cn.itcast.core.service.SecKillOrderService;
import cn.itcast.core.service.SecKillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillOrder")
public class secKillOrderController {
    @Reference
    private SecKillOrderService secKillOrderService;
}
