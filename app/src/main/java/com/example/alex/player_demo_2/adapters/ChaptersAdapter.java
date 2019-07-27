package com.example.alex.player_demo_2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alex.player_demo_2.R;
import com.example.alex.player_demo_2.activities.PlayerActivity;
import com.example.alex.player_demo_2.common.Utils;
import com.example.alex.player_demo_2.models.SerialChapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ChapterViewHolder> {

    private List<SerialChapter> chapters;
    private Context mContext;


    public ChaptersAdapter(List<SerialChapter> chapters, Context context) {
        this.chapters = chapters;
        mContext = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list_item, parent, false);
        ChapterViewHolder vHolder = new ChapterViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.tvChapterNum.setText(chapters.get(position).getNumberChpater());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = chapters.get(position).getMovieLink();
                String conn = link.substring(0, link.indexOf("://"));
                securConn = conn;
                //link = link.replace("https://", "https://s1.");
                Toast.makeText(mContext, link, Toast.LENGTH_SHORT).show();
                Log.i("FFFFFFFFFFFFFFFFFFFFFFF", link);
                new GetHtmlFile(false).execute(link);


                /*try {
                    org.jsoup.nodes.Document doc = Jsoup.connect(link).get();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        });
    }

    private String securConn = "";

    class GetHtmlFile extends AsyncTask<String, Void, String> {

        public boolean isSearchVideoMp4 = false;

        public GetHtmlFile(boolean isMp4Search){
            isSearchVideoMp4 = isMp4Search;
        }

        protected String doInBackground(String... urls) {

            String url = urls[0];
            Document doc = null;
            String result = "";
            try {
                doc = Jsoup.connect(url).get();
                result = doc.outerHtml();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }


        protected void onPostExecute(String outHtml) {
            outHTML = outHtml;
            if(outHtml != null){
                if(isSearchVideoMp4 == false){
                    String file = Utils.getContainsFile(outHtml, securConn);

                    Toast.makeText(mContext, "FILE "+ file, Toast.LENGTH_SHORT).show();
                    new GetHtmlFile(true).execute(file);
                }
                else{
                    String vidStream = Utils.getVideoStreamFile(outHtml, securConn);
                    Toast.makeText(mContext, "VIDEO "+ vidStream, Toast.LENGTH_SHORT).show();
                    Intent inte = new Intent(mContext, PlayerActivity.class);
                    inte.putExtra("LINK", vidStream);
                    mContext.startActivity(inte);
                }

            }
            else
            {
                Toast.makeText(mContext, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String outHTML = "";
    @Override
    public int getItemCount() {
        return chapters.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<SerialChapter> getChapters() {
        return chapters;
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvChapterNum)
        TextView tvChapterNum;

        @BindView(R.id.itemChapter)
        RelativeLayout layout;

        ChapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
