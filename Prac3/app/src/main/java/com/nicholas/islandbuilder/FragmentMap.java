package com.nicholas.islandbuilder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMap extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.mapRecyclerView);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter adapter = new MyAdapter(MapData.get());
        rv.setAdapter(adapter);

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //RecyclerView recycView = (RecyclerView) view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(
                getActivity(),
                MapData.HEIGHT,
                GridLayoutManager.HORIZONTAL,
                false
        ));

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        private MapData data;
        public MyAdapter(MapData data)
        {
            this.data = data;
        }

        @Override
        public int getItemCount() { return MapData.HEIGHT * MapData.WIDTH; }

        @Override
        public MyDataVHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            vh.bind(data.get(index % MapData.HEIGHT, index / MapData.WIDTH));
        }
    }

    private class MyDataVHolder extends RecyclerView.ViewHolder
    {

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));
        }

        public void bind(MapElement data)
        {

        }
    }
}