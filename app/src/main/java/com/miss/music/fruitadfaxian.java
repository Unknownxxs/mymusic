package com.miss.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class fruitadfaxian extends ArrayAdapter<fruitfaxian> {
    public fruitadfaxian(@NonNull Context context, int resource,  @NonNull List<fruitfaxian> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        fruitfaxian fruit=getItem(position);
        View view=  LayoutInflater.from(getContext()).inflate(R.layout.layfaxian,null);
      ImageView image1 =view.findViewById(R.id.listfaxianimage1);
        TextView text1 =view.findViewById(R.id.listfaxiantext1);
        ImageView image2 =view.findViewById(R.id.listfaxianimage2);
        TextView text2 =view.findViewById(R.id.listfaxiantext2);
        ImageView image3=view.findViewById(R.id.listfaxianimage3);
        TextView text3 =view.findViewById(R.id.listfaxiantext3);


            image1.setImageBitmap(fruit.getImage().get(0));
          text1.setText(fruit.getName().get(0));
        image2.setImageBitmap(fruit.getImage().get(1));
        text2.setText(fruit.getName().get(1));
        image3.setImageBitmap(fruit.getImage().get(2));
        text3.setText(fruit.getName().get(2));


        return view;
    }
//设置不可点击item
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    //结束
}
