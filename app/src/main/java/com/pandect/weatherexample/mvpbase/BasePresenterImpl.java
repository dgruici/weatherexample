package com.pandect.weatherexample.mvpbase;

/**
 * Created by PROFESSORCORE on 7/11/17.
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public V getView() {
        return view;
    }

    public boolean isViewAttached() {
        return null != view;
    }
}
