package bigBoy.app.goods;

/**
 * @program: zaoApiTest
 * @description:
 * @author: zhuli
 * @create: 2020-12-09 15:20
 **/

public class goodsCollection {
    String type;
    Long collectionId;

    public goodsCollection(String type, Long collectionId) {
        this.type = type;
        this.collectionId = collectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
}
