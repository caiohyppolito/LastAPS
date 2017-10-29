package com.unip.apppedido.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unip.apppedido.R;
import com.unip.apppedido.models.EstabelecimentoModel;
import com.unip.apppedido.viewholders.EstabelecimentoViewHolder;

import java.util.List;

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoViewHolder> {
    public interface IEstabelecimentoListener{
        void onClickListener(EstabelecimentoModel estabelecimento);
    }

    private IEstabelecimentoListener mEstabelecimentoListener;
    private List<EstabelecimentoModel> mListEstabelecimento;

    public EstabelecimentoAdapter(List<EstabelecimentoModel> listEstabelecimento, IEstabelecimentoListener listener){
        mListEstabelecimento = listEstabelecimento;
        mEstabelecimentoListener = listener;
    }

    @Override
    public EstabelecimentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estabelecimento_adapter, parent, false);

        EstabelecimentoViewHolder viewHolder = new EstabelecimentoViewHolder(view);

        viewHolder.relativeLayoutEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEstabelecimentoListener != null)
                {
                    View parent = (View) view.getParent();
                    
                    int position = (int) parent.getTag();

                    mEstabelecimentoListener.onClickListener(mListEstabelecimento.get(position));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EstabelecimentoViewHolder holder, int position) {
        EstabelecimentoModel estabelecimento = mListEstabelecimento.get(position);

        holder.textViewNameEstabelecimento.setText(estabelecimento.getName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mListEstabelecimento != null ? mListEstabelecimento.size() : 0;
    }
}