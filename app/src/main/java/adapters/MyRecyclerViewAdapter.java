package adapters;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalkulacka.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<String> mData;
    private final Context mcontext;
    private final ArrayList<Character> resultList;
    private final ArrayList<Character> resultList2;
    private String listString, listString2;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mData = data;
        this.mcontext = context;
        resultList = new ArrayList<>();
        resultList2 = new ArrayList<>();
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

    private String splitRow(String result) {
        int equalsPosition = result.indexOf("=");
        StringBuffer stringBuffer = new StringBuffer(result);
        for (int i = 0; i < equalsPosition + 1; i++) {
            resultList2.add(stringBuffer.charAt(i));
            listString2 = TextUtils.join("", resultList2);
        }
        return listString2;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rowSample.setText(splitRow(mData.get(position)));
        holder.resultSample.setText(splitResult(mData.get(position)));
        System.out.println(mData.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rowSample, resultSample;

        public ViewHolder(View itemView) {
            super(itemView);
            resultSample = itemView.findViewById(R.id.resultSample);
            rowSample = itemView.findViewById(R.id.rowSample);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

