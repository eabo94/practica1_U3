package ittepic.edu.practica1_u3barbosa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Carro> productList;

    public CarAdapter(Context mCtx, List<Carro> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_car, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Carro product = productList.get(i);

        productViewHolder.textViewName.setText(product.getPlaca());
        productViewHolder.textViewBrand.setText(product.getColor());
        productViewHolder.textViewDesc.setText(product.getMarca());
        productViewHolder.textViewPrice.setText(product.getTipo());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName,textViewBrand,textViewDesc,textViewPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);

            itemView.setOnClickListener( this);
        }

        @Override
        public void onClick(View v) {
            Carro product = productList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, Activity_update_car.class);
            intent.putExtra("Carros", product);
            mCtx.startActivity(intent);
        }
    }
}
