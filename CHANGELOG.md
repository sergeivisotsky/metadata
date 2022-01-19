# Changelog

## 1.2.0
### Features
* [#60] - Add a support for hasMoreElements and totalElements and estimated elements
* [#68] - Adopt each SQL generation formatter for every database currently supported
* [#72] - Upgrade JDK up to 17 and Spring up to 2.6.2
* [#73] - Make SQL dialect activation by property
* [#75] - Initialise chart metadata beans by a single property using @ConditionalOnProperty
* [#77] - Restructure Liquibase scripts so that Chart metadata related schema be included/excluded when needed
* [#79] - Bring up Swagger

### Bug fixes
* [#62] - Add missing conditional annotations for metadata provisioning beans
* [#63] - Add missing time formatter realization

### Vulnerability fixes
* [#70] - Eliminate CVE-2020-8908 produced by Guava

## 1.1.4
### Vulnerability fixes
* [#70 | BACKPORT] - Eliminate CVE-2020-8908 produced by Guava

## 1.1.3
### Bug fixes
* [#63 | BACKPORT] - Add missing time formatter realization

## 1.1.2
### Features
* [#60 | BACKPORT] - Add a support for hasMoreElements and totalElements and estimated elements

## 1.1.1
### Bug fixes
* [#54] - Queried view_field with name a_column_one does not exist
* [#55] - ui_description column does not allow to insert value which length is more than 25 characters
* [#56] - SQL syntax exception when query API is executed

## 1.1.0
### Features
* [#30] - Introduce charts metadata
* [#37] - Enhance a view metadata fields
* [#38] - Introduce a query API with a proper filtering which will fetch an initial content
* [#41] - Implement an Oracle dialect
* [#42] - Implement a MSSQL dialect
* [#45] - Implement a MySQL dialect

### Improvements
* [#35] - Remove redundant cardinality field for a view metadata
* [#41] - Remove a chart metadata auto-configuration

### Bug fixes
* [#46] - Filtering functionality lacks order list parsing logic
* [#47] - Fix ordering logic
* [#48] - Generated query SQL is syntactically incorrect
* [#49] - Column names and aliases are always supposed to be uppercase

## 1.0.0
### Features
* View metadata
* Form metadata
* ComboBox metadata
* Lookup metadata