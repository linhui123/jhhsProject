package com.jhhscm.platform.fragment.aftersale;

import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemAftersaleStoreBinding;
import com.jhhscm.platform.shoppingcast.entity.CartInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AfterSaleViewHolder extends AbsRecyclerViewHolder<FindBusListBean.DataBean> {

    private ItemAftersaleStoreBinding mBinding;

    public AfterSaleViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemAftersaleStoreBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusListBean.DataBean item) {
        if (item != null) {
            mBinding.tv1.setText(item.getBus_name());
            if (item.getProvince_name() != null) {
                mBinding.tv2.setText(item.getProvince_name() + " ");
                if (item.getCity_name() != null) {
                    mBinding.tv2.append(item.getCity_name() + " ");
                    if (item.getAddress_detail() != null) {
                        mBinding.tv2.append(item.getAddress_detail());
                    }
                } else if (item.getAddress_detail() != null) {
                    mBinding.tv2.append(item.getAddress_detail());
                }
            } else if (item.getCity_name() != null) {
                mBinding.tv2.setText(item.getCity_name() + " ");
                if (item.getAddress_detail() != null) {
                    mBinding.tv2.append(item.getAddress_detail());
                }
            } else if (item.getAddress_detail() != null) {
                mBinding.tv2.setText(item.getAddress_detail());
            } else {
                mBinding.tv2.setText("");
            }

            mBinding.tvStore.setText("距离-km");
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
                    StoreDetailActivity.start(itemView.getContext());
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

