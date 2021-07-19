package com.parthpaija.eventaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mcontext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mcontext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        final Upload uploadcurrent = mUploads.get(position);
        holder.textName.setText(uploadcurrent.getName());
        Glide.with(mcontext)
                .load(uploadcurrent.getImageUrl())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(holder.imageView1);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = uploadcurrent.getName();
                String img_url = uploadcurrent.getImageUrl();
                Intent intent = new Intent(v.getContext(), Event_Details.class);
                intent.putExtra("event_name", title);
                intent.putExtra("imag_url", img_url);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textName;
        public ImageView imageView1;
        public LinearLayout linearLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.txt_name);
            imageView1 = (ImageView) itemView.findViewById(R.id.img_upload);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);


        }
    }

    /*private ImageAdapter.clicklistener mclicklistener;

    public interface clicklistener {
        void onItemclick(View view, int postion);
    }

    public void setOnclickListener(ImageAdapter.clicklistener clicklistener) {
        mclicklistener = clicklistener;
    }
    */

}
