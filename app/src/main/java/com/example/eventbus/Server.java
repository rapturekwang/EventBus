package com.example.eventbus;

import android.content.SharedPreferences;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by 정광희 on 2016-05-09.
 */
public class Server {
    ArrayList<String> mServerList = new ArrayList<>();
    static Server instance;

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void saveItem(String item) {
        mServerList.add(item);
        requestItem();
    }

    public void requestItem() {
        Update update = new Update();
        update.itemList = mServerList;
        EventBus.getDefault().post(update);
    }
}
