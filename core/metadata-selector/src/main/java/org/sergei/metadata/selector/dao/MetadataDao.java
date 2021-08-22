package org.sergei.metadata.selector.dao;

import org.sergei.metadata.selector.dto.FormMetadata;

public interface MetadataDao {

    FormMetadata getFormMetadata(String formName, String lang);

}
