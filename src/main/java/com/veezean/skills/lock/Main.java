package com.veezean.skills.lock;

import java.util.concurrent.CompletableFuture;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/27
 */
public class Main {


    public static void main(String[] args) {
        VmService vmService = new VmService();
        DeployedProcessService deployedProcessService = new DeployedProcessService();

        CompletableFuture.allOf(CompletableFuture.runAsync(() -> vmService.manageVmInfo(deployedProcessService)),
                CompletableFuture.runAsync(() -> deployedProcessService.manageDeployedProcessInfo(vmService))).join();

        System.out.println("全部执行完成");
    }
}
