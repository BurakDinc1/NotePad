package com.dnc.notepad.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dnc.notepad.DataBases.DataBaseHelper;
import com.dnc.notepad.DataClass.Not;
import com.dnc.notepad.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NotOlusturActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton not_kayit, not_arka_plan_rengi, not_yazi_rengi, not_kalin_yazi, not_egik_yazi;
    private EditText not_olustur_edittext, not_olustur_baslik_edittext;
    private int arkaPlanRengi, yaziRengi;
    private Bundle bundle;
    private String bundle_kimlik;

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

        not_kayit = findViewById(R.id.not_kayit_button);
        not_arka_plan_rengi = findViewById(R.id.not_arka_plan_rengi);
        not_yazi_rengi = findViewById(R.id.not_yazi_rengi);
        not_kalin_yazi = findViewById(R.id.not_kalin_yazi);
        not_egik_yazi = findViewById(R.id.not_egik_yazi);
        toolbar = findViewById(R.id.not_olustur_appbar);
        not_olustur_edittext = findViewById(R.id.not_olustur_edittext);
        not_olustur_baslik_edittext = findViewById(R.id.not_olustur_baslik_edittext);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Not Oluştur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arkaPlanRengi = ContextCompat.getColor(not_olustur_edittext.getContext(), R.color.AppBarRengi);
        yaziRengi = ContextCompat.getColor(not_olustur_edittext.getContext(), R.color.yaziRengi);

        bundle = getIntent().getExtras();
        bundle_kimlik = Objects.requireNonNull(bundle).getString("isedit");
    }

    private void receivedData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            not_olustur_edittext.setText(sharedText);
        }
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, arkaPlanRengi, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                arkaPlanRengi = color;
                //not_olustur_edittext.setBackgroundColor(arkaPlanRengi);
                Drawable a = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.edittext_shape);
                Objects.requireNonNull(a).setTint(color);
                not_olustur_baslik_edittext.setBackground(a);
                not_olustur_edittext.setBackground(a);
            }
        });
        colorPicker.show();
    }

    private void openColorPickerWrite() {
        AmbilWarnaDialog colorPickerWrite = new AmbilWarnaDialog(this, yaziRengi, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                yaziRengi = color;
                not_olustur_edittext.setTextColor(color);
                not_olustur_baslik_edittext.setTextColor(color);
            }
        });
        colorPickerWrite.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_olustur);

        init();

        receivedData();

        if (!bundle_kimlik.equals("hayir")) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
            Not not = dataBaseHelper.getNote(bundle_kimlik);
            dataBaseHelper.close();
            not_olustur_edittext.setText(not.getNot_icerik());
            not_olustur_baslik_edittext.setText(not.getNot_baslik());
            not_olustur_edittext.setTextColor(Integer.parseInt(not.getYazirengi()));
            not_olustur_baslik_edittext.setTextColor(Integer.parseInt(not.getYazirengi()));
            Drawable a = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.edittext_shape);
            Objects.requireNonNull(a).setTint(Integer.parseInt(not.getArkaplanrenk()));
            not_olustur_baslik_edittext.setBackground(a);
            not_olustur_edittext.setBackground(a);
            arkaPlanRengi=Integer.parseInt(not.getArkaplanrenk());
            yaziRengi=Integer.parseInt(not.getYazirengi());
            if (not.getBold().equals("false") && not.getItalic().equals("false")) {
                not_olustur_edittext.setTypeface(Typeface.DEFAULT);
            } else if (not.getBold().equals("true") && not.getItalic().equals("false")) {
                not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            } else if (not.getBold().equals("false") && not.getItalic().equals("true")) {
                not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            } else {
                not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
            }
        }

        not_arka_plan_rengi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        not_yazi_rengi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerWrite();
            }
        });

        not_kalin_yazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (not_olustur_edittext.getTypeface().isBold()) {
                    if (not_olustur_edittext.getTypeface().isItalic()) {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
                    } else {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT);
                    }
                } else {
                    if (not_olustur_edittext.getTypeface().isItalic()) {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                    } else {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                    }
                }
            }
        });

        not_egik_yazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (not_olustur_edittext.getTypeface().isItalic()) {
                    if (not_olustur_edittext.getTypeface().isBold()) {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                    } else {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT);
                    }
                } else {
                    if (not_olustur_edittext.getTypeface().isBold()) {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                    } else {
                        not_olustur_edittext.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
                    }
                }
            }
        });

        not_kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bundle_kimlik.equals("hayir")) {

                    if (not_olustur_edittext.getText().toString().isEmpty() || not_olustur_baslik_edittext.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Lütfen alanları doldurunuz !", Toast.LENGTH_SHORT).show();
                    } else {

                        String colorid = String.valueOf(arkaPlanRengi);

                        String coloridyazi = String.valueOf(not_olustur_baslik_edittext.getCurrentTextColor());

                        String boldmu = null;
                        String italicmi = null;

                        if (not_olustur_edittext.getTypeface().isBold()) {
                            boldmu = "true";
                        } else {
                            boldmu = "false";
                        }

                        if (not_olustur_edittext.getTypeface().isItalic()) {
                            italicmi = "true";
                        } else {
                            italicmi = "false";
                        }

                        Not ekle = new Not(
                                not_olustur_baslik_edittext.getText().toString(),
                                not_olustur_edittext.getText().toString(),
                                colorid,
                                coloridyazi,
                                boldmu,
                                italicmi);

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                        dataBaseHelper.addNote(ekle);
                        dataBaseHelper.close();

                        onBackPressed();

                    }

                } else {

                    String duzenlenecek_not_id = bundle.getString("isedit");

                    if (not_olustur_edittext.getText().toString().isEmpty() || not_olustur_baslik_edittext.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Lütfen alanları doldurunuz !", Toast.LENGTH_SHORT).show();
                    } else {

                        String colorid = String.valueOf(arkaPlanRengi);

                        String coloridyazi = String.valueOf(not_olustur_baslik_edittext.getCurrentTextColor());

                        String boldmu = null;
                        String italicmi = null;

                        if (not_olustur_edittext.getTypeface().isBold()) {
                            boldmu = "true";
                        } else {
                            boldmu = "false";
                        }

                        if (not_olustur_edittext.getTypeface().isItalic()) {
                            italicmi = "true";
                        } else {
                            italicmi = "false";
                        }

                        Not duzenle = new Not(
                                not_olustur_baslik_edittext.getText().toString(),
                                not_olustur_edittext.getText().toString(),
                                colorid,
                                coloridyazi,
                                boldmu,
                                italicmi);

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                        dataBaseHelper.updateNote(duzenlenecek_not_id, duzenle);
                        dataBaseHelper.close();

                        onBackPressed();

                    }

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}