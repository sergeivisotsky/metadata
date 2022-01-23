package io.github.sergeivisotsky.metadata.engine.itest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.github.sergeivisotsky.metadata.engine.itest.domain.ExtendedNavigation;
import io.github.sergeivisotsky.metadata.engine.itest.domain.ExtendedNavigationElement;
import io.github.sergeivisotsky.metadata.engine.domain.Navigation;
import io.github.sergeivisotsky.metadata.engine.domain.NavigationElement;
import io.github.sergeivisotsky.metadata.engine.domain.NavigationType;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

public class NavigationMapper implements MetadataMapper<List<Navigation>> {

    @Override
    public String getSql() {
        return "SELECT n.type,\n" +
                "       n.number_of_elements,\n" +
                "       n.fixed,\n" +
                "       n.resizable,\n" +
                "       ne.code,\n" +
                "       ne.value,\n" +
                "       ne.is_active\n" +
                "FROM navigation n\n" +
                "         LEFT JOIN navigation_element ne on n.id = ne.navigation_id\n" +
                "WHERE n.view_name = :viewName";
    }

    @Override
    public List<Navigation> map(ResultSet rs) {
        try {
            List<Navigation> navFromRsList = new ArrayList<>();
            while (rs.next()) {
                ExtendedNavigation navigation = new ExtendedNavigation();
                navigation.setNumberOfElements(rs.getInt("number_of_elements"));
                navigation.setType(NavigationType.valueOf(rs.getString("type")));
                navigation.setResizable(rs.getBoolean("resizable"));
                navigation.setFixed(rs.getBoolean("fixed"));

                List<NavigationElement> navElements = new ArrayList<>();

                ExtendedNavigationElement navElem = new ExtendedNavigationElement();
                navElem.setCode(rs.getString("code"));
                navElem.setValue(rs.getString("value"));
                navElem.setActive(rs.getBoolean("is_active"));
                navElements.add(navElem);
                navigation.setElements(navElements);

                navFromRsList.add(navigation);
            }
            return navFromRsList;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get value from ResultSet for Mapper: {}" +
                    LookupMetadataMapper.class.getSimpleName(), e);
        }
    }
}
