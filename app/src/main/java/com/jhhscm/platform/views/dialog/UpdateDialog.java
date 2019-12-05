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

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogUpdateBinding;
import com.jhhscm.platform.event.ForceCloseEvent;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.EventBusUtil;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.util.List;

public class UpdateDialog extends BaseDialog {
    private DialogUpdateBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private boolean sure;
    private String DOWNLOAD_URL;
    private String jhhsApp = "jhhsapp";
    private int a = 0;

    public interface CallbackListener {
        void clickYes();
    }

    public UpdateDialog(Context context, String content, UpdateDialog.CallbackListener listener) {
        this(context, content, listener, false);
    }

    public UpdateDialog(Context context, String content, UpdateDialog.CallbackListener listener, boolean sure) {
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
        Log.e("onInitView", "DOWNLOAD_URL " + DOWNLOAD_URL);
//        DOWNLOAD_URL = "http://wajueji.oss-cn-shenzhen.aliyuncs.com/jhhs_v1.0.1.apk?Expires=1881217110&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=XvjcBkQupSYOad6ol6Ll8iOmpWE%3D";
        mDataBinding.flikerbar.setProgress(0);
        mDataBinding.flikerbar.setBackgroundResource(R.color.color_cc);
        if (sure) {
            //退出应用
            mDataBinding.cancle.setText("退出");
        } else {
            mDataBinding.cancle.setText("取消");
        }

        mDataBinding.llSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.sure.getText().toString().equals("下载")) {
                    startDownload();
                } else if (mDataBinding.sure.getText().toString().equals("安装")) {
                    openAPK(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + jhhsApp + ".apk");
                }
            }
        });

        mDataBinding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aria.download(this).removeAllTask(true);
                if (sure) {
                    //退出应用
                    EventBusUtil.post(new ForceCloseEvent());
                } else {
                    dismiss();
                }
            }
        });

        update();
    }

    private void update() {
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
                        .setFilePath(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + jhhsApp + ".apk")
                        .start();
                Log.e("Aria", "DOWNLOAD_URL " + getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + jhhsApp + ".apk");
                mDataBinding.content.setText("下载完成（0%）");
                mDataBinding.sure.setText("下载中");
                mDataBinding.sure.setEnabled(false);

            }

            @Override
            public void onDenied(List<String> permissions) {
                dismiss();
            }
        });

    }

    private void resumeDownload() {
        mDataBinding.flikerbar.setBackgroundResource(R.color.color_cc);
        mDataBinding.flikerbar.setStop(false);
        Aria.download(this).load(DOWNLOAD_URL).resume();
    }

    private void stopDownload() {
        Aria.download(this).load(DOWNLOAD_URL).pause();
        mDataBinding.flikerbar.setStop(true);
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        int p = task.getPercent();    //任务进度百分比
//        String speed = task.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
//        long speed1 = task.getSpeed(); //原始byte长度速度
//        Log.i("speed", speed);
//        Log.i("speeds", p + "");
        String text = "下载完成(" + (p + 1) + ")%";
        mDataBinding.content.setText(text);
        mDataBinding.flikerbar.setProgress(p + 1);
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        mDataBinding.flikerbar.finishLoad();
        mDataBinding.sure.setText("安装");
        mDataBinding.sure.setEnabled(true);
        mDataBinding.content.setText("下载完成（100%）");
        openAPK(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + jhhsApp + ".apk");
    }

    /**
     * 安装apk
     *
     * @param fileSavePath
     */
    private void openAPK(String fileSavePath) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Log.e("openAPK", "filePath " + filePath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(getContext(), "com.jhhscm.platform.fileprovider", new File(filePath));
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


    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

