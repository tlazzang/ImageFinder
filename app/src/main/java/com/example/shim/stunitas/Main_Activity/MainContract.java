package com.example.shim.stunitas.Main_Activity;

import com.example.shim.stunitas.Model.Document;

import java.util.ArrayList;

public interface MainContract {

    interface GetImageInteractor{

        interface OnFinishedListener {
            void onFinished(ArrayList<Document> documentList);

            void onError(String message);

            void onFailure(Throwable t);
        }

        void getDocumentList(OnFinishedListener onFinishedListener, String query, int page);

    }

    interface View{
        void showProgress();

        void hideProgress();

        void hideKeyboard();

        void addDataToRecyclerView(ArrayList<Document> documentList);

        void onResponseFailure(Throwable t);

        void showToast(String s);
    }

    interface Presenter{
        void onDestroy();

        void requestDataFromServer(String query, int page);
    }
}
