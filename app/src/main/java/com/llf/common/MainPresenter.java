package com.llf.common;

import com.llf.basemodel.utils.DownloadUtil;
import com.llf.basemodel.utils.JsonUtils;
import com.llf.basemodel.utils.LogUtil;
import com.llf.basemodel.utils.OkHttpUtils;
import com.llf.common.entity.ApplicationEntity;

/**
 * Created by llf on 2017/10/25.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkUpdate(String url) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("应用更新:" + response);
                try {
                    ApplicationEntity entity = JsonUtils.deserialize(response, ApplicationEntity.class);
                    //mView.returnUpdateResult(entity);
                }catch (Exception e){
                    mView.returnResult("System Object");
                }
            }

            @Override
            public void onFailure(Exception e) {
                mView.returnResult(e.getMessage());
            }
        });
    }

    @Override
    public void update(ApplicationEntity entity) {
        DownloadUtil.downloadApk(App.instance, entity.getInstall_url(), entity.getName(), entity.getChangelog(), "xiuqu.apk");
    }
}
