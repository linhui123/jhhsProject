package com.jhhscm.platform.fragment.msg;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentArticleDetailBinding;
import com.jhhscm.platform.databinding.FragmentNewsListBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleDetailsAction;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetArticleDetailsBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.msg.html.LinkMovementMethodExt;
import com.jhhscm.platform.fragment.msg.html.MessageSpan;
import com.jhhscm.platform.fragment.msg.html.URLImageParser;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.photopicker.PhotoPickerActivity;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.jhhscm.platform.views.selector.ImageSelector;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class ArticleDetailFragment extends AbsFragment<FragmentArticleDetailBinding> {

    private String id;
    private Context mContect;

    public static ArticleDetailFragment instance() {
        ArticleDetailFragment view = new ArticleDetailFragment();
        return view;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 200) {
                MessageSpan ms = (MessageSpan) msg.obj;
                Object[] spans = (Object[]) ms.getObj();
                ArrayList<ImageSelectorItem> list = new ArrayList<>();
                for (Object span : spans) {
                    if (span instanceof ImageSpan) {
                        ImageSelectorItem imageSelectorItem = new ImageSelectorItem();
                        imageSelectorItem.imageUrl = ((ImageSpan) span).getSource();
                        list.add(imageSelectorItem);
                    }
                }
                if (mContect != null) {
                    ImageSelectorPreviewActivity.startActivity(mContect, 0, list, 0);
                }
            }
        }

        ;
    };

    @Override
    protected FragmentArticleDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentArticleDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        id = getArguments().getString("id");
        if (id != null && id.length() > 0) {
            getArticleDetails();
        }
    }

    /**
     * 消息列表
     */
    private void getArticleDetails() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("id", Integer.parseInt(id));
            map.put("article_type_list", 1);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getArticleDetails");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetArticleDetailsAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetArticleDetailsBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetArticleDetailsBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            closeDialog();
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        initView(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initView(GetArticleDetailsBean getArticleDetailsBean) {
        if (getArticleDetailsBean != null) {
            mDataBinding.tvTitle.setText(getArticleDetailsBean.getData().getTitle());
            mDataBinding.tvDate.setText(getArticleDetailsBean.getData().getRelease_time());
            mDataBinding.tvArtical.setText(Html.fromHtml(getArticleDetailsBean.getData().getContent(), new URLImageParser(mDataBinding.tvArtical), null));
            mContect = getContext();
            mDataBinding.tvArtical.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
        }
    }
}
