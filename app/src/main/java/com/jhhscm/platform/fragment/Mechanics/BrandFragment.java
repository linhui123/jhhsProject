package com.jhhscm.platform.fragment.Mechanics;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentBrandBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.LettersSorting;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Trans2PinYin;
import com.jhhscm.platform.views.LettersView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BrandFragment extends AbsFragment<FragmentBrandBinding> {
    private LettersAdapter mAdapter;
    private int type = 1;// 1 选择品牌； 2 跳转新机列表;3选择机型返回

    public static BrandFragment instance() {
        BrandFragment view = new BrandFragment();
        return view;
    }

    @Override
    protected FragmentBrandBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBrandBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");
        EventBusUtil.registerEvent(this);
//        mDataBinding.recyclerview.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
//        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new InnerAdapter(getContext());
//        mDataBinding.recyclerview.setAdapter(mAdapter);
        findBrand();
    }

    /**
     * 获取品牌列表
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "1");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private FindBrandBean findCategoryBean;

    private void doSuccessResponse(FindBrandBean categoryBean) {
        this.findCategoryBean = categoryBean;
        initLetterView();
//        mAdapter.setData(categoryBean.getResult());
//        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
//      mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
    }

//    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBrandBean.ResultBean> {
//        public InnerAdapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        public AbsRecyclerViewHolder<FindBrandBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new BrandViewHolder(mInflater.inflate(R.layout.item_mechanics_brand, parent, false), type);
//        }
//    }

    private String[] strChars = {"BackUpPhoneBean", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private void initLetterView() {
        if (findCategoryBean.getResult().size() == 0)
            return;

        final List<FindBrandBean.ResultBean> hospitalListBeans = parsingData();//把拼音首字母也加到bean中

        //这里把字母的数组设置一下
        //首先把有的挑出来
        List<String> list = new ArrayList<>();
//        // 隐藏含*院部
//        list.add("*");
        for (int i1 = 0; i1 < strChars.length; i1++) {
            boolean haveLetter = false;
            for (int i = 0; i < hospitalListBeans.size(); i++) {
                String l = hospitalListBeans.get(i).getLetter();
                if (l.equals(strChars[i1]))
                    haveLetter = true;
            }
            if (haveLetter)
                list.add(strChars[i1]);
        }

        String[] mStrChars = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mStrChars[i] = list.get(i);
        }
        ViewGroup.LayoutParams layoutParams = mDataBinding.mLettersView.getLayoutParams();
        layoutParams.height = mStrChars.length * 35 + 80;
        mDataBinding.mLettersView.setLayoutParams(layoutParams);
        mDataBinding.mLettersView.invalidate();

        mDataBinding.mLettersView.setStrChars(mStrChars);
        mDataBinding.mLettersView.setmTextView(mDataBinding.tvToast);//把toasttextview传进去用作展示中间的方框

        mDataBinding.mLettersView.setOnLettersListViewListener(new LettersView.OnLettersListViewListener() {
            @Override
            public void onLettersListener(String s) {
                //对应的位置
                int position = mAdapter.getPositionForNmae(s.charAt(0));
                //移动
                mDataBinding.recyclerview.setSelection(position);
            }
        });
        //对字母进行排序A-Z #
        Collections.sort(hospitalListBeans, new LettersSorting());
        //加载适配器
        mAdapter = new LettersAdapter(getContext(), hospitalListBeans, type);
        //设置数据
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String letter = hospitalListBeans.get(firstVisibleItem).getLetter();
//                SLog.E("此时的pos是  "+firstVisibleItem);
//                SLog.E("此时的字母是  "+letter);
                int index = Arrays.binarySearch(mDataBinding.mLettersView.getStrChars(), letter);
//                SLog.E("此时的字母位置是  "+mDataBinding.mLettersView.getStrChars()[index]);
                if (index != mDataBinding.mLettersView.getCheckIndex()) {
                    mDataBinding.mLettersView.setCheckIndex(index);
                    mDataBinding.mLettersView.invalidate();
                }

            }
        });
    }

    /**
     * 联系人数组转换实体数据
     *
     * @return
     */
    private List<FindBrandBean.ResultBean> parsingData() {
        List<FindBrandBean.ResultBean> hospitalListBeans = new ArrayList<>();
        for (int i = 0; i < findCategoryBean.getResult().size(); i++) {
            // 转换拼音截取首字母并且大写
            String pinyin = Trans2PinYin.trans2PinYin(findCategoryBean.getResult().get(i).getName());
//            if (findCategoryBean.getResult().get(i).getName().equals("重庆分院")) {
//                pinyin = "chongqinfenyuan";
//            }
//            if (findCategoryBean.getResult().get(i).getName().equals("长沙分院")) {
//                pinyin = "changshafenyuan";
//            }
            Log.i("parsingData", "pinyin:" + pinyin);
            String letter = pinyin.substring(0, 1).toUpperCase();
            Log.i("parsingData", "letter:" + letter);
            findCategoryBean.getResult().get(i).setLetter(letter);
            hospitalListBeans.add(findCategoryBean.getResult().get(i));

        }
        return hospitalListBeans;
    }

    public void onEvent(FinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
