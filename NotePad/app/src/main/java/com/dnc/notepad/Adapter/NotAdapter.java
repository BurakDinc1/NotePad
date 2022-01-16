package com.dnc.notepad.Adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dnc.notepad.Activity.MainActivity;
import com.dnc.notepad.Activity.NotOlusturActivity;
import com.dnc.notepad.DataBases.DataBaseHelper;
import com.dnc.notepad.DataClass.Not;
import com.dnc.notepad.R;

import java.util.List;

public class NotAdapter extends RecyclerView.Adapter<NotAdapter.NotlarViewHolder> {

    private List<Not> notlar;
    private Context tiklama_icin;
    private Activity activity;

    public NotAdapter(List<Not> notlar, Context tiklama_icin, Activity activity) {
        this.notlar = notlar;
        this.tiklama_icin = tiklama_icin;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotlarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_not_layout, parent, false);
        return new NotlarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotlarViewHolder holder, final int position) {
        final Not a = notlar.get(position);

        holder.not_baslik.setText(a.getNot_baslik());
        holder.not_icerik.setText(a.getNot_icerik());
        holder.not_tarih.setText(a.getNot_tarih());

        holder.not_tiklama_icin.setCardBackgroundColor(Integer.parseInt(a.getArkaplanrenk()));
        holder.not_baslik.setTextColor(Integer.parseInt(a.getYazirengi()));
        holder.not_icerik.setTextColor(Integer.parseInt(a.getYazirengi()));

        if (a.getBold().equals("false") && a.getItalic().equals("false")) {
            holder.not_icerik.setTypeface(Typeface.DEFAULT);
        } else if (a.getBold().equals("true") && a.getItalic().equals("false")) {
            holder.not_icerik.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        } else if (a.getBold().equals("false") && a.getItalic().equals("true")) {
            holder.not_icerik.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        } else {
            holder.not_icerik.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        }

        holder.not_duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NotOlusturActivity.class);
                intent.putExtra("isedit",a.getId());
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        holder.not_paylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "·" + a.getNot_baslik() + "·" + "\n" + a.getNot_icerik());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Notu Gönder");
                activity.startActivity(shareIntent);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        holder.not_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!a.getId().equals("")) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(activity.getApplicationContext());
                    dataBaseHelper.deleteNote(a.getId());
                    dataBaseHelper.close();

                    Toast.makeText(activity.getApplicationContext(), "Not silindi !", Toast.LENGTH_LONG).show();

                    MainActivity.notlariCek();

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Bir hata oluştu !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notlar.size();
    }

    static class NotlarViewHolder extends RecyclerView.ViewHolder {
        private TextView not_baslik, not_icerik, not_tarih;
        private ImageButton not_sil, not_duzenle, not_paylas;
        private CardView not_tiklama_icin;

        private NotlarViewHolder(@NonNull View itemView) {
            super(itemView);
            not_baslik = itemView.findViewById(R.id.cardview_not_baslik);
            not_icerik = itemView.findViewById(R.id.cardview_not_icerik);
            not_tarih = itemView.findViewById(R.id.cardview_not_tarih);
            not_sil = itemView.findViewById(R.id.cardview_not_sil);
            not_duzenle = itemView.findViewById(R.id.cardview_not_duzenle);
            not_tiklama_icin = itemView.findViewById(R.id.arkadaslarcardview);
            not_paylas = itemView.findViewById(R.id.cardview_not_paylas);
        }
    }

}
