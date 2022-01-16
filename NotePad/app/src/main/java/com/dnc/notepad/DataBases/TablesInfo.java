package com.dnc.notepad.DataBases;

import android.provider.BaseColumns;

public class TablesInfo {

    public static final class NoteEntry implements BaseColumns {

        public static final String TABLO_ADI = "notlar";
        public static final String COLUMN_ID = "not_id";
        public static final String COLUMN_BASLIK = "not_baslik";
        public static final String COLUMN_ICERIK = "not_icerik";
        public static final String COLUMN_ARKAPLANRENK = "not_arkaplanrenk";
        public static final String COLUMN_YAZIRENK = "not_yazirenk";
        public static final String COLUMN_BOLD = "not_bold";
        public static final String COLUMN_ITALIC = "not_italic";
        public static final String COLUMN_TARIH = "not_tarih";

    }

}
