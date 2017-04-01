package com.example.tarunkukreja.moviesdb;

///**
// * Created by tarunkukreja on 26/03/17.
// */
//
//public class TrailerAdapter extends ArrayAdapter {
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
//}
