package cn.itcast.core.controller;

import cn.itcast.core.service.SecKillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillGoods")
public class secKillCartController {
    @Reference
    private SecKillService secKillService;

}
