package com.jhhscm.platform.photopicker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.databinding.FragmentPhotopickerBinding;
import com.jhhscm.platform.event.ImageSelectorEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.photopicker.adapter.PhotoAdapter;
import com.jhhscm.platform.photopicker.adapter.PhotoFloderAdapter;
import com.jhhscm.platform.photopicker.bean.Photo;
import com.jhhscm.platform.photopicker.bean.PhotoFloder;
import com.jhhscm.platform.photopicker.util.OtherUtils;
import com.jhhscm.platform.photopicker.util.PhotoUtils;
import com.jhhscm.platform.tool.FileUtils;
import com.jhhscm.platform.tool.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 类说明：
 */
public class CaseBasePhotoPickerActivity extends AbsToolbarActivity {
    /**
     * 图片选择模式，int类型
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;
    /**
     * 最大图片选择次数，int类型
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 默认最大照片数量
     */
    public static final int DEFAULT_MAX_TOTAL = 9;
    /**
     * 是否显示相机，boolean类型
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 默认选择的数据集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    /**
     * 筛选照片配置信息
     */
    public static final String EXTRA_IMAGE_CONFIG = "image_config";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";

    public static final String EXTRA_REQCODE = "req_code";

    /**
     * 选择多张图片
     *
     * @param context
     * @param code
     * @param images
     * @param selectMaxCount
     */
    public static void startActivity(Context context, int code, ArrayList<String> images, int selectMaxCount, String caseBaseId, String timeNodeId) {
        Intent intent = new Intent(context, CaseBasePhotoPickerActivity.class);
        intent.putExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        intent.putExtra(EXTRA_SHOW_CAMERA, false);
        intent.putStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST, images);
        intent.putExtra(EXTRA_REQCODE, code);
        intent.putExtra(EXTRA_SELECT_COUNT, selectMaxCount);
        intent.putExtra("caseBaseId", caseBaseId);
        intent.putExtra("timeNodeId",timeNodeId);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int code) {
        Intent intent = new Intent(context, CaseBasePhotoPickerActivity.class);
        intent.putExtra(EXTRA_SELECT_MODE, MODE_SINGLE);
        intent.putExtra(EXTRA_SHOW_CAMERA, false);
        intent.putExtra(EXTRA_REQCODE, code);
        context.startActivity(intent);
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt(EXTRA_SELECT_MODE, getIntent().getIntExtra(EXTRA_SELECT_MODE, 0));
        args.putBoolean(EXTRA_SHOW_CAMERA, getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, false));
        args.putSerializable(EXTRA_DEFAULT_SELECTED_LIST, getIntent().getSerializableExtra(EXTRA_DEFAULT_SELECTED_LIST));
        args.putInt(EXTRA_REQCODE, getIntent().getIntExtra(EXTRA_REQCODE, 0));
        args.putInt(EXTRA_SELECT_COUNT, getIntent().getIntExtra(EXTRA_SELECT_COUNT, 0));
        args.putString("caseBaseId",getIntent().getStringExtra("caseBaseId"));
        args.putString("timeNodeId",getIntent().getStringExtra("timeNodeId"));
        return args;
    }


    @Override
    protected boolean enableHomeButton() {
        return true;
    }

    @Override
    protected boolean enableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean enableOperateText() {
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.title_select_albums);
    }

    @Override
    protected void onToolbarRightClick(Context context) {
        super.onOneKeyShareClick(context);
        ((PhotoPickerFragment) mFragment).complete();
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return new PhotoPickerFragment();
    }

    public static class PhotoPickerFragment extends AbsFragment<FragmentPhotopickerBinding> implements PhotoAdapter.PhotoClickCallBack {
        private ImageCaptureManager mImageCaptureManager;
        private FragmentPhotopickerBinding mBinding;
        private int mCode;
        private String mCaseBaseId;
        private String mTimeNodeId;

        private final static String ALL_PHOTO = "所有图片";
        /**
         * 是否显示相机，默认不显示
         */
        private boolean mIsShowCamera = false;
        /**
         * 照片选择模式，默认是单选模式
         */
        private int mSelectMode = 0;
        /**
         * 最大选择数量，仅多选模式有用
         */
        private int mMaxNum;

        private Map<String, PhotoFloder> mFloderMap;
        private List<Photo> mPhotoLists = new ArrayList<>();
        private ArrayList<String> mSelectList = new ArrayList<>();
        private PhotoAdapter mPhotoAdapter;
        private ListView mFloderListView;
        /**
         * 文件夹列表是否处于显示状态
         */
        boolean mIsFloderViewShow = false;
        /**
         * 文件夹列表是否被初始化，确保只被初始化一次
         */
        boolean mIsFloderViewInit = false;

        @Override
        protected FragmentPhotopickerBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photopicker, container, false);
            initViews();
            return mBinding;
        }

        @Override
        protected void setupViews() {
            if(getArguments()!=null){
                mCaseBaseId=getArguments().getString("caseBaseId");
                mTimeNodeId=getArguments().getString("timeNodeId");
            }
        }

