package edu.zhanglrose_hulman.photobucket;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;


import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lukezhang on 7/20/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private List<Photo> mPhotos;
    public PhotoListFragment.Callback mCallback;
    public PhotoListFragment mFragment;
    private DatabaseReference mPhotosRef;

    public PhotoAdapter(PhotoListFragment.Callback callback, Context context, PhotoListFragment fragment) {
        mCallback = callback;
        mPhotos = new ArrayList<>();
        mFragment = fragment;

        mPhotosRef = FirebaseDatabase.getInstance().getReference().child("photos");
        mPhotosRef.addChildEventListener(new PhotoChildEventListener());
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Photo photo = mPhotos.get(position);
        holder.mCaptionTextView.setText(photo.getCaption());
        holder.mUrlTextView.setText(photo.getUrl());

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public void remove(Photo photo) {
        mPhotosRef.child(photo.getKey()).removeValue();
    }


    public void add(Photo photo) {
        mPhotosRef.push().setValue(photo);
        Log.d("add", "add zero");
    }

    public void update(Photo photo, String caption, String url) {
        photo.setCaption(caption);
        photo.setUrl(url);
        mPhotosRef.child(photo.getKey()).setValue(photo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mCaptionTextView;
        private TextView mUrlTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCaptionTextView = (TextView) itemView.findViewById(R.id.caption_text);
            mUrlTextView = (TextView) itemView.findViewById(R.id.url_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Photo wp = mPhotos.get(getAdapterPosition());
                    mCallback.onDisplay(wp);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Photo wp = mPhotos.get(getAdapterPosition());
                    mFragment.showAddEditDeleteDialog(wp);
                    return true;
                }
            });

        }
    }


    class PhotoChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Photo photo = dataSnapshot.getValue(Photo.class);
            photo.setKey(dataSnapshot.getKey());
            mPhotos.add(0, photo);
            notifyDataSetChanged();
            Log.d("add", "add one");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Photo newPhoto = dataSnapshot.getValue(Photo.class);
            for(Photo wp : mPhotos){
                if(wp.getKey().equals(key)){
                    wp.setValues(newPhoto);
                    break;
                }
            }

            notifyDataSetChanged();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for (Photo wp : mPhotos){
                if (key.equals(wp.getKey())){
                    mPhotos.remove(wp);
                    notifyDataSetChanged();
                    break;
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //empty
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(Constants.TAG,"DataBase error: " + databaseError);
        }
    }

}
