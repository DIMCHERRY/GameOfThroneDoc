package com.littlered.gameofthronedoc.progress;

/**
 * @author : littleredDLZ
 */
public interface ObserverOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable t);
}
