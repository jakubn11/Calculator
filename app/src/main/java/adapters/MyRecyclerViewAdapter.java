package adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalkulacka.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<String> mData;
    private final Context mcontext;
    private final ArrayList<Character> resultList;
    private final ArrayList<Character> resultList2;
    private String listString, listString2;
    private final OnResultListener onResultListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<String> data, OnResultListener onResultListener) {
        this.mData = data;
        this.mcontext = context;
        resultList = new ArrayList<>();
        resultList2 = new ArrayList<>();
        this.onResultListener = onResultListener;
    }

    private String splitResult(String result) {
        int equalsPosition = result.indexOf("=");
        StringBuffer stringBuffer = new StringBuffer(result);
        for (int i = equalsPosition + 1; i < result.length(); i++) {
            resultList.add(stringBuffer.charAt(i));
            listString = TextUtils.join("", resultList);
        }
        return listString;
    }

    private String splitRow(String result2) {
        int equalsPosition2 = result2.indexOf("=");
        StringBuffer stringBuffer2 = new StringBuffer(result2);
        for (int i = 0; i < equalsPosition2 + 1; i++) {
            resultList2.add(stringBuffer2.charAt(i));
            listString2 = TextUtils.join("", resultList2);
        }
        return listString2;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false), onResultListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String setText = mData.get(position);
        holder.rowSample.setText(splitRow(setText));
        holder.resultSample.setText(splitResult(setText));
        listString = "";
        listString2 = "";
        resultList.clear();
        resultList2.clear();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private Activity mActivity;
        private TextView rowSample, resultSample;
        private OnResultListener onResultListener;

        public ViewHolder(View itemView, OnResultListener onResultListener) {
            super(itemView);

            resultSample = itemView.findViewById(R.id.resultSample);
            rowSample = itemView.findViewById(R.id.rowSample);
            this.onResultListener = onResultListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onResultListener.onResultClick(getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onResultListener.onLongClick(getAbsoluteAdapterPosition());
            return true;
        }
    }

    public interface OnResultListener {
        void onResultClick(int position);
        void onLongClick(int position);
    }
}

