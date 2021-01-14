package com.mag.digikala.view.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mag.digikala.view.activities.CardActivity;
import com.mag.digikala.view.adapters.SliderViewPagerAdapter;
import com.mag.digikala.view.adapters.SpinnerAdapter;
import com.mag.digikala.R;
import com.mag.digikala.data.repository.CardRepository;
import com.mag.digikala.data.repository.FilterRepository;
import com.mag.digikala.databinding.FragmentProductDetailBinding;
import com.mag.digikala.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding binding;
    private ProductViewModel viewModel;

    private SliderViewPagerAdapter sliderAdapter;


    public static ProductDetailFragment newInstance() {

        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_product_detail, container, false);
        binding.setProductViewModel(viewModel);
        viewModel.getProduct().observe(this, product -> {
            binding.setProductViewModel(viewModel);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();

        setSpinners();

        viewModel.getImagesSrc().observe(this, strings -> sliderAdapter.setImagesFragment(strings));

        binding.productDetailFragmentProductRegularPrice.setPaintFlags(binding.productDetailFragmentProductSalePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.productDetailFragmentCardBtn.setOnClickListener(cardView -> {

            CardRepository.getInstance().addToCard(viewModel.getProduct().getValue());
            getActivity().startActivity(CardActivity.newIntent(getContext()));

        });

    }

    private void setAdapter() {
        sliderAdapter = new SliderViewPagerAdapter(getFragmentManager());
        binding.productDetailActivityViewPager.setAdapter(sliderAdapter);
    }


    // Should be changed
    private void setSpinners() {
        List<String> spinnerColorArray = new ArrayList<>();
        for (FilterRepository.Term term : FilterRepository.getInstance().getAttributeById("3").getTerms())
            spinnerColorArray.add(term.getName());
        SpinnerAdapter colorAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerColorArray
        );
        binding.productDetailFragmentColorSpinner.setAdapter(colorAdapter);


        List<String> spinnerSizeArray = new ArrayList<>();
        for (FilterRepository.Term term : FilterRepository.getInstance().getAttributeById("4").getTerms())
            spinnerSizeArray.add(term.getName());
        SpinnerAdapter sizeAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerSizeArray
        );
        binding.productDetailFragmentSizeSpinner.setAdapter(sizeAdapter);

    }

}
