package com.gsix.dvr_application.ui.mycheckins;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCheckinsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCheckinsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}