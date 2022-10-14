package com.veezean.skills.lock;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/28
 */
public class LockTest {

    public void updateArticle() {
        verifyAuthorInfo();
        checkArticleDuplication();
        checkBlackWords();
        saveToDb();
        loadToEs();
    }

    private void verifyAuthorInfo() {

    }

    private void checkArticleDuplication() {

    }

    private void checkBlackWords() {

    }

    private synchronized void saveToDb() {
        // ...
    }

    private void loadToEs() {

    }
}
