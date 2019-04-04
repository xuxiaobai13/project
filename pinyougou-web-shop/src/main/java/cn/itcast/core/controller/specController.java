package cn.itcast.core.controller;

import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationVo;

@RestController
@RequestMapping("/specification")
public class specController {
    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/shenqing")
    public Result shenqing(@RequestBody SpecificationVo specVo){
        try {
            specificationService.shenhe(specVo);
            return new Result(true,"提交审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"提交审核失败");
        }




    }



}
