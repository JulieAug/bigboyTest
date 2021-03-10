package bigBoy.app.goods;

import java.math.BigInteger;

/**
 * @program: zaoApiTest
 * @description: 一级/二级/三级目录类型
 * @author: zhuli
 * @create: 2020-12-09 17:44
 **/
public class menuType {

    Integer categorys;
    String menuType2;
    Long menuTargetId2;
    String menuType3;
    Long menuTargetId3;

    public Integer getCategorys() {
        return categorys;
    }

    public void setCategorys(Integer categorys) {
        this.categorys = categorys;
    }

    public String getMenuType2() {
        return menuType2;
    }

    public void setMenuType2(String menuType2) {
        this.menuType2 = menuType2;
    }

    public Long getMenuTargetId2() {
        return menuTargetId2;
    }

    public void setMenuTargetId2(Long menuTargetId2) {
        this.menuTargetId2 = menuTargetId2;
    }

    public String getMenuType3() {
        return menuType3;
    }

    public void setMenuType3(String menuType3) {
        this.menuType3 = menuType3;
    }

    public Long getMenuTargetId3() {
        return menuTargetId3;
    }

    public void setMenuTargetId3(Long menuTargetId3) {
        this.menuTargetId3 = menuTargetId3;
    }


    public menuType(Integer categorys, String menuType2, Long menuTargetId2, String menuType3, Long menuTargetId3) {
        this.categorys = categorys;
        this.menuType2 = menuType2;
        this.menuTargetId2 = menuTargetId2;
        this.menuType3 = menuType3;
        this.menuTargetId3 = menuTargetId3;
    }





}
