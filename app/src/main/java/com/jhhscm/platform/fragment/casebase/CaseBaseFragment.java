package com.jhhscm.platform.fragment.casebase;

/**
 * Created by Administrator on 2018/10/15/015.
 */

//public class CaseBaseFragment extends AbsFragment<FragmentCaseBaseBinding> implements AdapterView.OnItemClickListener {

//    private TypeAdapter mTypeAdapter;
//    private NameAdapter mNameAdapter;
//    private int mShowCount = 10;
//    private int mCurrentPage = 1;
//    private final int START_PAGE = mCurrentPage;
//    private FindCaseLevelEntity findCaseLevelEntity;
//    private String mType="2";
//    private String mLevel="1";
//    /**
//     * 传入的数据
//     */
//    private List<DropBean> drops;
//    /**
//     * 当前被选择的item位置
//     */
//    private int selectPosition;
//
//    DropDownAdapter dropDownAdapter;
//
//    public static CaseBaseFragment instance() {
//        CaseBaseFragment view = new CaseBaseFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentCaseBaseBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentCaseBaseBinding.inflate(inflater, container, attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        EventBusUtil.registerEvent(this);
//        findLevel();
//        setIsShowAddButton();
//        mDataBinding.ivCaseBaseAdd.setOnClickListener(createOnClickListener());
//        mDataBinding.wrvName.setLayoutManager(new GridLayoutManager(getContext(),2));
//        mNameAdapter = new NameAdapter(getContext());
//        mDataBinding.wrvName.setAdapter(mNameAdapter);
//        mDataBinding.wrvName.loadComplete(false, true);
//        //mDataBinding.wrvName.autoRefresh();
//        mDataBinding.wrvName.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
//            @Override
//            public void onRefresh(RecyclerView view) {
//                getOrderList(true);
//            }
//
//            @Override
//            public void onLoadMore(RecyclerView view) {
//                getOrderList(false);
//            }
//        });
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBusUtil.unregisterEvent(this);
//    }
//
//    public void onEvent(CaseBaseEvent event) {
//        getOrderList(true);
//    }
//
//    private void findLevel() {
////        if (getContext() != null) {
////            UserSession userSession = ConfigUtils.getCurrentUser(getContext());
////            if (userSession != null) {
////                onNewRequestCall(FindCaseLevelAction.newInstance(getContext()).request(new AHttpService.IResCallback<BaseEntity<FindCaseLevelEntity>>() {
////                    @Override
////                    public void onCallback(int resultCode, Response<BaseEntity<FindCaseLevelEntity>> response, BaseErrorInfo baseErrorInfo) {
////                        if (getView() != null) {
////                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
////                                return;
////                            }
////                            if (response != null) {
////                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
////                                if (response.body().getCode().equals("200")) {
////                                    initView(response.body().getResult());
////                                } else {
////                                    ToastUtils.show(getContext(), response.body().getMessage());
////                                }
////                            }
////                        }
////                    }
////                }));
////            }
////        }
//    }
//
//    private void getOrderList(final boolean refresh) {
////        if (getContext() != null) {
////            UserSession userSession = ConfigUtils.getCurrentUser(getContext());
////            if (userSession != null) {
////                mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
////                onNewRequestCall(FindCaseGGAction.newInstance(getContext(),mShowCount,mCurrentPage,mType,mLevel).request(new AHttpService.IResCallback<BaseEntity<FindCaseGGEntity>>() {
////                    @Override
////                    public void onCallback(int resultCode, Response<BaseEntity<FindCaseGGEntity>> response, BaseErrorInfo baseErrorInfo) {
////                        if (getView() != null) {
////                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
////                                mDataBinding.wrvName.loadComplete(false, true);
////                                mDataBinding.rlCaseBaseNull.setVisibility(VISIBLE);
////                                return;
////                            }
////                            if (response != null) {
////                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
////                                if (response.body().getCode().equals("200")) {
////                                    mDataBinding.rlCaseBaseNull.setVisibility(GONE);
////                                    doSuccessResponse(refresh, response.body().getResult());
////                                } else {
////                                    mDataBinding.wrvName.loadComplete(false, true);
////                                    ToastUtils.show(getContext(), response.body().getMessage());
////                                }
////                            }
////                        }
////                    }
////                }));
////            }
////        }
//    }
//
//    private void initView(FindCaseLevelEntity findCaseLevelEntity) {
//        this.findCaseLevelEntity = findCaseLevelEntity;
//        drops = new ArrayList<>();
//        for (int i = 0; i < findCaseLevelEntity.getCASE_PAGE2().size(); i++) {
//            DropBean dropBean = new DropBean(findCaseLevelEntity.getCASE_PAGE2().get(i).getCASELEVELNAME());
//            if (i == 0) {
//                mLevel=findCaseLevelEntity.getCASE_PAGE2().get(0).getCASELEVEL();
//                dropBean.setChoiced(true);
//            }
//            drops.add(dropBean);
//        }
//        dropDownAdapter = new DropDownAdapter(drops, getContext());
//        mDataBinding.wrvType.setAdapter(dropDownAdapter);
//        mDataBinding.wrvType.setOnItemClickListener(this);
//        if (dropDownAdapter != null && dropDownAdapter.getCount() > 0) {
//            mLevel = findCaseLevelEntity.getCASE_PAGE2().get(0).getCASELEVEL();
//        }
//        mDataBinding.wrvName.autoRefresh();
//    }
//
//
//    private void doSuccessResponse(boolean refresh, FindCaseGGEntity findCaseGGEntity) {
//        if (refresh) {
//            mNameAdapter.setData(CaseBaseItem.convert(findCaseGGEntity.getCASE_PAGE1(),mType));
//        } else {
//            mNameAdapter.append(CaseBaseItem.convert(findCaseGGEntity.getCASE_PAGE1(),mType));
//        }
//
//        mDataBinding.wrvName.getAdapter().notifyDataSetChanged();
//        mDataBinding.wrvName.loadComplete(mNameAdapter.getItemCount() == 0, ((float)findCaseGGEntity.getCASE1().getTOTALRESULT() / (float) findCaseGGEntity.getCASE1().getSHOWCOUNT()) > mCurrentPage);
//        if(mNameAdapter.getItemCount()==0){
//            mDataBinding.rlCaseBaseNull.setVisibility(VISIBLE);
//            mDataBinding.wrvName.setLoadmoreText("");
//        }
//    }
//
//    private CaseBaseType.OnDropItemSelectListener onDropItemSelectListener;
//
//    public void setOnDropItemSelectListener(CaseBaseType.OnDropItemSelectListener onDropItemSelectListener) {
//        this.onDropItemSelectListener = onDropItemSelectListener;
//    }
//
//    public interface OnDropItemSelectListener {
//        void onDropItemSelect(int Postion);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (selectPosition == position) {
//            drops.get(selectPosition).setChoiced(false);
//            selectPosition = -1;
//            if (onDropItemSelectListener != null) {
//                onDropItemSelectListener.onDropItemSelect(selectPosition);
//            }
//            return;
//        }
//        if (selectPosition != -1) {
//            drops.get(selectPosition).setChoiced(false);
//        }
//        drops.get(position).setChoiced(true);
//        dropDownAdapter.notifyDataSetChanged();
//        selectPosition = position;
//        if (onDropItemSelectListener != null) {
//            onDropItemSelectListener.onDropItemSelect(position);
//        }
//        mLevel=findCaseLevelEntity.getCASE_PAGE2().get(selectPosition).getCASELEVEL();
////        getOrderList(true);
//        mDataBinding.wrvName.autoRefresh();
//    }
//
//
//    private class TypeAdapter extends AbsRecyclerViewAdapter<CaseBaseTypeItem> {
//        public TypeAdapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        public AbsRecyclerViewHolder<CaseBaseTypeItem> onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new CaseBaseTypeViewHolder(mInflater.inflate(R.layout.item_case_base_type, parent, false));
//        }
//
//    }
//
//    class DropDownAdapter extends BaseAdapter {
//        private List<DropBean> drops;
//        private Context context;
//
//        public DropDownAdapter(List<DropBean> drops, Context context) {
//            this.drops = drops;
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            return drops.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            DropDownAdapter.ViewHolder holder;
//            if (convertView == null) {
//                holder = new DropDownAdapter.ViewHolder();
//                convertView = LayoutInflater.from(context).inflate(R.layout.item_case_base_type, parent, false);
//                holder.tv = (TextView) convertView.findViewById(R.id.btn_type);
//                convertView.setTag(holder);
//            } else {
//                holder = (DropDownAdapter.ViewHolder) convertView.getTag();
//            }
//            holder.tv.setText(drops.get(position).getName());
//            if (drops.get(position).isChoiced()) {
//                holder.tv.setTextColor(getResources().getColor(R.color.color_1890));
//                holder.tv.setBackgroundResource(R.color.white);
//            } else {
//                holder.tv.setTextColor(getResources().getColor(R.color.acc6));
//                holder.tv.setBackgroundResource(R.color.color_fafa);
//            }
//            return convertView;
//        }
//
//        private class ViewHolder {
//            TextView tv;
//        }
//    }
//
//
//    private class NameAdapter extends AbsRecyclerViewAdapter<CaseBaseItem> {
//        public NameAdapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        public AbsRecyclerViewHolder<CaseBaseItem> onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new CaseBaseNameViewHolder(mInflater.inflate(R.layout.item_case_base_name, parent, false));
//        }
//    }
//
//    public void onPrivareActionClick(boolean judge) {
//        if (judge) {
//            mType="2";
//            setIsShowAddButton();
////            getOrderList(true);
//            mDataBinding.wrvName.autoRefresh();
//        } else {
//            mType="1";
//            setIsShowAddButton();
////            getOrderList(true);
//            mDataBinding.wrvName.autoRefresh();
//        }
//    }
//
//    private void setIsShowAddButton(){
//        if(mType.equals("2")){
//            mDataBinding.ivCaseBaseAdd.setVisibility(GONE);
//        }else {
//            mDataBinding.ivCaseBaseAdd.setVisibility(VISIBLE);
//        }
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if(R.id.iv_case_base_add==viewId){
//                    NewCaseBaseActivity.start(getContext());
//                }
//            }
//        };
//    }
//
//    public void onSearchActionClick() {
//        CaseBaseSearchActivity.start(getContext(),mType);
//    }
//}
