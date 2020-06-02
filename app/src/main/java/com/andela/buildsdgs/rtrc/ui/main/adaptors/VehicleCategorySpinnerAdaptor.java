package com.andela.buildsdgs.rtrc.ui.main.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.VehicleCategory;

import java.util.List;

public class VehicleCategorySpinnerAdaptor extends ArrayAdapter<VehicleCategory> {
    private List<VehicleCategory> categoryList;
    private LayoutInflater layoutInflater;
    private int mResource;

    public VehicleCategorySpinnerAdaptor(@NonNull Context context, int resource, @NonNull List<VehicleCategory> objects) {
        super(context, resource, 0, objects);
        categoryList = objects;
        layoutInflater = LayoutInflater.from(context);
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(mResource, parent, false);
        }
        VehicleCategory vehicleCategory = categoryList.get(position);
        TextView categoryName = view.findViewById(R.id.txt_category_name);
        categoryName.setText(vehicleCategory.getName());
        TextView categoryAmount = view.findViewById(R.id.txt_toll_price);
        categoryAmount.setText("GHS " + vehicleCategory.getTollFee());
        return view;
    }
}
