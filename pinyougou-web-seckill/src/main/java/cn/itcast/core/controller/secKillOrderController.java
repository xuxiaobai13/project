package cn.itcast.core.controller;

import cn.itcast.core.service.SecKillOrderService;
import cn.itcast.core.service.SecKillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillOrder")
public class secKillOrderController {
    @Reference
    private SecKillOrderService secKillOrderService;

    /**
     * 提交秒杀订单
     * @param seckillId
     * @return
     */
    public Result submitOrder(Long seckillId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if("anonymousUser".equals(name)){
            //未登录状态,不能提交
            return new Result(false,"用户未登录");
        }
        try {
            secKillOrderService.submitOrder(seckillId,name);
            return  new Result(true,"success");
        }catch(RuntimeException e){
            return  new Result(false,e.getMessage());
        }
        catch (Exception e) {
            return new Result(false,"failure");
        }
    }
}
