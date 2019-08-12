package com.example.shim.stunitas.Main_Activity;


import com.example.shim.stunitas.Model.Document;

import java.util.ArrayList;

public class MainPresenterImpl implements MainContract.Presenter, MainContract.GetImageInteractor.OnFinishedListener {
    private MainContract.View view;
    private MainContract.GetImageInteractor getImageInteractor;

    public MainPresenterImpl(MainContract.View view, MainContract.GetImageInteractor getImageInteractor) {
        this.view = view;
        this.getImageInteractor = getImageInteractor;
    }

    @Override
    public void onFinished(ArrayList<Document> documentList) {
        if(view != null) {
            view.hideProgress();
            view.hideKeyboard();
            view.addDataToRecyclerView(documentList);
        }
    }

    @Override
    public void onError(String message) {
        if(view != null){
            view.showToast(message);
            view.hideKeyboard();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(view != null) {
            view.hideProgress();
            view.hideKeyboard();
            view.onResponseFailure(t);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestDataFromServer(String query, int page) {
        if(view != null) {
            view.showProgress();
            getImageInteractor.getDocumentList(this, query, page);
        }
    }
}
