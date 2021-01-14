package com.mag.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.digikala.data.repository.CardRepository;

public class MainToolbarViewModel extends AndroidViewModel {

    private CardRepository repository;
    private MutableLiveData<Integer> numberOfCardProducts;

    public MainToolbarViewModel(@NonNull Application application) {
        super(application);

        repository = CardRepository.getInstance();
        numberOfCardProducts = repository.getNumberOfCardProducts();

    }

    public MutableLiveData<Integer> getNumberOfCardProducts() {
        return numberOfCardProducts;
    }

    public void loadData() {
        repository.loadInitialData();
    }

}
