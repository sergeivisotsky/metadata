# UI metadata provider
A UI metadata provider is a library to build a metadata driven UI.

## Description
This approach is especially useful in project teams with a high database competence rather than UI.

In general provides an element alignment by invocation of a single endpoint which provides all data required like
cardinality, language, font size, and font itself.

## Project structure

```text
- core - aggregate module for all other modules
   - metadata-deploy - hold a liquibase deployment scripts for a base metadata schema
   - metadata-selector - main metadata provider library to be added as a Maven dependency to implementation projects
- ref-impl - sample modules to easier get started
   - metadata-deployer - database schema deployment Spring Boot application
   - metadata-provider-app - metadata provisioning main application which demonstrates a common implementation scenario
```

## Get started
To get started with a usage of metadata provider just add the following maven dependency to the main application:

```html
<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-selector</artifactId>
    <version>0.0.2</version>
</dependency>
```

And the following dependency to a deployment application:
```html
<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-deploy</artifactId>
    <version>0.0.2</version>
</dependency>
```

It is also highly recommended adding library starter to have a compatible version of both 
dependencies to your `dependencyManegemnt` section of parent POM: 
```html
<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-provider-starter</artifactId>
    <version>0.0.2</version>
    <scope>import</scope>
    <type>pom</type>
</dependency>
```

It will be loaded from Maven central.

Fully open-source and intuitive. Just enjoy!

## Contribution
There are just four rules of contribution:
1. Create an issue in GitHub with a fully described functionality
2. Fork repository, provide your enhancement and create a pull request
3. If a new class is required to be created it should contain a JavaDoc @author on top of the class
4. If a new class if a public API then JavaDoc should be written on top of each method. Method level JavaDoc should contain a description of what it does.