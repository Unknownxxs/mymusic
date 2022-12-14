package com.miss.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.lzx.starrysky.StarrySky;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;

import okhttp3.OkHttpClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        OkGo.getInstance().init(getApplication()).setOkHttpClient(builder.build());
        StarrySky.init(getApplication()).apply();
        StarrySky.setIsOpenNotification(true);
        final Activity activity=this;

   new Thread(new Runnable(){

                @Override
                public void run() {

                  try {
                        Log.v(":,," ,OkGo.get("http://42.192.226.221:3000//register/anonimous").execute().body().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                    runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                activity.startActivity(new Intent(getApplication(),musicmain.class));
                                activity.finish();
                            }
                        });
                }
                
            }).start();
        }
    private long firstBackTime;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstBackTime > 2000) {
            Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
            firstBackTime = System.currentTimeMillis();
            return;
        }

        super.onBackPressed();
    }
}
