package com.example.advance.service;

//import org.springframework.web.bind.annotation.RequestBody;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Upload {

    private void updateImage(File imageFile) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("image" + "\"; filename=\"" + System.currentTimeMillis(), RequestBody.create(MediaType.parse("image/png"), imageFile));
        //ApiHelper.getApiService().upLoadImg("1", "avatar", HiGameKit.readCurrentUserTokenFromFile(), ApiHelper.device_id, bodyMap)
        //        .compose(ResponseTransformer.handleResult())
        //        .compose(TheadHelper.applySchedulers())
        //        .subscribe(upLoadImg -> {
        //            if (!mSubscribe.isDisposed()) {
        //                mSubscribe.dispose();
        //            }
        //            progressbar_upload.setVisibility(View.GONE);
        //            Toaster.show("图片上传成功");
        //
        //            mGuid = upLoadImg.guid;
        //            tv_upload_text.setVisibility(View.GONE);
        //            setNetEnable();
        //        }, throwable -> {
        //            if (!mSubscribe.isDisposed()) {
        //                mSubscribe.dispose();
        //            }
        //            progressbar_upload.setVisibility(View.GONE);
        //            tv_upload_text.setText("上传失败");
        //        });
    }
}
