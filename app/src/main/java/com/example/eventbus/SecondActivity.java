package com.example.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class SecondActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    Server mServer;

    private EventBus mEventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_second);
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mServer = Server.getInstance();
        mEventBus.register(this);
        mServer.requestItem();
    }

    @Override
    protected void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(Update update) {
        mAdapter.swapData(update.itemList);
    }
}
