package com.example.myapplication;





import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Recyclerview_adapter extends RecyclerView.Adapter<Recyclerview_adapter.MyViewHolder>{

     RecyclerView recyclerView;
    private Context mcontext;
    private List<Book> mbook;



    public Recyclerview_adapter(RecyclerView recyclerView, Context mcontext, List<Book> mbook) {
        this.recyclerView=recyclerView;
        this.mcontext = mcontext;
        this.mbook = mbook;
    }

    public void update(Book b1){
        mbook.add(new Book(b1.getName(),b1.getDescription(),b1.getImage_url(),b1.getBook_url(), b1.getBook_price()));
        notifyDataSetChanged();
    }
    public void clear(){
        mbook.clear();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater= LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.cardview_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.bookname.setText(mbook.get(position).getName());
        Picasso.get().load(mbook.get(position).getImage_url()).into(holder.bookimage);
        holder.cardView.setOnClickListener(view -> {

            Intent i = new Intent(mcontext,Book_info.class);
            i.putExtra("book_name",mbook.get(position).getName());
            i.putExtra("img_url",mbook.get(position).getImage_url());
            i.putExtra("book_url",mbook.get(position).getBook_url());
            i.putExtra("description",mbook.get(position).getDescription());
            i.putExtra("book_price",mbook.get(position).getBook_price() );
            mcontext.startActivity(i);

        });

    }

    @Override
    public int getItemCount() {
        return mbook.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bookname;
        ImageView bookimage;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            bookname= itemView.findViewById(R.id.book_name_id);
            bookimage= itemView.findViewById(R.id.image_view_id);
            cardView= itemView.findViewById(R.id.cardview_id);

        }



    }

}
