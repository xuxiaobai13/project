package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 网站前台之首页轮播图
 */
@RestController
@RequestMapping("/content")
public class ContentController {


    @Reference
    private ContentService contentService;
    //根据广告分类ID  查询此分类下所有广告集合
    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }

    //查询 商品信息
    @RequestMapping("/findByGoods")
    public List<Object> findByGoods(){
        return contentService.findByGoods();
    }

    //查询 分类信息
    @RequestMapping("/findItemByCat")
    public List<ItemCat> findItemByCat(Long parentId){
        System.out.println(parentId);
        return contentService.findItemByCat(parentId);
    }
}
