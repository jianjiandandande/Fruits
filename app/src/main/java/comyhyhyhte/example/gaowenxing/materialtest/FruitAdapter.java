package comyhyhyhte.example.gaowenxing.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static comyhyhyhte.example.gaowenxing.materialtest.R.styleable.View;

/**
 * Created by lx on 2017/3/1.
 */

public class FruitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Fruit>mFruitList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(android.view.View view){
            super(view);
            cardView=(CardView)view;
            fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
            fruitName=(TextView)view.findViewById(R.id.fruit_name);
        }

    }

    public FruitAdapter(List<Fruit> list){
        mFruitList=list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Fruit fruit=mFruitList.get(position);
        ((ViewHolder)holder).fruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(((ViewHolder) holder).fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view=LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Intent intent=new Intent(context,FruitActivity.class);
                Fruit fruit=mFruitList.get(position);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                Log.i("--------------------", "onClick: 成功");
                context.startActivity(intent);
            }
        });
        return holder;
    }
}
