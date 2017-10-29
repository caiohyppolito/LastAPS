package com.unip.apppedido.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unip.apppedido.R;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.viewholders.CategoryViewHolder;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    public interface ICategoryListener {
        void onClickListener(CategoryModel category);
    }

    private ICategoryListener mCategoryListener;
    private List<CategoryModel> mListCategory;

    public CategoryAdapter(List<CategoryModel> listCategory, ICategoryListener listener) {
        mListCategory = listCategory;
        mCategoryListener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter, parent, false);

        CategoryViewHolder viewHolder = new CategoryViewHolder(view);

        viewHolder.relativeLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCategoryListener != null) {
                    View parent = (View) view.getParent();

                    int position = (int) parent.getTag();

                    mCategoryListener.onClickListener(mListCategory.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel category = mListCategory.get(position);

        holder.textViewNameCategory.setText(category.getName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mListCategory != null ? mListCategory.size() : 0;
    }
}