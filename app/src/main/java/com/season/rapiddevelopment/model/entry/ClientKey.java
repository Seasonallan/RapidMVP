package com.season.rapiddevelopment.model.entry;

import android.text.TextUtils;

import com.season.rapiddevelopment.model.FilePrefrences;

import java.io.Serializable;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:17
 */
public class ClientKey implements Serializable {


    private static final long serialVersionUID = 1513618789946990246L;

    public String client_id = "102";

    public String key = "4084e4t0da7xgdbe";

    @Override
    public String toString() {
        return "client_id=" + client_id + "  key=" + key;
    }

    public boolean isSign() {
        return !TextUtils.isEmpty(client_id) && !client_id.equals("102");
    }

    private static ClientKey sKeyItemData;

    public static ClientKey getClientKey() {
        if (sKeyItemData == null) {
            sKeyItemData = new ClientKey();
        }
        return sKeyItemData;
    }

    public static void resetKeyDate() {
        sKeyItemData = new ClientKey();
    }

    public static void saveKeyData(ClientKey key) {
        FilePrefrences.saveObject("keyData", key);
        sKeyItemData = key;
    }

    public static void initKeyData() {
        sKeyItemData = new ClientKey();
        Object key = FilePrefrences.getObject("keyData");
        if (key != null && key instanceof ClientKey) {
            sKeyItemData = (ClientKey) key;
            if (!sKeyItemData.isSign()) {
                sKeyItemData.client_id = "102";
                sKeyItemData.key = "4084e4t0da7xgdbe";
            }
        }
    }
}
