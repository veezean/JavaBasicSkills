package com.veezean.skills.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/23
 */
public class FutureService {

    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public void buyCoffeeAndOthers() throws ExecutionException, InterruptedException {
        goShopping();
        // 子线程中去处理做咖啡这件事，返回future对象
        Future<Coffee> coffeeTicket = threadPool.submit(this::makeCoffee);
        // 主线程同步去做其他的事情
        Bread bread = buySomeBread();
        // 主线程其他事情并行处理完成，阻塞等待获取子线程执行结果
        Coffee coffee = coffeeTicket.get();
        // 子线程结果获取完成，主线程继续执行
        eatAndDrink(bread, coffee);
    }


     private  void goShopping() {

     }

     private void eatAndDrink(Bread bread, Coffee coffee) {

     }

     private Coffee makeCoffee() {
        return null;
     }

     private Bread buySomeBread() {
        return null;
     }

    public static void main(String[] args) {

    }


    private static class Coffee {

    }

    private static class Bread {

    }
}
