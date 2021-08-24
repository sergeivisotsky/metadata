package org.sergei.metadata.selector;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Mapper<F, T> extends Function<F, T> {

    String getSql();

    default List<T> applyList(List<F> fromList) {
        return fromList.stream().map(this).collect(Collectors.toList());
    }

}
