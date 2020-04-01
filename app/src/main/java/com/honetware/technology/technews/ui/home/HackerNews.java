package com.honetware.technology.technews.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.honetware.technology.technews.R;

import utils.Constants;
import utils.Utils;

public class HackerNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Context context;
    RecyclerView recyclerView;
    TextView empty_view;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity().getBaseContext();
        recyclerView = root.findViewById(R.id.recycler_view);
        empty_view = root.findViewById(R.id.empty_view);
        swipeRefreshLayout = root.findViewById(R.id.swipe_container);

        Utils.general(context,recyclerView,empty_view,context.getString(R.string.menu_hacker_news), Constants.Companion.getPageSize(),swipeRefreshLayout);

        return root;
    }

    @Override
    public void onRefresh() {
        Utils.general(context,recyclerView,empty_view,context.getString(R.string.menu_hacker_news), Constants.Companion.getPageSize(),swipeRefreshLayout);
    }
}
