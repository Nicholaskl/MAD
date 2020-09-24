package com.nicholas.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyFragmentA extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui,
                             Bundle bundle) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_a, ui, false);

       //Obtain the RecyclerView UI element
        RecyclerView rv = view.findViewById(R.id.my_list);



        List<String> data = new ArrayList<>();
        data.add("help");
        data.add("cool");


        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        //works
       //Button myButton = (Button) view.findViewById(R.id.my_button);



       return view;
    }

    private class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_mydata, parent, false));
            textView = (TextView) itemView.findViewById(R.id.data_list);
        }

        public void bind(String str)
        {
            textView.setText(str);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        private List<String> data;
        public MyAdapter(List<String> data)
        {
            this.data = data;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public MyDataVHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            vh.bind(data.get(index));
        }
    }
}