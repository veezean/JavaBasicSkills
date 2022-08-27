package com.veezean.skills.lock;

import java.util.Random;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/7/27
 */
public class DeployedProcessService {


    public synchronized void manageDeployedProcessInfo(VmService vmService) {
        System.out.println(Thread.currentThread().getName() + " 开始获取进程信息...");
        // 获取进程信息
        collectProcessInfo();
        // 获取进程所在VM信息
        vmService.manageVmInfo(this);
        System.out.println(Thread.currentThread().getName() + " 开始获取进程信息...");
    }



    private void collectProcessInfo() {
        try {
            Thread.sleep(500L + new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
