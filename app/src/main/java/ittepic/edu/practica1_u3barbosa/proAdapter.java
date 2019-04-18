package ittepic.edu.practica1_u3barbosa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class proAdapter extends RecyclerView.Adapter<proAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<propietario> proList;

    public proAdapter(Context mCtx, List<propietario> proList) {
        this.mCtx = mCtx;
        this.proList = proList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_pro,viewGroup,false));



    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {

        propietario pro = proList.get(i);

        productViewHolder.textViewName.setText(pro.getNombre());
        productViewHolder.textViewBrand.setText(pro.getDomicilio());
        productViewHolder.textViewDesc.setText(pro.getEdad());
        productViewHolder.textViewPrice.setText(pro.getNacionalidad());
    }

    @Override
    public int getItemCount() {
        return proList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName,textViewBrand,textViewDesc,textViewPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_na);
            textViewBrand = itemView.findViewById(R.id.textview_dom);
            textViewDesc = itemView.findViewById(R.id.textview_ed);
            textViewPrice = itemView.findViewById(R.id.textview_nac);

            itemView.setOnClickListener( this);


        }

        @Override
        public void onClick(View v) {
            propietario product = proList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, Main5Activity.class);
            intent.putExtra("Propietario", product);
            mCtx.startActivity(intent);

        }
    }
}
