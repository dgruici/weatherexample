package com.pandect.weatherexample.mvpbase;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by PROFESSORCORE on 7/25/17.
 */

public interface BaseInteractor {

    void onRequestSuccess(String data);
    void onRequestFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t);

}
