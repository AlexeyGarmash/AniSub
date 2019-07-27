package com.example.alex.player_demo_2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.alex.player_demo_2.adapters.ChaptersAdapter;
import com.example.alex.player_demo_2.R;
import com.example.alex.player_demo_2.common.Utils;
import com.example.alex.player_demo_2.models.SerialChapter;
import com.example.alex.player_demo_2.models.SerialInfo;
import com.example.alex.player_demo_2.models.SerialInfoShort;
import com.example.alex.player_demo_2.mvp.presenters.SerialInfoPresenter;
import com.example.alex.player_demo_2.mvp.views.SerialInfoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SerialActivity extends MvpAppCompatActivity implements SerialInfoView {

    @BindView(R.id.imageAnime)
    ImageView mImageViewAnime;

    @BindView(R.id.tvTitle)
    TextView mTextViewTitle;

    @BindView(R.id.tvDesc)
    TextView mTextViewCategory;

    @BindView(R.id.tvSujet)
    TextView mTextViewSujet;

    @BindView(R.id.generalDesc)
    TextView mTextViewGeneral;

    @BindView(R.id.progressBarSerial)
    ProgressBar mProgressBar;


    SerialInfoShort shortInfo;


    @BindView(R.id.rvChapters)
    RecyclerView mRecyclerViewChapters;

    private ChaptersAdapter chaptersAdapter;

    @InjectPresenter
    SerialInfoPresenter mSerialInfoPresenter;

    @ProvidePresenter
    SerialInfoPresenter provideDetailsPresenter() {
        return new SerialInfoPresenter((SerialInfoShort)getIntent().getParcelableExtra(MainActivity.PARCABLE_OBJECT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        shortInfo = (SerialInfoShort) intent.getParcelableExtra(MainActivity.PARCABLE_OBJECT);
        setTitle(shortInfo.getTitle());
        chaptersAdapter = new ChaptersAdapter(new ArrayList<>(), this);
        mRecyclerViewChapters.setAdapter(chaptersAdapter);
        mRecyclerViewChapters.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewChapters.getContext(),
                RecyclerView.VERTICAL);
        mRecyclerViewChapters.addItemDecoration(dividerItemDecoration);
        //mSerialInfoPresenter.getSerialDetails(shortInfo.getLink(), true);
    }

    @Override
    public void addToList(ArrayList<SerialChapter> chapters) {
        chaptersAdapter.getChapters().addAll(chapters);
        chaptersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDetails(SerialInfo serialDetails) {
        serialDetails.setShortInfo(shortInfo);
        Utils.setImageByURL(this, mImageViewAnime, 0, 0, serialDetails.getShortInfo().getImageUrl());
        mTextViewTitle.setText(serialDetails.getShortInfo().getTitle());
        mTextViewCategory.setText(serialDetails.getShortInfo().getCategory());
        //TODO: Utils.getFormattedText
        mTextViewGeneral.setText(Utils.getFormattedString(serialDetails.getGeneralDescription()));
        mTextViewSujet.setText(serialDetails.getDescription());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(SerialActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideError() {

    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onFinishLoading() {

    }

    @Override
    public void showRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshing() {
        mProgressBar.setVisibility(View.GONE);
    }
}
