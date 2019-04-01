package cn.itcast.core.controller;


import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class brandsController {
    @Reference
    private BrandService brandService;

    @RequestMapping("/shenhe")
    public Result shenhe(@RequestBody Brand brand){
        try {
            System.out.println(brand);
            brandService.shenhe(brand);
            return new Result(true,"提交审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"提交审核失败");
        }

    }

    @RequestMapping("/findSQ")
    public List<Brand> findSQ(){
        return brandService.findSQ();

    }



}
