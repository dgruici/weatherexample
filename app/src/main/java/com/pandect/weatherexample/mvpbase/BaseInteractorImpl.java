package com.pandect.weatherexample.mvpbase;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by PROFESSORCORE on 7/11/17.
 */

public abstract class BaseInteractorImpl implements BaseInteractor {

    public abstract void onRequestSuccess(String data);
    public abstract void onRequestFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t);
}
