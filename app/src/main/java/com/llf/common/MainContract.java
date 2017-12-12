package com.llf.common;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;
import com.llf.common.entity.ApplicationEntity;

/**
 * Created by llf on 2017/10/20.
 */

public interface MainContract {
    interface View extends BaseView {
        void returnResult(String result);
        //void returnUpdateResult(ApplicationEntity entity);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
        void update(ApplicationEntity entity);
    }
}