//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photopicker, container, false);
//            initViews();
//            return mBinding.getRoot();
//        }

        public void onEventMainThread(ImageSelectorEvent event) {
            if (event.getType() == ImageSelectorEvent.EVENT_DEL) {
                if (event.getDelImage() == null) return;
                if (event.getCode() == hashCode()) {
                    for (int i = 0; i < mSelectList.size(); i++) {
                        if (event.getDelImage().equals(mSelectList.get(i))) {
                            mSelectList.remove(i);
                            break;
                        }
                    }
                    mPhotoAdapter.removeImage(event.getDelImage());
                    refreshActionStatus();
                }
            }
        }

        private void initViews() {
            mImageCaptureManager = new ImageCaptureManager(getActivity());
            mBinding.gvImages.setGravity(Gravity.CENTER);
            mBinding.gvImages.setNumColumns(getNumColnums());
            mBinding.gvImages.setHorizontalSpacing(getResources().getDimensionPixelOffset(R.dimen.space_size));
            mBinding.gvImages.setVerticalSpacing(getResources().getDimensionPixelOffset(R.dimen.space_size));

            mCode = getArguments().getInt(EXTRA_REQCODE, 0);
            // 照片属性
//            imageConfig = getIntent().getParcelableExtra(EXTRA_IMAGE_CONFIG);

            // 选择图片数量
            mMaxNum = getArguments().getInt(EXTRA_SELECT_COUNT, DEFAULT_MAX_TOTAL);

            // 图片选择模式
            mSelectMode = getArguments().getInt(EXTRA_SELECT_MODE, MODE_SINGLE);

            // 默认选择
            if (mSelectMode == MODE_MULTI) {
                ArrayList<String> tmp = getArguments().getStringArrayList(EXTRA_DEFAULT_SELECTED_LIST);
                if (tmp != null && tmp.size() > 0) {
                    mSelectList.addAll(tmp);
                }
            }
            // 是否显示照相机
            mIsShowCamera = getArguments().getBoolean(EXTRA_SHOW_CAMERA, false);

//            refreshActionStatus();

            if (!OtherUtils.isExternalStorageAvailable()) {
                Toast.makeText(getActivity(), "No SD card!", Toast.LENGTH_SHORT).show();
                return;
            }
            //加载图片
            getPhotosTask.execute();
        }

        /**
         * 显示或者隐藏文件夹列表
         *
         * @param floders
         */
        private void toggleFloderList(final List<PhotoFloder> floders) {
            //初始化文件夹列表
            if (!mIsFloderViewInit) {
                ViewStub floderStub = (ViewStub) mBinding.getRoot().findViewById(R.id.floder_stub);
                floderStub.inflate();
                View dimLayout = mBinding.getRoot().findViewById(R.id.dim_layout);
                mFloderListView = (ListView) mBinding.getRoot().findViewById(R.id.listview_floder);
                final PhotoFloderAdapter adapter = new PhotoFloderAdapter(getActivity());
                adapter.setData(floders);
                mFloderListView.setAdapter(adapter);
                mFloderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (PhotoFloder floder : floders) {
                            floder.setIsSelected(false);
                        }
                        PhotoFloder floder = floders.get(position);
                        floder.setIsSelected(true);
                        adapter.notifyDataSetChanged();

                        mPhotoLists.clear();
                        mPhotoLists.addAll(floder.getPhotoList());
                        if (ALL_PHOTO.equals(floder.getName())) {
                            mPhotoAdapter.setIsShowCamera(mIsShowCamera);
                        } else {
                            mPhotoAdapter.setIsShowCamera(false);
                        }
                        //这里重新设置adapter而不是直接notifyDataSetChanged，是让GridView返回顶部
                        mBinding.gvImages.setAdapter(mPhotoAdapter);
                        mBinding.photoPickerFooter.btnAlbum.setText(floder.getName());
                        toggle();
                    }
                });
                dimLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsFloderViewShow) {
                            toggle();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                initAnimation(dimLayout);
                mIsFloderViewInit = true;
            }
            toggle();
        }

        /**
         * 弹出或者收起文件夹列表
         */
        private void toggle() {
            if (mIsFloderViewShow) {
                outAnimatorSet.start();
                mIsFloderViewShow = false;
            } else {
                inAnimatorSet.start();
                mIsFloderViewShow = true;
            }
        }

        /**
         * 初始化文件夹列表的显示隐藏动画
         */
        AnimatorSet inAnimatorSet = new AnimatorSet();
        AnimatorSet outAnimatorSet = new AnimatorSet();

        private void initAnimation(View dimLayout) {
            ObjectAnimator alphaInAnimator, alphaOutAnimator, transInAnimator, transOutAnimator;
            //获取actionBar的高
            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
            /**
             * 这里的高度是，屏幕高度减去上、下tab栏，并且上面留有一个tab栏的高度
             * 所以这里减去3个actionBarHeight的高度
             */
            int height = OtherUtils.getHeightInPx(getActivity()) - 3 * actionBarHeight;
            alphaInAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0f, 0.7f);
            alphaOutAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0.7f, 0f);
            transInAnimator = ObjectAnimator.ofFloat(mFloderListView, "translationY", height, 0);
            transOutAnimator = ObjectAnimator.ofFloat(mFloderListView, "translationY", 0, height);

            LinearInterpolator linearInterpolator = new LinearInterpolator();

            inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
            inAnimatorSet.setDuration(300);
            inAnimatorSet.setInterpolator(linearInterpolator);
            outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
            outAnimatorSet.setDuration(300);
            outAnimatorSet.setInterpolator(linearInterpolator);
        }

