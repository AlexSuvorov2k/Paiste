package ru.alexsuvorov.paistewiki.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import ru.alexsuvorov.paistewiki.BuildConfig;
import ru.alexsuvorov.paistewiki.R;

public class AboutAppFragment extends Fragment {

    String versionName = BuildConfig.VERSION_NAME;
    String BuildDate = "1.02.18";
    String[] donateItems;
    TextView selection;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        selection = view.findViewById(R.id.selection);
        final Spinner spinner = view.findViewById(R.id.donateBar);
        donateItems = getContext().getResources().getStringArray(R.array.donateArray);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, donateItems) {

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position + 1, convertView, parent);
            }

            //Пунктов в выпадающем списке
            public int getCount() {
                return donateItems.length - 1;
            }
        };

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position + 1);
                //selection.setText(item);
                //Переход к оплате
                /*Toast toast = Toast.makeText(getContext(),
                        item, Toast.LENGTH_SHORT);
                toast.show();*/
                spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner.setOnItemSelectedListener(itemSelectedListener);

        TextView version = view.findViewById(R.id.versionNumber);
        Resources res = getResources();
        String versionStr = res.getString(R.string.version);
        String versionOf = res.getString(R.string.versionData);
        version.setText(versionStr + " " + versionName + " " + versionOf + " " + BuildDate);
        return view;
    }

    public void openBrowser(View view) {

        String url = (String) view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

}