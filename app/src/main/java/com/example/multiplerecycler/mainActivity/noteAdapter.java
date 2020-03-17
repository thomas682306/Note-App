package com.example.multiplerecycler.mainActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplerecycler.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;


public class noteAdapter extends FirestoreRecyclerAdapter<modelClass_note,noteAdapter.noteViewHolder> {
    Context context;
    onNoteClick monNoteClick;
    TimePickerDialog timePickerDialog;

    public noteAdapter(@NonNull FirestoreRecyclerOptions<modelClass_note> options, Context context,onNoteClick monNoteClick) {
        super(options);
        this.context = context;
        this.monNoteClick=monNoteClick;
    }

    public noteAdapter(@NonNull FirestoreRecyclerOptions<modelClass_note> options) {
        super(options);

    }




    @Override
    protected void onBindViewHolder(@NonNull final noteViewHolder holder, int position, @NonNull modelClass_note model) {
        holder.headingtv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha));

        holder.listCard.setAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));

        holder.datetv.setText(model.getDate());
        holder.headingtv.setText(model.getHeading());
        holder.daytv.setText(model.getDay());
        holder.descriptiontv.setText(model.getDescription());
        holder.bg.setBackgroundColor(Color.parseColor(model.getHexCode()));
    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.liste_item_gridview,parent,false);
        return new noteViewHolder(view,monNoteClick);
    }


    class noteViewHolder extends RecyclerView.ViewHolder{

        TextView headingtv,descriptiontv,datetv,daytv;

        ImageView bg,bellicon,bin;
        CardView listCard;
        onNoteClick onNoteClick;
        Calendar c;
        int hour;
        int minute;


        public noteViewHolder(@NonNull View itemView, final onNoteClick onNoteClick) {
            super(itemView);
            bellicon=itemView.findViewById(R.id.bellIcon);

            listCard=itemView.findViewById(R.id.cardViewListItem);
            headingtv=itemView.findViewById(R.id.heading);
            descriptiontv=itemView.findViewById(R.id.textView3);
            daytv=itemView.findViewById(R.id.day);
            bg=itemView.findViewById(R.id.imageview);
            datetv=itemView.findViewById(R.id.textView4);
            this.onNoteClick=onNoteClick;
            Calendar c = Calendar.getInstance();
             hour= c.get(Calendar.HOUR);
             minute= c.get(Calendar.MINUTE);





            bellicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoteClick.alarmSet(bellicon);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoteClick.onNoteitemSelected(getSnapshots().getSnapshot(getAdapterPosition()),getAdapterPosition());
                }
            });
        }


    }

    public interface onNoteClick{
        void onNoteitemSelected(DocumentSnapshot documentSnapshot,int position);

        void delete(int position);
        void alarmSet(ImageView imageView);

    }



}
