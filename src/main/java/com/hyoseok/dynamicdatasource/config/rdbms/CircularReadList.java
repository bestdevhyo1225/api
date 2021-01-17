package com.hyoseok.dynamicdatasource.config.rdbms;

import java.util.List;

public class CircularReadList<T> {

    private final List<T> list;
    private Integer counter = 0;

    public CircularReadList(List<T> list) {
        this.list = list;
    }

    public T getOne() {
        if(counter + 1 >= list.size()) {
            counter = -1;
        }
        return list.get(++counter);
    }
}
