package ru.alexsuvorov.paiste;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SeriesDescription extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cymbal_description, container, false);

        Bundle bundle = getArguments();
        Integer selectedProduct;

        //ReDesing link:  https://habrahabr.ru/post/270121/
        /*
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        if (bundle != null) {
            selectedProduct = bundle.getInt("productIndex");
            Toast toast = Toast.makeText(getContext(),
                    "Выбран продукт номер " + selectedProduct, Toast.LENGTH_SHORT);
            //toast.show();


            //Series Image

            TypedArray cymbalSeriesPicsArr = getResources().obtainTypedArray(R.array.cymbalSeriesPics);
            ImageView img = (ImageView) view.findViewById(R.id.seriesLogo);
            img.setImageDrawable(cymbalSeriesPicsArr.getDrawable(selectedProduct));

            cymbalSeriesPicsArr.recycle();


            //Series Name
            String[] cymbalSeriesNamesArr = getResources().getStringArray(R.array.cymbalSeriesNames);
            getActivity().setTitle(cymbalSeriesNamesArr[selectedProduct]);
            /*
            TextView seriesName = (TextView) view.findViewById(R.id.cymbalSeriesName);
            seriesName.setText(cymbalSeriesNamesArr[selectedProduct]);


        <TextView
            android:id="@+id/cymbalSeriesName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=""
            android:textSize="20sp"
            android:textColor="@color/black"
            android:textStyle="bold"
            android:layout_marginTop="17dp"
            android:layout_below="@+id/seriesLogo"
            android:layout_alignParentLeft="true"
            android:layout_alignParentStart="true" />
            */

            //Описание серии
            String[] cymbalSeriesDescriptionArr = getResources().getStringArray(R.array.cymbalSeriesDescription);
            TextView seriesDescription = (TextView) view.findViewById(R.id.seriesDescriptionText);
            seriesDescription.setText(Html.fromHtml(cymbalSeriesDescriptionArr[selectedProduct]));

        }

        return view;
    }

}