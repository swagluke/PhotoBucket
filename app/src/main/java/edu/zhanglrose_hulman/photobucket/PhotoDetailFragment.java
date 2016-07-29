package edu.zhanglrose_hulman.photobucket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lukezhang on 7/20/16.
 */
public class PhotoDetailFragment extends Fragment {


    private static final String ARG_PIC = "photos";

    private Photo mPhoto;


    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    public static PhotoDetailFragment newInstance(Photo photo) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PIC, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable(ARG_PIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        TextView textView = (TextView)view.findViewById(R.id.fragment_name);
        ImageView imageView = (ImageView)view.findViewById(R.id.fragment_image);
        new GetImageTask(imageView).execute(mPhoto.getUrl());
        textView.setText(mPhoto.getCaption());

        return view;
    }
}
