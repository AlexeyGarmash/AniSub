package com.example.alex.player_demo_2.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.alex.player_demo_2.adapters.MainListAdapter;
import com.example.alex.player_demo_2.R;
import com.example.alex.player_demo_2.models.SerialInfoShort;
import com.example.alex.player_demo_2.mvp.presenters.AllSerialsPresenter;
import com.example.alex.player_demo_2.mvp.views.AllSerialsView;
import com.google.android.material.textfield.TextInputLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends MvpAppCompatActivity implements AllSerialsView {

    public static final String PARCABLE_OBJECT = "obj";

    @BindView(R.id.parseRes)
    TextView mTextViewResult;

    @BindView(R.id.input_layout_search)
    TextInputLayout mInputLayoutSearch;

    @BindView(R.id.searchEditText)
    EditText mEditTextSearch;

    @BindView(R.id.btnSend)
    Button mButtonSend;

    @BindView(R.id.btnToolbar)
    Button mButtonSearch;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.rvSerials)
    RecyclerView mRecyclerViewSerials;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.title)
    TextView mToolbarTitle;

    private MainListAdapter mainListAdapter;


    @InjectPresenter
    AllSerialsPresenter mAllSerialsPresenter;

    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setTitle("Тайтлы");
        res = "";
        ButterKnife.bind(this);
        mProgressBar.setZ(99);
        setSupportActionBar(mToolbar);
        setTitle("Тайтлы");
        mRecyclerViewSerials.requestFocus();
        mainListAdapter = new MainListAdapter(new ArrayList<>(), this);

        mRecyclerViewSerials.setAdapter(mainListAdapter);
        mRecyclerViewSerials.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSerials.addOnScrollListener(mScrollListener);


        //mEditTextSearch.addTextChangedListener(new MyTextWatcher(mEditTextSearch));
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextSearch.setVisibility(View.VISIBLE);
            }
        });

    }

    private void searchSerials() {
        /*if(mEditTextSearch.getText().toString().length() > 3){
            res = "";
            String url = "http://fanserials.care/search/";
            String serachParams = null;
            serachParams = mEditTextSearch.getText().toString();




            String newSearchparams = null;
            try {
                newSearchparams = URLEncoder.encode(serachParams, "cp1251");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String searchQuery = String.format("http://fanserials.care/search/?query=%s", newSearchparams);
            new GetHtml().execute(searchQuery,searchQuery);
        }*/

        //new DownloadPageTask("http://fanserials.care", "2", Constants.SHOW_ALL_SERIALS).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void search(String url, String query) {
        //mTextViewResult.setText(url);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", url);
        clipboard.setPrimaryClip(clip);
        new GetHtml().execute(url, query);

    }

    @Override
    public void addToList(ArrayList<SerialInfoShort> serialInfoShort) {
        mainListAdapter.getSerials().addAll(serialInfoShort);
        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
        //mToolbarTitle.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {
        mProgressBar.setVisibility(View.GONE);
        //mToolbarTitle.setVisibility(View.VISIBLE);
    }



    private class GetHtml extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... strings) {
            String url = strings[0];
            String query = strings[1];
            Document doc = null;
            try {
                doc = Jsoup.connect(url)

                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {


            for (Element serial : document.select("div.item-search-serial")) {
                Element serInfo = serial.selectFirst("div.serial-info");
                Element text_ssb = serInfo.selectFirst("div.text_ssb");
                Element itemSearch_header = text_ssb.selectFirst("div.item-search-header");
                Element h2 = itemSearch_header.selectFirst("h2");
                Element title = h2.selectFirst("a[title]");
                String titleStr = title.text();
                res += titleStr + "\n";
                mTextViewResult.setText(res);
            }
            //mTextViewResult.setText(document.baseUri().toString());
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private EditText editText;

        private MyTextWatcher(EditText et) {
            this.editText = et;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {


        }
    }

    /*private class DownloadPageTask extends AsyncTask<String, Void, Document> {

        private String siteUrl;
        private String dataId;
        private int constant;

        public DownloadPageTask(String siteUrl, String dataId, int constant) {
            this.siteUrl = siteUrl;
            this.dataId = dataId;
            this.constant = constant;
        }

        public int getConstant() {
            return constant;
        }

        public void setConstant(int constant) {
            this.constant = constant;
        }

        public String getSiteUrl() {
            return siteUrl;
        }

        public void setSiteUrl(String siteUrl) {
            this.siteUrl = siteUrl;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            try {
                doc = Jsoup.connect(siteUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            switch (constant){
                case Constants.SHOW_ALL_SERIALS:
                    List<SerialInfoShort> serialInfosShort = new ParseAllSerials(document,dataId).getSerialsBlock();
                    showList(serialInfosShort);
            }

        }
    }*/

    private void showList(List<SerialInfoShort> serialInfoShorts) {
        mTextViewResult.setText("");
        String rrr = "none";
        rrr = serialInfoShorts.get(0).toString() + "\n\n";
        mTextViewResult.setText(rrr);


    }

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(mProgressBar.getVisibility() == View.VISIBLE)
                return;
            int visibleItemCount = mRecyclerViewSerials.getLayoutManager().getChildCount();
            int totalItemCount = mRecyclerViewSerials.getLayoutManager().getItemCount();
            int pastVisibleItems = ((LinearLayoutManager)mRecyclerViewSerials.getLayoutManager()).findFirstVisibleItemPosition();
            if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                Toast.makeText(MainActivity.this, "end of list", Toast.LENGTH_SHORT).show();
                mAllSerialsPresenter.getSerialsInBlock(getString(R.string.fanserials_url), mAllSerialsPresenter.getData_id()+1, true);
            }
        }
    };
}


