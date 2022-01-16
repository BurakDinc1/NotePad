package com.dnc.notepad.DataClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Not {

    private String not_baslik, not_icerik, arkaplanrenk, yazirengi, not_tarih, bold, italic, id;

    public Not(String not_baslik, String not_icerik, String arkaplanrenk, String yazirengi, String bold, String italic) {
        long tsLong = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm | MM.dd.yyyy ");
        Date resultdate = new Date(tsLong);
        System.out.println(sdf.format(resultdate));

        this.not_baslik = not_baslik;
        this.not_icerik = not_icerik;
        this.arkaplanrenk = arkaplanrenk;
        this.yazirengi = yazirengi;
        this.not_tarih = sdf.format(resultdate);
        this.id = UUID.randomUUID().toString();
        this.bold = bold;
        this.italic = italic;
    }

    public Not(String id, String not_baslik, String not_icerik, String arkaplanrenk, String yazirengi, String bold, String italic, String not_tarih){
        this.id=id;
        this.not_baslik=not_baslik;
        this.not_icerik=not_icerik;
        this.arkaplanrenk=arkaplanrenk;
        this.yazirengi=yazirengi;
        this.bold=bold;
        this.italic=italic;
        this.not_tarih=not_tarih;
    }

    public String getNot_baslik() {
        return not_baslik;
    }

    public void setNot_baslik(String not_baslik) {
        this.not_baslik = not_baslik;
    }

    public String getNot_icerik() {
        return not_icerik;
    }

    public void setNot_icerik(String not_icerik) {
        this.not_icerik = not_icerik;
    }

    public String getArkaplanrenk() {
        return arkaplanrenk;
    }

    public void setArkaplanrenk(String arkaplanrenk) {
        this.arkaplanrenk = arkaplanrenk;
    }

    public String getYazirengi() {
        return yazirengi;
    }

    public void setYazirengi(String yazirengi) {
        this.yazirengi = yazirengi;
    }

    public String getNot_tarih() {
        return not_tarih;
    }

    public void setNot_tarih(String not_tarih) {
        this.not_tarih = not_tarih;
    }

    public String getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = bold;
    }

    public String getItalic() {
        return italic;
    }

    public void setItalic(String italic) {
        this.italic = italic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
