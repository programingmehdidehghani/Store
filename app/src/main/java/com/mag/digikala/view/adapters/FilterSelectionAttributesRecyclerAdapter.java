package com.mag.digikala.view.adapters;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.digikala.data.repository.FilterRepository;
import com.mag.digikala.R;
import com.mag.digikala.databinding.LayoutFilterSelectionAttributesListItemBinding;
import com.mag.digikala.viewmodel.FilterSelectionViewModel;

import java.util.List;

public class FilterSelectionAttributesRecyclerAdapter extends RecyclerView.Adapter<FilterSelectionAttributesRecyclerAdapter.FilterSelectionAttributesRecyclerViewHolder> {

    private FilterSelectionViewModel viewModel;

    private Activity activity;
    private List<FilterRepository.Attribute> attributes;
    private FilterRepository.Attribute selected;
    private FilterSelectionTermsRecyclerAdapter termsRecyclerAdapter;


    // should be changed
    public FilterSelectionAttributesRecyclerAdapter(FilterSelectionViewModel viewModel, FilterSelectionTermsRecyclerAdapter adapter) {
        this.viewModel = viewModel;
        this.attributes = viewModel.getAttributes().getValue();
        this.selected = viewModel.getSelectedAttribute().getValue();
        this.termsRecyclerAdapter = adapter;
    }

    @NonNull
    @Override
    public FilterSelectionAttributesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutFilterSelectionAttributesListItemBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.layout_filter_selection_attributes_list_item, parent, false);
        setObserve();
        return new FilterSelectionAttributesRecyclerViewHolder(binding);
    }

    private void setObserve() {
        viewModel.getAttributes().observe((LifecycleOwner) activity, attributes -> {
            this.attributes = attributes;
        });
        viewModel.getSelectedAttribute().observe((LifecycleOwner) activity, selected -> {
            this.selected = selected;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull FilterSelectionAttributesRecyclerViewHolder holder, int position) {
        holder.bind(attributes.get(position));
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    public class FilterSelectionAttributesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private FilterRepository.Attribute productAttribute;
        private TextView attrText;

        public FilterSelectionAttributesRecyclerViewHolder(@NonNull LayoutFilterSelectionAttributesListItemBinding binding) {

            super(binding.getRoot());

            attrText = binding.layoutFilterSelectionAttributesListItemTitle;

            attrText.setOnClickListener(view -> {
                viewModel.onAttributeClicked(productAttribute);
                viewModel.clearSelectedTerms();
                notifyDataSetChanged();
                termsRecyclerAdapter.notifyDataSetChanged();;
            });

        }

        private void selectedAttributeUi() {

            if (selected != null && selected.getId().equals(productAttribute.getId())) {
                attrText.setTextColor(activity.getResources().getColor(R.color.cardview_dark_background));
                attrText.setBackgroundColor(activity.getResources().getColor(R.color.digikala_dark_white));
            } else {
                attrText.setTextColor(activity.getResources().getColor(R.color.digikala_raw_white));
                attrText.setBackgroundColor(activity.getResources().getColor(R.color.cardview_dark_background));
            }

        }

        private void bind(FilterRepository.Attribute productAttribute) {
            this.productAttribute = productAttribute;

            selectedAttributeUi();

            attrText.setText(productAttribute.getName());
        }

    }

}
