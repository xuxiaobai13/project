package vo;

import cn.itcast.core.pojo.good.Brand;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BrandsVo implements Serializable {
    private List<Brand> brandList;
    //0:未审核 1:审核通过 2:驳回
    private String status;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
