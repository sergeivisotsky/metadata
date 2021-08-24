package org.sergei.metadata.selector;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An interface to be implemented by mappers from {@link ResultSet} to DTO.
 *
 * @param <F> map from.
 * @param <T> map to.
 * @author Sergei Visotsky
 */
public interface MetadataMapper<F, T> extends Function<F, T> {

    /**
     * Get an SQL containing a new fields added during customization.
     *
     * @return SQL to execute.
     */
    String getSql();

    /**
     * Map a collection of elements.
     *
     * @param fromList collection to map.
     * @return returning collection with mapped elements.
     */
    default List<T> applyList(List<F> fromList) {
        return fromList.stream().map(this).collect(Collectors.toList());
    }

}
