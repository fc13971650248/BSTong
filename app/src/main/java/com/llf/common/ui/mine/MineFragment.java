package com.llf.common.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.llf.basemodel.base.BaseFragment;
import com.llf.basemodel.dialog.ShareDialog;
import com.llf.basemodel.utils.AppInfoUtil;
import com.llf.basemodel.utils.DateUtil;
import com.llf.basemodel.utils.ImageLoaderUtils;
import com.llf.basemodel.utils.LogUtil;
import com.llf.common.LoginActivity;
import com.llf.common.R;
import com.llf.common.constant.AppConfig;
import com.llf.common.ui.mine.contact.MineContract;
import com.llf.common.ui.mine.presenter.MinePresenter;
import com.llf.photopicker.ImgSelConfig;
import com.llf.photopicker.PickPhotoActivity;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.tencent.mm.sdk.platformtools.Util.bmpToByteArray;

/**
 * Created by llf on 2017/10/25.
 * 我的
 */

public class MineFragment extends BaseFragment implements MineContract.View, IUiListener, ShareDialog.OneShare {
    private static final String TAG = "MineFragment";
    Unbinder mUnbinder;

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.img_attention)
    ImageView mImgAttention;
    @BindView(R.id.img_track)
    ImageView mImgTrack;
    @BindView(R.id.img_share)
    ImageView mImgShare;
    @BindView(R.id.login)
    TextView tvlogin;

    private static final int CHANGE_AVATAIR = 1;
    private MineContract.Presenter mPresenter;
    private Tencent mTencent;
    private IWXAPI iwxapi;

    public static MineFragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mPresenter = new MinePresenter(this);
        /**
         * 推荐位，根据服务器传入的图标名字图标可动态配置
         */
        mImgAttention.setImageResource(getResources().getIdentifier("ic_mine_attention", "drawable", AppInfoUtil.getPackageName(getActivity())));
        mImgTrack.setImageResource(getResources().getIdentifier("ic_mine_track", "drawable", AppInfoUtil.getPackageName(getActivity())));
        mImgShare.setImageResource(getResources().getIdentifier("ic_mine_share", "drawable", AppInfoUtil.getPackageName(getActivity())));
        mTencent = Tencent.createInstance(AppConfig.APP_ID_QQ, getActivity());
        iwxapi = WXAPIFactory.createWXAPI(getActivity(), AppConfig.APP_ID_WEIXIN, false);
        iwxapi.registerApp(AppConfig.APP_ID_WEIXIN);
    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({R.id.setting, R.id.attention, R.id.track, R.id.share, R.id.night, R.id.service, R.id.update, R.id.reply, R.id.avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.attention:
                //startActivity(AttentionActivity.class);
                break;
            case R.id.track:
                startActivity(TrackActivity.class);
                break;
            case R.id.share:
                ShareDialog.show(getActivity(), this);
                break;
            case R.id.night:
               /* int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                getActivity().recreate();*/
                showToast("夜间模式");
                break;
            case R.id.service:
                showToast("客服中心");
                break;
            case R.id.update:
               // mPresenter.checkUpdate("http://api.fir.im/apps/latest/58f87d50959d6904280005a3?api_token=9f2408863ff25abccca986e5d4d9d6ba");
                showToast("已经是最新版本");
                break;
            case R.id.reply:
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, DateUtil.getCurrentHour());
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 0);
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, "设置计划");
                intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.avatar:
                PickPhotoActivity.startActivity(this, new ImgSelConfig.Builder().multiSelect(false).build(), CHANGE_AVATAIR);
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_AVATAIR && resultCode == Activity.RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(PickPhotoActivity.INTENT_RESULT);
            if (result.size() != 0) {
                ImageLoaderUtils.displayCircle(getActivity(), mAvatar, result.get(0));

                final File file = new File(result.get(0));
                Luban.get(getActivity())
                        .load(file)                     //传人要压缩的图片
                        .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                        .setCompressListener(new OnCompressListener() { //设置回调

                            @Override
                            public void onStart() {
                                LogUtil.d("压缩之前的图片大小" + file.length());
                            }

                            @Override
                            public void onSuccess(File file) {
                                LogUtil.d("压缩之后的图片大小" + file.length());
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.d("压缩出错了" + e.getMessage());
                            }
                        }).launch();    //启动压缩
            }
        }

        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorHint(msg);
    }

    @Override
    public void returnResult(String result) {
        LogUtil.d(TAG + result);
        showToast(result);
    }

    @Override
    public void onComplete(Object o) {
        showToast("qq分享成功");
    }

    @Override
    public void onError(UiError uiError) {
        showToast("qq分享出错" + uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        showToast("qq分享取消");
    }

    @Override
    public void weixinShare() {
        if (!AppInfoUtil.isWeixinAvilible(getActivity())) {
            showToast("请先安装微信");
            return;
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "https://fir.im/6s7z";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "出大事了";
        msg.description = "这里有个好强大的app";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    @Override
    public void qqShare() {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "出大事了");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "这里有个好强大的app");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://fir.im/6s7z");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://avatar.csdn.net/B/0/1/1_new_one_object.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "百事通");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(getActivity(), params, this);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
