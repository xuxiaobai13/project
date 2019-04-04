package vo;
//申请类

import java.io.Serializable;

public class SpecificationSQ implements Serializable {
    private String status;
    private SpecificationVo specificationVo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SpecificationVo getSpecificationVo() {
        return specificationVo;
    }

    public void setSpecificationVo(SpecificationVo specificationVo) {
        this.specificationVo = specificationVo;
    }
}
