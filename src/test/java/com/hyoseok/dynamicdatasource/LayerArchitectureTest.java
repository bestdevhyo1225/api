package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.*;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class LayerArchitectureTest {

    @ArchTest
    static final ArchRule 레이어_의존성을_테스트한다 = layeredArchitecture()
            .layer("Controller").definedBy("com.hyoseok.dynamicdatasource.web.http")
            .layer("Service").definedBy("com.hyoseok.dynamicdatasource.domain.usecase.*")
            .layer("Repository").definedBy("com.hyoseok.dynamicdatasource.domain.entity.*")
            .layer("Data").definedBy("com.hyoseok.dynamicdatasource.data")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service", "Data");
}
