package com.example.shim.stunitas.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.shim.stunitas.Model.Document;
import com.example.shim.stunitas.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Document> documentList;
    private Context context;

    public ImageAdapter(Context context) {
        this.documentList = new ArrayList<Document>();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_list_item, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ImageViewHolder) viewHolder).bind(documentList.get(i));
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void addItem(List<Document> documents){
        int prevSize = getItemCount();
        for(Document doc : documents){
            documentList.add(doc);
        }

        //notifyDataSetChanged()사용시 스크롤이 최상단으로 이동하기 해서 아래 함수로 사용.
        notifyItemRangeInserted(prevSize, getItemCount());
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.imageList_iv_image);
        }
        public void bind(Document document){
            Glide
                    .with(context)
                    .load(document.getImageUrl())
                    .into(iv_image);
        }
    }
}
