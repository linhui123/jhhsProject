package com.jhhscm.platform.fragment.my.book;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddBookingActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.databinding.FragmentBookingDetailBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BookingDetailFragment extends AbsFragment<FragmentBookingDetailBinding> {

    private int type;
    private String data_code;
    private InnerAdapter mAdapter;
    private DetailToolBean detailToolBean;

    public static BookingDetailFragment instance() {
        BookingDetailFragment view = new BookingDetailFragment();
        return view;
    }

    @Override
    protected FragmentBookingDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBookingDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        type = getArguments().getInt("type");
        data_code = getArguments().getString("data_code");
        if (data_code != null) {
            detailTool(data_code);
        } else {
            ToastUtil.show(getContext(), "数据错误");
            getActivity().finish();
        }

        if (type == 0) {
            mDataBinding.rlPay.setVisibility(View.GONE);
            mDataBinding.rlTypePay.setVisibility(View.GONE);
        } else {
            mDataBinding.rlIncome.setVisibility(View.GONE);
            mDataBinding.rlUnIncome.setVisibility(View.GONE);
            mDataBinding.rlTypeIncome.setVisibility(View.GONE);
        }

        mDataBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailToolBean != null && detailToolBean.getData() != null) {
                    delTool();
                }
            }
        });

        mDataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailToolBean != null && detailToolBean.getData() != null) {
                    AddBookingActivity.start(getContext(), type, detailToolBean);
                }
            }
        });
    }

    private void detailTool(String data_code) {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("data_code", data_code);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "detailTool");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(DetailToolActin.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<DetailToolBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<DetailToolBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
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

    private void delTool() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("data_code", detailToolBean.getData().getData_code());
            map.put("id", detailToolBean.getData().getId());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "delTool");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(DelToolAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        ToastUtil.show(getContext(), "删除成功");
                                        EventBusUtil.post(new RefreshEvent());
                                        getActivity().finish();
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initView(DetailToolBean item) {
        detailToolBean = item;
        if (type == 0) {//收入
            mDataBinding.income.setText(item.getData().getPrice_1() + "元");
            mDataBinding.unIncome.setText(item.getData().getPrice_2() + "元");
            mDataBinding.typeIncome.setText(item.getData().getIn_type_name() + "");
        } else {//支出
            mDataBinding.pay.setText(item.getData().getPrice_3() + "元");
            mDataBinding.typePay.setText(item.getData().getOut_type_name() + "");
        }
        mDataBinding.content.setText(item.getData().getData_content());
        mDataBinding.remark.setText(item.getData().getDesc());
        if (item.getData().getData_time() != null) {
            mDataBinding.dataIncome.setText(DataUtil.getDateStr(item.getData().getData_time().length() > 10 ?
                            item.getData().getData_time().substring(0, 10) : item.getData().getData_time()
                    , "yyyy年MM月dd日"));
        } else {
            mDataBinding.dataIncome.setText("----年--月--日");
        }

        if (item.getData().getPic_small_url() != null && item.getData().getPic_small_url().length() > 10) {
            mDataBinding.isSchemeImage.setVisibility(View.VISIBLE);
            List<PbImage> items = new ArrayList<>();
            String[] strs = item.getData().getPic_small_url().split(",");
            if (strs.length > 0) {
                for (int i = 0; i < strs.length; i++) {
                    PbImage pbImage = new PbImage();
                    pbImage.setmUrl(strs[i].trim());
                    pbImage.setmToken(strs[i].trim());
                    items.add(pbImage);
                }
                mDataBinding.isSchemeImage.setPbImageList(items);
            }
        } else {
            mDataBinding.isSchemeImage.setVisibility(View.GONE);
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<AllSumByDataTimeBean.DataBean.DetailBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<AllSumByDataTimeBean.DataBean.DetailBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingListViewHolder(mInflater.inflate(R.layout.item_booking_list, parent, false));
        }
    }

    public void onEvent(RefreshEvent event) {
        if (data_code != null) {
            detailTool(data_code);
        } else {
            ToastUtil.show(getContext(), "数据错误");
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