//        /**
//         * 选择文件夹
//         *
//         * @param photoFloder
//         */
//        public void selectFloder(PhotoFloder photoFloder) {
//            mPhotoAdapter.setDatas(photoFloder.getPhotoList());
//            mPhotoAdapter.notifyDataSetChanged();
//        }

        /**
         * 获取照片的异步任务
         */
        private AsyncTask getPhotosTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
//                showProgressDialog();
//                mProgressDialog = ProgressDialog.show(getActivity(), null, "loading...");
            }

            @Override
            protected Object doInBackground(Object[] params) {
                mFloderMap = PhotoUtils.getPhotos(
                        getActivity().getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
//                closeProgressDialog();
                getPhotosSuccess();
            }
        };

        private void getPhotosSuccess() {
            mPhotoLists.addAll(mFloderMap.get(ALL_PHOTO).getPhotoList());

            mPhotoAdapter = new PhotoAdapter(getActivity(), mPhotoLists, getItemImageWidth());
            mPhotoAdapter.setIsShowCamera(mIsShowCamera);
            mPhotoAdapter.setDefaultSelected(mSelectList);
            mPhotoAdapter.setSelectMode(mSelectMode);
            mPhotoAdapter.setMaxNum(mMaxNum);
            mPhotoAdapter.setPhotoClickCallBack(this);
            mBinding.gvImages.setAdapter(mPhotoAdapter);
            Set<String> keys = mFloderMap.keySet();
            final List<PhotoFloder> floders = new ArrayList<>();
            for (String key : keys) {
                if (ALL_PHOTO.equals(key)) {
                    PhotoFloder floder = mFloderMap.get(key);
                    floder.setIsSelected(true);
                    floders.add(0, floder);
                } else {
                    floders.add(mFloderMap.get(key));
                }
            }
            mBinding.photoPickerFooter.btnAlbum.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    toggleFloderList(floders);
                }
            });

            // 预览
            mBinding.photoPickerFooter.btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoPreviewActivity.startActivity(getActivity(), PhotoPickerFragment.this.hashCode(), mSelectList, 0);
                }
            });

            mBinding.gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mPhotoAdapter.isShowCamera() && position == 0) {
                        if (mPhotoAdapter.getmSelectedPhotos().size() >= mMaxNum) {
                            Toast.makeText(getActivity(), R.string.photopicker_msg_amount_limit, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showCamera();
                        return;
                    }
                    selectPhoto(mPhotoAdapter.getItem(position));
                }
            });

            mPhotoAdapter.notifyDataSetChanged();
            refreshActionStatus();
        }

        @Override
        public void onPhotoClick() {
            refreshActionStatus();
        }

        private void refreshActionStatus() {
            List<String> list = mPhotoAdapter.getmSelectedPhotos();
            String text = getString(R.string.photopicker_done_with_count, list.size(),mMaxNum);
            ((CaseBasePhotoPickerActivity) getActivity()).setToolBarRightText(text,true);
            boolean hasSelected = list.size() > 0;
            mBinding.photoPickerFooter.btnPreview.setEnabled(hasSelected);
            if (hasSelected) {
                mBinding.photoPickerFooter.btnPreview.setText(getResources().getString(R.string.photopicker_preview) + "(" + (list.size()) + ")");
            } else {
                mBinding.photoPickerFooter.btnPreview.setText(getResources().getString(R.string.photopicker_preview));
            }
        }

        /**
         * 点击选择某张照片
         *
         * @param photo
         */
        private void selectPhoto(Photo photo) {
            if (photo == null) {
                return;
            }
            String path = photo.getPath();
            if (mSelectMode == MODE_SINGLE) {
                mSelectList.add(path);
                complete();
            }
        }

        /**
         * 获取GridView Item宽度
         *
         * @return
         */
        private int getItemImageWidth() {
            int cols = getNumColnums();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
            return (screenWidth - columnSpace * (cols - 1)) / cols;
        }

        /**
         * 选择相机
         */
        private void showCamera() {
            try {
                Intent intent = mImageCaptureManager.dispatchTakePictureIntent();
                startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                Toast.makeText(getActivity(), R.string.photopicker_msg_no_camera, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    // 相机拍照完成后，返回图片路径
                    case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                        if (mImageCaptureManager.getCurrentPhotoPath() != null) {
                            mImageCaptureManager.galleryAddPic();
                            mSelectList.add(FileUtils.getPhotoImageUri(mImageCaptureManager.getCurrentPhotoPath()));
                        }
                        complete();
                        break;
                }
            }
        }

        /**
         * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列
         *
         * @return
         */
        private int getNumColnums() {
            int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
            return cols < 4 ? 4 : cols;
        }


        // 返回已选择的图片数据
        private void complete() {
            if(mSelectList.size()==0){
                ToastUtils.show(getContext(),"请选择图片");
            }else {
//                CaseBaseUploadingActivity.start(getContext(),mSelectList,mCaseBaseId,mTimeNodeId);
//                EventBusUtil.post(ImageSelectorEvent.newInstance(ImageSelectorEvent.EVENT_CLOTHESPRESS_ADD, mSelectList));
                getActivity().finish();
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            mImageCaptureManager.onSaveInstanceState(outState);
            super.onSaveInstanceState(outState);
        }

    }


}
