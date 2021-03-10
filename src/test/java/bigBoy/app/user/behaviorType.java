package bigBoy.app.user;

import java.math.BigInteger;

/**
 * @program: zaoApiTest
 * @description: 商品/帖子/回帖
 * @author: zhuli
 * @create: 2020-12-11 14:47
 **/
public class behaviorType {

    Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    Long targetId;

    public behaviorType(Integer type, Long targetId) {
        this.type = type;
        this.targetId = targetId;
    }



}
