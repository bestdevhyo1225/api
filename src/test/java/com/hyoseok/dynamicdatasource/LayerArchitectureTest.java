package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.*;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class LayerArchitectureTest {

    @ArchTest
    static final ArchRule 레이어_의존성을_테스트한다 = layeredArchitecture()
            .layer("Web").definedBy("com.hyoseok.dynamicdatasource.web.http")
            .layer("Usecase").definedBy("com.hyoseok.dynamicdatasource.usecase.*")
            .layer("Domain").definedBy("com.hyoseok.dynamicdatasource.domain.*")
            .layer("Data").definedBy("com.hyoseok.dynamicdatasource.data..")
            .whereLayer("Web").mayNotBeAccessedByAnyLayer()
            .whereLayer("Usecase").mayOnlyBeAccessedByLayers("Web")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Usecase", "Data");
}


