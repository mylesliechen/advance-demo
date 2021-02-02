package com.example.advance;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RateLimiterDemoApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("ok");
    }

    @Test
    public void updateImage() {
        File imageFile = new File("/Users/chenxue198/Desktop/test.png");
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("image" + "\"; filename=\"" + System.currentTimeMillis(), RequestBody.create(MediaType.parse("image/png"), imageFile));
        //{\"x-real-ip\":\"172.16.149.106\",\"content-length\":\"75533\",\"host\":\"matchmaker-api-test.immomo.com\",\"connection\":\"close\",\"content-type\":\"multipart/form-data; boundary=7ee5a671-02a4-49e4-9049-3ff56438912f\",\"accept-encoding\":\"gzip\",\"user-agent\":\"HongNiang/1210 android/10 MI 9Xiaomi\"}
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
