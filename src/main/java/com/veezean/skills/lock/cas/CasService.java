package com.veezean.skills.lock.cas;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/7/28
 */
public class CasService {

    private int updateContentByIdAndVersion(String content, int id, int version) {
        return 0;
    }

    public Item getItemById(int id) {
        return new Item();
    }

    public void updateItem(Item  item) {
        int updateResult = updateContentByIdAndVersion(item.getContent(), item.getId(), item.getVersion());
        if (updateResult == 0) {
            // 没有更新成功任何记录，说明version比对失败已经有别人更新了
            // 要么放弃处理，要么重试
        }
    }

}
