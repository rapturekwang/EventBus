package com.example.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    EditText mEditText;
    Server mServer;

    private EventBus mEventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_main);
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mEditText = (EditText)findViewById(R.id.edit_main);
        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServer.saveItem(mEditText.getText().toString());
                mEditText.setText("");
            }
        });

        mServer = Server.getInstance();
        mEventBus.register(this);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<String> mList;

        public RecyclerAdapter() {
            mList = new ArrayList<>();
        }

        public void swapData(List<String> data) {
            mList.clear();
            mList.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ViewHolder itemHolder =  (ViewHolder)holder;
            itemHolder.textView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View view) {
                super(view);

                textView = (TextView) view.findViewById(R.id.text_item);
            }
        }
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
