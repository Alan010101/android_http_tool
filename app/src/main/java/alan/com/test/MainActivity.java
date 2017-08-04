package alan.com.test;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.alan.http.AppException;
import com.alan.http.BaseActivity;
import com.alan.http.FileCallBack;
import com.alan.http.JsonCallBack;
import com.alan.http.Request;
import com.alan.http.RequestManager;
import com.model.User;

import java.io.File;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TelephonyManager test = (TelephonyManager)this.getSystemService("phone");
//        final String deviceId = test.getDeviceId();
//        System.out.println("=="+deviceId);

        Button testBtn = (Button)findViewById(R.id.testBtn);
        Button testBtn1 = (Button)findViewById(R.id.testBtn1);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Request request = new Request("http://www.geju100.com/mapi_v2/User/getUserInfo?userId=18377");
                    request.setCallback(new JsonCallBack<User>() {

                        @Override
                        public void onSuccess(User user) {
                            System.out.println("============>"+user.getId());
                            System.out.println("============>"+user.getNickname());
                            System.out.println("============>"+user.getSmallAvatar());
                        }

                        @Override
                        public void onFailure(AppException e) {
                            if(e.getStatusCode()==403){
                                if("password incorrect".equals(e.getResponseMessage())){
                                    //// TODO: 2017/8/3
                                }
                            }
                        }

                        @Override
                        public User preRequest() {
                            return super.preRequest();
                        }

                        @Override
                        public User postRequest(User user) {
                            return super.postRequest(user);
                        }
                    });
                    request.setGlobalExcetionListener(MainActivity.this);
                    RequestManager.getInstance().performRequest(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        testBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Request request = new Request("http://192.157.254.120/20170614beidian.mp3");
                final Request request = new Request("http://miniui.com/scripts/miniui/miniui.js");
                String path = Environment.getExternalStorageDirectory()+ File.separator+"/traces/111.js";
                request.setCallback(new FileCallBack() {

                    @Override
                    public void onProgressUpdated(int curLen, int totalLen) {
                        System.out.println("download:"+curLen+"/"+totalLen);
//                        if(curLen*100/totalLen>50){
//                            request.cancel();
//                        }
                    }

                    @Override
                    public void onSuccess(String result) {
                        System.out.println("download.result"+result);
                    }

                    @Override
                    public void onFailure(AppException e) {

                    }
                }.setCachePath(path));
                request.enableProgressUpdated(true);
                request.setTag(toString());
                RequestManager.getInstance().performRequest(request);
            }
        });
    }
}
