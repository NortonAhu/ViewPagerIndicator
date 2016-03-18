package com.bluecup.hongyu.viewpagerindicator;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Des:
 * Created by hongyu
 * Date:16/3/17_下午2:47
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("yhtest");
//        Logger
//                .init(YOUR_TAG)               // 日志显示的Tag值
//                .setMethodCount(3)            // 输出方法数 默认值为2
//                .hideThreadInfo()             // 隐藏线程信息 默认显示
//                .setLogLevel(LogLevel.NONE);  // 设置Log级别 默认为LogLevel.FULL
//        .setMethodOffset(2)           // 输出方法层级 默认值为0
    }
}
