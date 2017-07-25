package com.pandect.weatherexample.mvpbase;

/**
 * Created by PROFESSORCORE on 7/11/17.
 */

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void detachView();
    V getView();
    boolean isViewAttached();

}
