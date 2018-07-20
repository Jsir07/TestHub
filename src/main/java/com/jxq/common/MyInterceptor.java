package com.jxq.common;

import com.aventstack.extentreports.Status;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import reporter.Listener.MyReporter;

import java.io.IOException;

/**
 * 自定义拦截器--超时拦截器
 * 验证响应时间超过100毫秒，则警告。
 * 也可以自定义添加拦截器
 *
 * @author jx
 * @Date: 2018/7/20 09:44
 */
public class MyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        long time = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
        if (time > 100) {
            MyReporter.report.log(Status.WARNING, MyReporter.getTestName() + " 接口耗时：" + time);
        }
        return response;
    }
}
