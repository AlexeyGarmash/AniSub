package com.example.alex.player_demo_2.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alex.player_demo_2.R;
import com.example.alex.player_demo_2.activities.MainActivity;
import com.example.alex.player_demo_2.activities.SerialActivity;
import com.example.alex.player_demo_2.common.Utils;
import com.example.alex.player_demo_2.models.SerialInfoShort;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.CLIPBOARD_SERVICE;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainViewHolder> {


    public static class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView titleTextView;

        @BindView(R.id.tvDesc)
        TextView categoryTextView;

        @BindView(R.id.imageAnime)
        ImageView imgAnime;

        @BindView(R.id.tvStatus)
        TextView statusRelise;

        @BindView(R.id.cardSerial)
        CardView cardView;

        /**
         * Конструктор, выполняющий поиск View по ID в родительском View.
         *
         * @param itemView родительский элемент.
         */
        MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private List<SerialInfoShort> serials;
    private Context mContext;


    public MainListAdapter(List<SerialInfoShort> serials, Context context) {
        this.serials = serials;
        mContext = context;
        setHasStableIds(true);
        clipboardManager = (ClipboardManager)mContext.getSystemService(CLIPBOARD_SERVICE);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, viewGroup, false);
        MainViewHolder vHolder = new MainViewHolder(view);
        return vHolder;
    }

    ClipboardManager clipboardManager;

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        mainViewHolder.titleTextView.setText(serials.get(i).getTitle());
        mainViewHolder.categoryTextView.setText(serials.get(i).getCategory());
        mainViewHolder.statusRelise.setText(serials.get(i).getStatus());
        Utils.setImageByURL(mContext, mainViewHolder.imgAnime, 0,0, serials.get(i).getImageUrl());
        mainViewHolder.cardView.setOnClickListener(click->{
            //Toast.makeText(mContext, serials.get(i).getImageUrl(), Toast.LENGTH_SHORT).show();
            if(serials.get(i).getStatus().contains("Анонс")){
                Toast.makeText(mContext, "До скорого залива!", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(mContext, SerialActivity.class);
                intent.putExtra(MainActivity.PARCABLE_OBJECT, serials.get(i));
                mContext.startActivity(intent);
            }
        });
        mainViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData = ClipData.newPlainText("text",serials.get(i).getLink());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(mContext, "Copied!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return serials.size();
    }

    /**
     * Вызывается RecyclerView, когда он начинает наблюдать за этим адаптером.
     *
     * @param recyclerView экземпляр RecyclerView, который начал наблюдать за этим адаптером
     */
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

    public List<SerialInfoShort> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialInfoShort> serials) {
        this.serials = serials;
    }
}
