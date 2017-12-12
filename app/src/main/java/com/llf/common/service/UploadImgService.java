package com.llf.common.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.llf.basemodel.utils.LogUtil;

/**
 * Created by llf on 2017/10/24.
 * 上传多张图片的service
 * http://blog.csdn.net/lmj623565791/article/details/47143563
 */

public class UploadImgService extends IntentService {
    private static final String ACTION_UPLOAD_IMG = "com.llf.bstong.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH = "com.llf.bstong.intentservice.extra.IMG_PATH";
    public static final String UPLOAD_RESULT = "com.zhy.bstong.intentservice.UPLOAD_RESULT";

    public static void startUploadImg(Context context, String path) {
        Intent intent = new Intent(context, UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }

    public UploadImgService() {
        super("UploadImgService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String action = intent.getAction();
        if (ACTION_UPLOAD_IMG.equals(action)) {
            final String path = intent.getStringExtra(EXTRA_IMG_PATH);
            handleUploadImg(path);
        }
    }

    private void handleUploadImg(String path) {
        /**
         * 1.开始做上传图片的耗时操作
         * 2.把结果返回，可以用广播接收
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(UPLOAD_RESULT);
        intent.putExtra(EXTRA_IMG_PATH, path);
        sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        LogUtil.d("UploadImgService onCreate");
        keepAlive();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogUtil.d("UploadImgService onDestroy");
        super.onDestroy();
    }

    private void keepAlive() {
        try {
            Notification notification = new Notification();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            // 设置为前台服务避免kill，Android4.3及以上需要设置id为0时通知栏才不显示该通知；
            startForeground(0, notification);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
