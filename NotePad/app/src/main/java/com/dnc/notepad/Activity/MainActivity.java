package com.dnc.notepad.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.dnc.notepad.Adapter.NotAdapter;
import com.dnc.notepad.DataBases.DataBaseHelper;
import com.dnc.notepad.DataClass.Not;
import com.dnc.notepad.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private RecyclerView notlarrecycview;
    private ImageButton noteklebutton;
    private static NotAdapter notAdapter;
    private static List<Not> notlar = new ArrayList<>();
    private StaggeredGridLayoutManager gridLayoutManager;
    public static Context context;
    public static PullRefreshLayout pullRefreshLayout;

    private boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isAvailable()
                && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void init() {
        appbar = findViewById(R.id.mainappbar);
        notlarrecycview = findViewById(R.id.notlarrecyclerview);
        noteklebutton = findViewById(R.id.noteklebutton);
        pullRefreshLayout = findViewById(R.id.yenile);
        context = this;

        setSupportActionBar(appbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notAdapter = new NotAdapter(notlar, context, this);
        notlarrecycview.setHasFixedSize(true);

        if (notlarrecycview != null) {
            notlarrecycview.setAdapter(notAdapter);
            notlarrecycview.setLayoutManager(gridLayoutManager);
        }

        notlariCek();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notlariCek();
    }

    public static void notlariCek() {
        notlar.clear();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        notlar.addAll(dataBaseHelper.getNoteList());
        notAdapter.notifyDataSetChanged();
        dataBaseHelper.close();
        pullRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        noteklebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotOlusturActivity.class);
                intent.putExtra("isedit", "hayir");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notlariCek();
                Toast.makeText(getApplicationContext(), "Notlar yenilendi !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}