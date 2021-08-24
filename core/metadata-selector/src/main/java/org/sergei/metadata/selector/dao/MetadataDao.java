package org.sergei.metadata.selector.dao;

import org.sergei.metadata.selector.dto.FormMetadata;

/**
 * Interface to be considered both API and SPI for a metadata retrieval functionality.
 * In general do not supposed to be fully SPIed apart from this a corresponding
 * metadata DAO class should be extended and enhanced to support an additional fields.
 *
 * @author Sergei Visotsky
 */
public interface MetadataDao {

    /**
     * Gets the all metadata.
     *
     * @param formName name of the form to retrieve metadata for.
     * @param lang     language on which metadata should be handled.
     * @return metadata DTO.
     */
    FormMetadata getFormMetadata(String formName, String lang);

}
