package com.miss.music;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzy.okgo.OkGo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruitsousuo> {

    public FruitAdapter(@NonNull Context context, int resource,  @NonNull List<Fruitsousuo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Fruitsousuo fruit=getItem(position);
      View view=  LayoutInflater.from(getContext()).inflate(R.layout.viewitemsousuo,null);
ImageView ss=  view.findViewById(R.id.list_itemtianjia);

   ss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v("bugyugy","fgyugfysa8fgsyfdsty");
                SongInfo x=new SongInfo();
                x.setSongId(fruit.getSongid());
                x.setSongUrl(getSongurl(fruit.getSongid()));
                StarrySky.with().addSongInfo(x);

            }
        });
        ImageView image=view.findViewById(R.id.list_itemimage);
        TextView name=view.findViewById(R.id.list_itemtextmingchen);
        TextView gesou=view.findViewById(R.id.list_textgesou);
        ss.setImageResource(R.drawable.tianjai);
       // image.setImageBitmap(fruit.getImage());
name.setText(fruit.getName());
gesou.setText(fruit.getgesou());

   Thread s= new Thread(()->{

    try {
        try {
            Bitmap sc=null;
            URL url1 = new URL(fruit.getImage());
            Log.v("ccv",fruit.getImage());
       sc  = BitmapFactory.decodeStream(url1.openStream());

            Bitmap finalSc = sc;
            image.post(()->{image.setImageBitmap(finalSc);});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }


});
s.start();


        return view;



    }
    String resuit;
    public String getSongurl(String songid) {

        Thread xs = new Thread(() -> {

            try {
                resuit = OkGo.get("http://42.192.226.221:3000/song/url?id=" + songid).execute().body().string();
                String[] sb = resuit.split("url\":\"");
                if (sb.length < 1) {
                    return;
                }
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
}
