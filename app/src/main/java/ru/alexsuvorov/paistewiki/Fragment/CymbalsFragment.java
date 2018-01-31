package ru.alexsuvorov.paistewiki.Fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.Adapter.CymbalChoiseAdapter;
import ru.alexsuvorov.paistewiki.ItemModel.Product;
import ru.alexsuvorov.paistewiki.R;

import static ru.alexsuvorov.paistewiki.R.id.container;

public class CymbalsFragment extends Fragment implements CymbalChoiseAdapter.OnProductSelected {

    ArrayList<Product> products = new ArrayList<Product>();
    CymbalChoiseAdapter boxAdapter;
    Fragment fragment = null;
    Class fragmentClass = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cymbals_fragment, container, false);

        // создаем адаптер
        products.clear();
        fillData();
        boxAdapter = new CymbalChoiseAdapter(getContext(), products, this); //this - интерфейс


        // настраиваем список
        ListView lvMain = (ListView) view.findViewById(R.id.cymbalsList);
        lvMain.setAdapter(boxAdapter);
        return view;

    }

    // генерируем данные для адаптера
    void fillData() {
        for (int i = 0; i <= 17; i++) {
            String[] cymbalSeriesNamesArr = getResources().getStringArray(R.array.cymbalSeriesNames);
            //String[] cymbalSeriesSubNamesArr = getResources().getStringArray(R.array.cymbalSeriesSubNames);

            //TypedArray cymbalPicsArr = getResources().obtainTypedArray(R.array.cymbalPics);
            TypedArray cymbalSeriesPicsArr = getResources().obtainTypedArray(R.array.cymbalSeriesPics);

            products.add(new Product(cymbalSeriesNamesArr[i], null,
                    cymbalSeriesPicsArr.getDrawable(i), null)); //

            cymbalSeriesPicsArr.recycle();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.nav_header_cymbalsbutton);
    }

    //Когда нажали на кнопку , адаптер вернет в функцию onProductSelected
    //которая находится во фрагменте, позицию товара в листе которую выбрали,
    //то есть в фрагменте мы будем знать что выбрали
    //и сможем передать только один продукт в следующий экран

    @Override
    public void onProductSelected(int productIndex) {
        //Product selectedProduct = products.get(productIndex);

        fragmentClass = SeriesDescriptionFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SeriesDescriptionFragment SerDescr = new SeriesDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("productIndex", productIndex);
        SerDescr.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(container, SerDescr).commit();
    }

}