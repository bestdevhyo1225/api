package com.hyoseok.dynamicdatasource.config;

import java.util.List;

public class CircularSlaveList<T> {

    private final List<T> list;
    private Integer counter = 0;

    public CircularSlaveList(List<T> list) {
        this.list = list;
    }

    public T getOne() {
        if(counter + 1 >= list.size()) {
            counter = -1;
        }
        return list.get(++counter);
    }
}
