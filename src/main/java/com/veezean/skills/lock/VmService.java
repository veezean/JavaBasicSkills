package com.veezean.skills.lock;

import java.util.Random;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/25
 */
public class VmService {

    public synchronized void manageVmInfo(DeployedProcessService deployedProcessService) {
        System.out.println(Thread.currentThread().getName() + " 开始获取VM信息...");
        // 获取此VM基础信息
        collectVmBasicInfo();
        // 获取此VM上已部署的进程信息
        deployedProcessService.manageDeployedProcessInfo(this);
        System.out.println(Thread.currentThread().getName() + " 结束获取VM信息...");
    }

    private void collectVmBasicInfo() {
        try {
            Thread.sleep(500L + new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
