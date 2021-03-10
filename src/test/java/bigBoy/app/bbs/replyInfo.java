package bigBoy.app.bbs;

/**
 * @program: zaoApiTest
 * @description: 回帖
 * @author: zhuli
 * @create: 2020-12-18 11:49
 **/
public class replyInfo {
    Long id;
    Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public replyInfo(Long id, Integer type) {
        this.id = id;
        this.type = type;
    }

}
