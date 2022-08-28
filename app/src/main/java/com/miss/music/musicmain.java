package com.miss.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzy.okgo.OkGo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class musicmain extends Activity implements View.OnClickListener {

    View faxian, soushuo, gerenzx,activmainxiayisou;
    ViewPager ViewPager;
    EditText sousuoedit;
    Button sousuostrate;
    ImageView imagezhuye;
    ListView sousuolin ,faxianlin;
    List<SongInfo> songInfoList = new ArrayList<>();

    TextView mainnametext;
    Context context;
    List<Fruitsousuo> fruitsousuo = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Faxianthread();
        context = this;


        LayoutInflater inflater = getLayoutInflater();
        ViewPager = findViewById(R.id.ViewPager);
        View view1 = inflater.inflate(R.layout.layout, null);
        View view2 = inflater.inflate(R.layout.layoutgd, null);
        View view3 = inflater.inflate(R.layout.mymusic, null);
        final List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中  
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        faxianlin=view1.findViewById(R.id.listfaxian);

        sousuoedit = view2.findViewById(R.id.sousuoedit);
        sousuostrate = view2.findViewById(R.id.sousuostrate);
        sousuolin = view2.findViewById(R.id.sousuolistview);


        faxian = findViewById(R.id.faxiananliu);
        soushuo = findViewById(R.id.sousuo);
        gerenzx = findViewById(R.id.gerenzx);
        imagezhuye = findViewById(R.id.imagezhuye);
 activmainxiayisou=findViewById(R.id.activmainxiayisou);
        mainnametext=findViewById(R.id.mainname);
        sousuostrate.setOnClickListener(this::onClick);
        faxian.setOnClickListener(this::onClick);
        soushuo.setOnClickListener(this::onClick);
        gerenzx.setOnClickListener(this::onClick);
activmainxiayisou.setOnClickListener(this::onClick);



        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
// TODO Auto-generated method stub  
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
// TODO Auto-generated method stub  
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
// TODO Auto-generated method stub  
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
// TODO Auto-generated method stub  
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        ViewPager.setAdapter(pagerAdapter);

//搜索item点击事件
        sousuolin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fruitsousuo fruit = fruitsousuo.get(position);
                String v=getSongurl(fruit.getSongid());
                Log.v("xxxxx",v);
        playsongs(v ,fruit.getSongid(),fruit.getName(),fruit.getgesou(),fruit.getImage());



            }
        });


    }

    String resuit = "";
    String songid = "";
    String zuozhename;
    String imageurl = "";
    Bitmap imagebitmap;
    JSONArray s;
    JSONObject json;

    String name;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousuostrate:
                fruitsousuo = new ArrayList<>();
                String url = "http://42.192.226.221:3000/search?keywords=" + sousuoedit.getText().toString() + "&limit=15";

                new Thread(() -> {

                    try {
                        resuit = OkGo.post(url).execute().body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        json = new JSONObject(resuit);
                        json = new JSONObject(json.getString("result"));
                        s = json.getJSONArray("songs");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int cs = 0; cs < s.length(); cs++) {

                        try {

                            String[] dd = s.getString(cs).split("\"id\":");

                            songid = dd[1].substring(0, dd[1].indexOf(","));
                            name = dd[1].substring(dd[1].indexOf("name\":\"") + 7, dd[1].indexOf("\",\"artists\""));
                            imageurl = dd[2].substring(dd[2].indexOf("img1v1Url\":\"") + 12, dd[2].indexOf("\",\"img1v1"));
                            zuozhename = dd[2].substring(dd[2].indexOf("name\":\"") + 7, dd[2].indexOf("\",\"picUrl"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
           /*
                    if(songid.contains("null")){continue;}
                        if(songid.equals("0")){continue;}
                        Log.v(songid,songid);
                        Log.v("vvdvvv",songres[cs]);
                        String[] v = songres[cs].split("img1v1Url\":\"");

                        String[] k = v[1].split("\",\"img");
                      imageurl = k[0];
                      name=dd[1].split("\",\"picUrl\":")[0];

*/


                        try {
                            try {
                                URL url1 = new URL(imageurl);
                                imagebitmap = BitmapFactory.decodeStream(url1.openStream());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Fruitsousuo ee = new Fruitsousuo(imagebitmap, name, zuozhename, songid);
                        fruitsousuo.add(ee);
                        if (cs == 5) {
                            runOnUiThread(() -> {
                                FruitAdapter adapter = new FruitAdapter(context, R.layout.viewitemsousuo, fruitsousuo);
                                //      adapter.add(ee);

                                sousuolin.setAdapter(adapter);
                            });
                        }
                    }


                    //  resuit= String.valueOf(s);


                    runOnUiThread(() -> {
                        //listview加载
                        FruitAdapter adapter = new FruitAdapter(context, R.layout.viewitemsousuo, fruitsousuo);
                        //      adapter.add(ee);
                        sousuolin.setAdapter(adapter);
                    });
                }).start();


                break;
            case R.id.faxiananliu:
                //发现
                ViewPager.setCurrentItem(0);

                break;
            case R.id.sousuo:
                //搜索按钮
                ViewPager.setCurrentItem(1);

                break;
            case R.id.gerenzx:
                //个人中心按钮
                ViewPager.setCurrentItem(2);

                break;
            case R.id.activmainxiayisou:
              // 下一首
                if ( StarrySky.with().isSkipToNextEnabled()) {  StarrySky.with().skipToNext();
                }else if (StarrySky.with().isSkipToPreviousEnabled()) {
                    StarrySky.with().skipToPrevious();
                }




                break;
            default:
                break;
        }
    }

    public String getSongurl(String songid) {

        Thread xs = new Thread(() -> {

            try {
                resuit = OkGo.get("http://42.192.226.221:3000/song/url?id=" + songid).execute().body().string();

                String[] sb = resuit.split("url\":\"");
                if (sb.length < 1) {return;}
                resuit = sb[1];
                String[] ss = resuit.split("\",\"br");

                resuit = ss[0];
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        xs.start();
        try {
            xs.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resuit;


    }
    public void playsongs(String urll,String songid,String name,String artist,Bitmap image){
       SongInfo s=  new SongInfo();
       Log.v("sf",urll);
       s.setSongUrl(urll);
       s.setSongName(name);
       s.setSongId(songid);
       s.setArtist(artist);
       StarrySky.with().addSongInfo(s);
       StarrySky.with().playMusicById(songid);
      // StarrySky.with().skipToNext();
runOnUiThread(()->{
    imagezhuye.setImageBitmap(imagebitmap);
    mainnametext.setText(name+"     "+artist);});
StarrySky.setIsOpenNotification(true);




    }


    public void Faxianthread()
    {


        new Thread(()->{
            String result="";
            JSONObject json;
List<fruitfaxian> fruitf=new ArrayList<>();
            try {
              result=  OkGo.get("http://42.192.226.221:3000/top/playlist?limit=20").execute().body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JSONArray s;
            try {
                json = new JSONObject(result);
       s=  json.getJSONArray("playlists");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
         List< Bitmap>   bitmap=new ArrayList<>();
            List< String>  text=new ArrayList<>();
            List< String>  id=new ArrayList<>();
            for (int cs=0;cs<s.length();cs++){
                String      bitmapurl="";
                String name="";
                Bitmap bit;
                String reason="";

                String idd="";
                try {
                  reason=  s.getString(cs);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                name=reason.substring(reason.indexOf("name\":\"")+7,reason.indexOf("\",\"id\":"));
                bitmapurl=reason.substring(reason.indexOf("\"coverImgUrl\":\"")+15,reason.indexOf("\",\"coverImgId"));
idd=reason.substring(reason.indexOf("\"id\":")+5,reason.indexOf(",\"trackNum"));

                try {
                    URL url = new URL(bitmapurl);
              bit=      BitmapFactory.decodeStream(url.openStream());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                text.add(name);
                bitmap.add(bit);
                id.add(idd);
                if (text.size()==3)
                {
                    List< Bitmap>   bitmapp=bitmap;
                    List< String>  textt=text;
                    List< String>  iddd=id;
                    runOnUiThread(()->{
                        fruitfaxian fruitfaxian=new fruitfaxian(bitmapp,textt,iddd);
                        fruitf.add(fruitfaxian);
                        fruitadfaxian adapter = new fruitadfaxian(context,R.layout.layfaxian,fruitf);
                        faxianlin.setAdapter(adapter);


                    });
                    bitmap=new ArrayList<>();
                    text=new ArrayList<>();
                    id=new ArrayList<>();


                }


            }

        }).start();


    }
}
