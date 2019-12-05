package com.jhhscm.platform.fragment.aftersale;

import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemAftersaleStoreBinding;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AfterSaleViewHolder extends AbsRecyclerViewHolder<FindBusListBean.DataBean> {

    private ItemAftersaleStoreBinding mBinding;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public AfterSaleViewHolder(View itemView, double latitude, double longitude) {
        super(itemView);
        this.latitude = latitude;
        this.longitude = longitude;
        mBinding = ItemAftersaleStoreBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusListBean.DataBean item) {
        if (item != null) {
            if (getAdapterPosition() == 0) {
                mBinding.tv3.setVisibility(View.VISIBLE);
            } else {
                mBinding.tv3.setVisibility(View.GONE);
            }
            mBinding.tv1.setText(item.getBus_name());

            String location = "";
            if (item.getProvince_name() != null) {
                location = item.getProvince_name() + " ";
            }
            if (item.getCity_name() != null) {
                location = location + item.getCity_name() + " ";
            }
            if (item.getCounty_name() != null) {
                location = location + item.getCounty_name() + " ";
            }
            if (item.getAddress_detail() != null) {
                location = location + item.getAddress_detail() + " ";
            }
            mBinding.tv2.setText(location);

            if (item.getDistance() != null) {
                mBinding.tvStore.setVisibility(View.VISIBLE);
                mBinding.tvStore.setText("距离" + item.getDistance() + "km");
            } else {
                mBinding.tvStore.setVisibility(View.GONE);
            }

            if (item.getPic_url() != null) {
                String jsonString = "{\"pic_url\":" + item.getPic_url() + "}";
                Log.e("jsonString", "jsonString  " + jsonString);
                PicBean pic = JSON.parseObject(jsonString, PicBean.class);
                if (pic != null
                        && pic.getPic_url() != null && pic.getPic_url().size() > 0) {
                    ImageLoader.getInstance().displayImage(pic.getPic_url().get(0).getUrl(), mBinding.im);
                }
            }

            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 0) {
                        StoreDetailActivity.start(itemView.getContext(), item.getBus_code(),
                                latitude + "", longitude + "", true);
                    } else {
                        StoreDetailActivity.start(itemView.getContext(), item.getBus_code(),
                                latitude + "", longitude + "");
                    }
                }
            });
        }
    }

    public static class PicBean {

        private List<PicUrlBean> pic_url;

        public List<PicUrlBean> getPic_url() {
            return pic_url;
        }

        public void setPic_url(List<PicUrlBean> pic_url) {
            this.pic_url = pic_url;
        }

        public static class PicUrlBean {
            /**
             * name  : http: //wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D
             * url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/368f7a402b2d4314bb51d3e201738ecd.jpg?Expires=1888987302&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=1rq1HHTq5lfyvaNAQ9MRvi0s%2Ff8%3D
             * uid : 1573627216338
             * status : success
             */

            private String name;
            private String url;
            private long uid;
            private String status;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

