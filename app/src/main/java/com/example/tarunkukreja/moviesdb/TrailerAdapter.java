package com.example.tarunkukreja.moviesdb;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

///**
// * Created by tarunkukreja on 26/03/17.
// */
//
public class TrailerAdapter extends ArrayAdapter {

    private static final String LOG_Tag = TrailerAdapter.class.getSimpleName() ;
    LayoutInflater inflater ;
    int resource ;
    Context context ;
    private List<TrailerCLass> trailerList ;
    public TrailerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TrailerCLass> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;
        this.resource = resource ;
        this.context = context ;
        trailerList = objects ;

       // inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHoder viewHoder = null;

        if(convertView == null){
            Log.d(LOG_Tag, "Convert view is null") ;
            viewHoder = new ViewHoder() ;
            convertView = inflater.inflate(resource, null) ;
            viewHoder.trailer_text = (TextView)convertView.findViewById(R.id.trailer_textView) ;
            viewHoder.play_image = (ImageView)convertView.findViewById(R.id.play_trailer_icon) ;

            convertView.setTag(viewHoder);
        }else{
            Log.d(LOG_Tag, "Convert view is not null") ;
            viewHoder = (ViewHoder)convertView.getTag() ;
        }

        viewHoder.trailer_text.setText(trailerList.get(position).getTrailer_name());
        viewHoder.play_image.setImageResource(R.drawable.ic_play_arrow_white_36dp);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerList.get(position).getTrailer_vid_key()));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + trailerList.get(position).getTrailer_vid_key()));
                        try {
                            context.startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            context.startActivity(webIntent);
                        }
            }
        });

        return convertView ;

    }

    private class ViewHoder{
        private TextView trailer_text;
        private ImageView play_image ;
    }



//
//    private static final String LOG_TAG = TrailerAdapter.class.getCanonicalName() ;
//
//    int resource ;
//    List<String> trailerList ;
//    LayoutInflater inflater ;
//
//    public TrailerAdapter(@NonNull Context context, @LayoutRes int resource, List<String> objects) {
//        super(context, resource, objects);
//        this.resource = resource ;
//        this.trailerList = objects ;
//        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        TrailerViewHolder trailerViewHolder = null;
//        if(convertView == null){
//
//            Log.d(LOG_TAG, "ConvertView of trailer is NULL") ;
//            convertView = inflater.inflate(resource, null) ;
//            trailerViewHolder = new TrailerViewHolder() ;
//            trailerViewHolder.trailerText = (TextView)convertView.findViewById(R.id.list_textView) ;
//            trailerViewHolder.playImage = (ImageView)convertView.findViewById(R.id.trailer_play_image) ;
//            convertView.setTag(trailerViewHolder);
//
//        }else {
//            Log.d(LOG_TAG, "ConvertView of trailer is not NULL") ;
//            trailerViewHolder = (TrailerViewHolder)convertView.getTag() ;
//        }
//
//        trailerList.get(position) ;
//
//        return convertView ;
//    }
//
//
//    private class TrailerViewHolder{
//
//        private TextView trailerText ;
//        private ImageView playImage ;
//
//    }
//
//
}
