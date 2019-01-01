package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.alexsuvorov.paistewiki.R;

public class BannerAdapter extends PagerAdapter {

    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
            R.drawable.banner4, R.drawable.banner5, R.drawable.banner6, R.drawable.banner7};
    private Context ctx;


    public BannerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_newsbanner, container, false);
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(images[position]);
        //container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        container.refreshDrawableState();
    }
}