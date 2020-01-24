package com.snowman.sdparser_jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    public Elements content;
    public ArrayList <String> titleList = new ArrayList<>();
    private ArrayAdapter <String> adapter;
    private ListView listView;

    private ArrayAdapter adapterCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        new NewThread().execute();
        //adapter = new ArrayAdapter<>(this,R.layout.list_item, R.id.pro_item);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, titleList);
/////////////////////////

        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };
        adapterCats = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNames);
    }

    public class NewThread extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            Document document;
            try {
                //document = Jsoup.connect("https://www.volzsky.ru/").get();
                document = Jsoup.connect("https://coinmarketcap.com/").get();

              //Сайт службы поддержки Тулэнерго
               // document = Jsoup.connect("https://esd.mrsk-1.ru/UserRequests.aspx").get();

                //Контент для CoinmarketCap
                content = document.select(".cmc-link");


                //Контент для волжский
                //content = document.select(".btc_p");

                //Контент по которому цеплять содержимое с сайта Тулэнерго
                //content = document.select(".dxgvDataRow_Glass");
                titleList.clear();
                for (Element contents: content){
                    titleList.add(contents.text());
                    Log.d("Add to array: ", contents.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            listView.setAdapter(adapter);
         //Тестовый адаптер для массива с котиками
            //  listView.setAdapter(adapterCats);
            Log.d("Подключение адаптера :", listView.toString() + " к " + adapter.toString());

        }
    }

   }
