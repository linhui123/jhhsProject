package com.jhhscm.platform.views.dialog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogUpdateBinding;
import com.jhhscm.platform.permission.YXPermission;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.util.List;

public class UpdateDialog extends BaseDialog {
    private DialogUpdateBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;
    private String DOWNLOAD_URL;
    private String blsApp = "BLSAPP";
    private int a = 0;

    public interface CallbackListener {
        void clickYes();
    }

    public UpdateDialog(Context context, String content, UpdateDialog.CallbackListener listener) {
        this(context, content, listener, null);
    }

    public UpdateDialog(Context context, String content, UpdateDialog.CallbackListener listener, String sure) {
        super(context);
        setCancelable(false);
        this.DOWNLOAD_URL = content;
        this.mListener = listener;
        this.sure = sure;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_update, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.flikerbar.setProgress(0);
        mDataBinding.flikerbar.setBackgroundResource(R.color.color_cc);
        mDataBinding.llSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();
//                if (mListener != null) mListener.clickYes();
                if (mDataBinding.sure.getText().toString().equals("下载")) {
                    startDownload();
                } else if (mDataBinding.sure.getText().toString().equals("安装")) {
//                    openAPK(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + blsApp + ".apk");
                }
            }
        });
        update();
    }

    private void update() {
//        if (Aria.download(this).taskExists(DOWNLOAD_URL)) {
//            DownloadTarget target = Aria.download(this).load(DOWNLOAD_URL);
//            int a=target.getPercent();
//            mDataBinding.flikerbar.setProgress(8);
//        }
//
//        DownloadEntity entity = Aria.download(this).getDownloadEntity(DOWNLOAD_URL);
//        if (entity != null) {
//            int state = entity.getState();
//        }
        Aria.download(this).register();
    }


    private void startDownload() {

        YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "存储"))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(), new AcpListener() {
            @Override
            public void onGranted() {
                mDataBinding.flikerbar.setBackgroundResource(R.color.color_cc);

                Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setFilePath(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + blsApp + ".apk")
                        .start();
                mDataBinding.content.setText("下载完成（0%）");
                mDataBinding.sure.setText("下载中");
                mDataBinding.sure.setEnabled(false);
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });

    }

    private void resumeDownload() {
        mDataBinding.flikerbar.setBackgroundResource(R.color.color_cc);
        mDataBinding.flikerbar.setStop(false);
        Aria.download(this).load(DOWNLOAD_URL).resume();
    }

    private void stopDownload() {
//        mDataBinding.btnConfirm.setText("暂停");
        Aria.download(this).load(DOWNLOAD_URL).pause();
        mDataBinding.flikerbar.setStop(true);
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
//    @Download.onTaskRunning
    private void running(DownloadTask task) {
        int p = task.getPercent();    //任务进度百分比
        String speed = task.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
        long speed1 = task.getSpeed(); //原始byte长度速度
        Log.i("speed", speed);
        Log.i("speeds", p + "");
        String text = "下载完成(" + (p + 1) + ")%";
        mDataBinding.content.setText(text);
        mDataBinding.flikerbar.setProgress(p + 1);
    }

    //    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        mDataBinding.flikerbar.finishLoad();
        mDataBinding.sure.setText("安装");
        mDataBinding.sure.setEnabled(true);
        mDataBinding.content.setText("下载完成（100%）");
        openAPK(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + blsApp + ".apk");
    }


    /**
     * 安装apk
     *
     * @param fileSavePath
     */
    private void openAPK(String fileSavePath) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            data = FileProvider.getUriForFile(getContext(), "com.westcoast.blsapp.fileprovider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        Aria.download(this).load(DOWNLOAD_URL).pause();
        mDataBinding.flikerbar.setStop(true);
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }


    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }
}

