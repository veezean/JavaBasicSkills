package com.veezean.skills.lock;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/28
 */
public class ThreadTest {

    public void buyProduct() {
        int price = getPrice();
        // 子线程同步处理部分操作
        new Thread(this::printTicket).start();
        // 主线程继续处理其它逻辑
        doOtherOperations(price);
    }

    private int getPrice() {
        return 2;
    }

    private void printTicket() {

    }

    private void doOtherOperations(int price) {

    }

}
