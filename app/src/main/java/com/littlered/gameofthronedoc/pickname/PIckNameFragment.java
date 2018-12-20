package com.littlered.gameofthronedoc.pickname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.littlered.gameofthronedoc.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * author : littleredDLZ
 * date : 2018-12-20 15:03
 */
public class PIckNameFragment extends Fragment implements PickNameContract.view {
    @BindView(R.id.progress_name)
    FrameLayout mProgressBar;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_name, container, false);
        ButterKnife.bind(this, view);
        indexableLayout.setLayoutManager(new GridLayoutManager(getContext(), 1));
        return view;
    }
}
