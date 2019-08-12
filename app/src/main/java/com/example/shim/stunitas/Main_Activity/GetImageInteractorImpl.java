package com.example.shim.stunitas.Main_Activity;

import com.example.shim.stunitas.Model.KaKaoResponse;
import com.example.shim.stunitas.Network.KaKaoApiService;
import com.example.shim.stunitas.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetImageInteractorImpl implements MainContract.GetImageInteractor {

    private KaKaoResponse kaKaoResponse;
    private final int size = 10;

    @Override
    public void getDocumentList(final OnFinishedListener onFinishedListener, String query, final int page) {
        if (kaKaoResponse == null) {
            KaKaoApiService service = RetrofitInstance.getInstance().create(KaKaoApiService.class);
            Call<KaKaoResponse> call = service.getImage(query, page, size);

            call.enqueue(new Callback<KaKaoResponse>() {
                @Override
                public void onResponse(Call<KaKaoResponse> call, Response<KaKaoResponse> response) {
                    if (response.isSuccessful()) {
                        kaKaoResponse = response.body();
                        onFinishedListener.onFinished(response.body().getDocuments());
                    }
                    else{
                        onFinishedListener.onError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<KaKaoResponse> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
        else{
            if(!kaKaoResponse.getMeta().getIsEnd()){
                KaKaoApiService service = RetrofitInstance.getInstance().create(KaKaoApiService.class);
                Call<KaKaoResponse> call = service.getImage(query, page, size);

                call.enqueue(new Callback<KaKaoResponse>() {
                    @Override
                    public void onResponse(Call<KaKaoResponse> call, Response<KaKaoResponse> response) {
                        if (response.isSuccessful()) {
                            kaKaoResponse = response.body();
                            onFinishedListener.onFinished(response.body().getDocuments());
                        }
                    }

                    @Override
                    public void onFailure(Call<KaKaoResponse> call, Throwable t) {
                        onFinishedListener.onFailure(t);
                    }
                });
            }
        }
    }

}
