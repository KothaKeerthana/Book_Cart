package com.example.jpmc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mcontext;
    private List<Bookk> mbooks;

    public BookAdapter(Context context,List<Bookk> books){
        mcontext = context;
        mbooks = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(mcontext).inflate(R.layout.lists,parent,false);
        return  new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Bookk bookCurrent = mbooks.get(position);
        holder.bktitle.setText((bookCurrent.getTitle()));
        holder.bkauthor.setText((bookCurrent.getAuthor()));
        holder.bkdesc.setText((bookCurrent.getDescription()));



        Glide.with(mcontext)
            .load(bookCurrent.getImageUrl())
                .into(holder.bkimg);



       // holder.bkimg.setImageBitmap((bookCurrent.getTitle()));

    }

    @Override
    public int getItemCount() {

        return mbooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        public TextView bktitle,bkauthor,bkdesc;
        public ImageView bkimg;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bktitle = itemView.findViewById(R.id.textView7);
            bkauthor = itemView.findViewById(R.id.textView8);
            bkdesc = itemView.findViewById(R.id.textView9);
            bkimg = itemView.findViewById(R.id.imageView4);




        }
    }

}

















   /* private final Context context;
    private final ArrayList<Bookk> values;

    public BookAdapter(@NonNull Context context, ArrayList<Bookk> list) {

        super(context, R.layout.lists,list);
        this.context = context;
        this.values = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.lists,parent,false);

        TextView title = (TextView) rowView.findViewById(R.id.textView7);
        TextView author = (TextView) rowView.findViewById(R.id.textView8);
        TextView desc = (TextView) rowView.findViewById(R.id.textView9);
        ImageView bkimg = (ImageView) rowView.findViewById(R.id.imageView4);





        return super.getView(position, convertView, parent);
    }

    */


