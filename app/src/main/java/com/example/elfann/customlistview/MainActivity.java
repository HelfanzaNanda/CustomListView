package com.example.elfann.customlistview;

import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, Object>> searchResult;

    ArrayList<HashMap<String, Object>> HasilMakanan;

    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText kotakPencarian = (EditText)findViewById(R.id.makanan);
        final ListView _listMakanan = (ListView) findViewById(R.id.ListMakanan);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final String namaMakanan1 [] = {
                "Olos",
                "Tahu Aci",
                "Tempe",
                "Bakso",
                "Mie Ayam"
        };
        String detailMakanan1 [] = {
                "Harga Rp. 7.000",
                "Harga Rp. 10.000",
                "Harga 5.000",
                "Harga Rp.12.000 ",
                "Harga Rp.15.000"
        };

        int [] gambar1 = {R.drawable.olos, R.drawable.tahuaci, R.drawable.tempe, R.drawable.bakso, R.drawable.mieayam};

        HasilMakanan = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> temp;

        int nomor_makanan = namaMakanan1.length;

        for(int i=0;i<nomor_makanan;i++){
            temp = new HashMap<String, Object>();
            temp.put("namamakanan", namaMakanan1[i]);
            temp.put("detailmakanan", detailMakanan1[i]);
            temp.put("gambar", gambar1[i]);

            HasilMakanan.add(temp);
        }

        searchResult = new ArrayList<HashMap<String, Object>>(HasilMakanan);

        final CustomAdapter adapter =  new CustomAdapter(this,R.layout.activity_daftar_makanan, searchResult);
        _listMakanan.setAdapter(adapter);

        _listMakanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = searchResult.get(position).get("namamakanan").toString();
                switch (str){
                    case "Olos" :
                        Intent olos = new Intent(MainActivity.this, Olos.class);
                        startActivity(olos);
                        break;

                    case "tahuAci" :
                        Intent tahuaci = new Intent(MainActivity.this, TahuAci.class);
                        startActivity(tahuaci);
                        break;

                    case "tempe" :
                        Intent tempe = new Intent(MainActivity.this, tempe.class);
                        startActivity(tempe);
                        break;

                    case "bakso" :
                        Intent bakso = new Intent(MainActivity.this, bakso.class);
                        startActivity(bakso);
                        break;

                    case "mieayam" :
                        Intent mieayam = new Intent(MainActivity.this, mieAyam.class);
                        startActivity(mieayam);
                        break;

                    default: "Tidak Ada Makanan";
                    break;
                }

            }
        });



        kotakPencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString = kotakPencarian.getText().toString();
                int textLenght = searchString.length();
                searchResult.clear();

                for(int i=0;i<HasilMakanan.size();i++){
                    String hasil_NamaMakanan = HasilMakanan.get(i).get("namamakanan").toString();

                    if(textLenght<=hasil_NamaMakanan.length()){
                        if(searchString.equalsIgnoreCase(hasil_NamaMakanan.substring(0,textLenght)))
                        searchResult.add(HasilMakanan.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {

        public CustomAdapter(Context context, int textViewResourcId,
                             ArrayList<HashMap<String, Object>> Strings){
            super(context, textViewResourcId, Strings);
        }

        private class ViewHolder{
            ImageView gamber;
            TextView namamakanan, detailmakanan;
        }

        ViewHolder viewHolder;


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView ==null){
                convertView = inflater.inflate (R.layout.activity_daftar_makanan, null);
                viewHolder = new ViewHolder();

                viewHolder.gamber = (ImageView) convertView.findViewById(R.id.gambar);
                viewHolder.namamakanan = (TextView) convertView.findViewById(R.id.namaMakanan);
                viewHolder.detailmakanan = (TextView) convertView.findViewById(R.id.detailMakanan);

                convertView.setTag(viewHolder);
            }else
                viewHolder = (ViewHolder)convertView.getTag();

                int gambarId = (Integer) searchResult.get(position).get("gambar");

                viewHolder.gamber.setImageDrawable(getResources().getDrawable(gambarId));
                viewHolder.namamakanan.setText(searchResult.get(position).get("namamakanan").toString());
                viewHolder.detailmakanan.setText(searchResult.get(position).get("detailmakanan").toString());

            return convertView;

        }
    }

}
