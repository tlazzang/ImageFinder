package com.example.shim.stunitas.Main_Activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shim.stunitas.Adapter.ImageAdapter;
import com.example.shim.stunitas.Model.Document;

import com.example.shim.stunitas.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;
    private EditText et_query;
    private ProgressBar progressBar;
    private MainContract.Presenter presenter;

    //리사이클러뷰 페이징 구현을 위한 변수
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerView);
        et_query = findViewById(R.id.main_et_query);
        progressBar = findViewById(R.id.main_progressBar);
        presenter = new MainPresenterImpl(this, new GetImageInteractorImpl());
        imageAdapter = new ImageAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(imageAdapter);

        et_query.addTextChangedListener(new TextWatcher() {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        presenter.requestDataFromServer(et_query.getText().toString(), page);
                    }
                };
                if(et_query.getText().toString().length() > 0) {
                    handler.postDelayed(runnable, 1000);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){ //리사이클러뷰가 최하단인 경우
                    presenter.requestDataFromServer(et_query.getText().toString(), ++page);
                }
            }
        });

    }

    @Override
    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(MainActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void addDataToRecyclerView(ArrayList<Document> documentList) {
        imageAdapter.addItem(documentList);
    }

    @Override
    public void onResponseFailure(Throwable t) {
        showToast(t.getMessage());
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
