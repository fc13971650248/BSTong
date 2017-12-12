package com.llf.common.ui.mine.contact;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

/**
 * Created by llf on 2017/11/2.
 *
 */

public interface MineContract {
    interface View extends BaseView {
        void returnResult(String result);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
    }
}
