package ru.alexsuvorov.paistewiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.Product;


public class CymbalChoiseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Product> objects;
    OnProductSelected mOnProductSelected;

    public CymbalChoiseAdapter(Context contex, ArrayList<Product> products, OnProductSelected onProductSelected) {
        ctx = contex;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mOnProductSelected = onProductSelected;
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.cymbal_item, parent, false);
        }

        Product p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: название, описание
        // и картинка
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.cymbalName);
        //((TextView) view.findViewById(R.id.tvSubDescr)).setText(p.cymbalSubName);
        ((ImageView) view.findViewById(R.id.ivImage)).setImageDrawable(p.cymbalImage);
        Button chooseButton = view.findViewById(R.id.chooseButton);

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOnProductSelected.onProductSelected(position);
                //Когда нажали на кнопку , адаптер вернет в функцию onProductSelected
                //которая находится во фрагменте, позицию товара в листе которую выбрали,
                //то есть в фрагменте мы будем знать что выбрали
                //и сможем передать только один продукт в следующий экран
            }

        });

        return view;
    }

    // товар по позиции
    private Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    public interface OnProductSelected {
        void onProductSelected(int productIndex);
    }

}