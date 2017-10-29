package com.unip.apppedido.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unip.apppedido.R;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.viewholders.CategoryViewHolder;
import com.unip.apppedido.viewholders.ProductViewHolder;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    public interface IProductListener {
        void onClickListener(ProductModel product);
    }

    private IProductListener mProductListener;
    private List<ProductModel> mListProduct;

    public ProductAdapter(List<ProductModel> listProduct, IProductListener listener) {
        mListProduct = listProduct;
        mProductListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter, parent, false);

        ProductViewHolder viewHolder = new ProductViewHolder(view);

        viewHolder.relativeLayoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProductListener != null) {
                    View parent = (View) view.getParent();

                    int position = (int) parent.getTag();

                    mProductListener.onClickListener(mListProduct.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductModel product = mListProduct.get(position);

        holder.textViewNameProduct.setText(product.getName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mListProduct != null ? mListProduct.size() : 0;
    }
}