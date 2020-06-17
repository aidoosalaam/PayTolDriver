package com.andela.buildsdgs.rtrc.ui.main.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.DepositResult;

import java.util.List;

public class DepositRecyclerAdaptor extends RecyclerView.Adapter<DepositRecyclerAdaptor.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<DepositResult> depositResultList;

    public DepositRecyclerAdaptor(Context mContext, List<DepositResult> depositResultList) {
        this.layoutInflater = LayoutInflater.from(mContext);
        this.depositResultList = depositResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_deposit_list,parent,false);
        return new DepositRecyclerAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DepositResult depositResult = depositResultList.get(position);
        String datee = depositResult.getCreatedAt().split("T")[0];
        holder.mTextDepositRef.setText("REF : "+depositResult.getRefCode());
        holder.mTextDepositMethod.setText("Method : " +depositResult.getMethod());
        holder.mTxtDepositStatus.setText(depositResult.getStatus());
        holder.mTxtDepositDate.setText("Date : " + datee);
        holder.mTxtDepositAmount.setText("GHS " + depositResult.getAmount());
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return depositResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTextDepositRef;
        public final TextView mTextDepositMethod;
        public final TextView mTxtDepositDate;
        public final TextView mTxtDepositStatus;
        public final TextView mTxtDepositAmount;
        public int mPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextDepositRef = itemView.findViewById(R.id.txt_deposit_ref);
            mTextDepositMethod = itemView.findViewById(R.id.txt_deposit_method);
            mTxtDepositDate = itemView.findViewById(R.id.txt_deposit_date);
            mTxtDepositStatus = itemView.findViewById(R.id.txt_deposit_status);
            mTxtDepositAmount = itemView.findViewById(R.id.txt_deposit_amount);
        }
    }
}
