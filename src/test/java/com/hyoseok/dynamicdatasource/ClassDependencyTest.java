package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class ClassDependencyTest {

    @ArchTest
    static final ArchRule RedisService는_QueryServiceImpl에_의해서만_의존해야_합니다 =
            classes().that().haveNameMatching(".*RedisService")
                    .should().onlyHaveDependentClassesThat().haveNameMatching(".*QueryServiceImpl");

    @ArchTest
    static final ArchRule JpaService는_RedisService_또는_QueryServiceImpl에_의해서만_의존해야_합니다 =
            classes().that().haveNameMatching(".*JpaService")
                    .should().onlyHaveDependentClassesThat().haveNameMatching(".*QueryServiceImpl")
                    .orShould().haveNameMatching(".*RedisService");
}
