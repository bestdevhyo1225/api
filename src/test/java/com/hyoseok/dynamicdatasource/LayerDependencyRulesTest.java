package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class LayerDependencyRulesTest {

    // 'access'는 실제 액세스, 즉 필드 액세스, 메서드 호출에 의한 위반 만 포착한다.

    @ArchTest
    static final ArchRule Service는_Controller에_접근해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.usecase.*..")
                    .should().accessClassesThat().resideInAPackage("..web..");

    @ArchTest
    static final ArchRule Repository_그리고_Entity는_Service에_접근해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.entity.*..")
                    .should().accessClassesThat().resideInAPackage("..domain.usecase.*..");

    @ArchTest
    static final ArchRule Service는_Controller_또는_기타_Serivce에_의해서만_접근되어야_합니다 =
            classes().that().resideInAPackage("..domain.usecase.*..")
                    .should().onlyBeAccessed().byAnyPackage("..web..", "..domain.usecase.*..");

    @ArchTest
    static final ArchRule Service는_Domain_Layer에만_접근해야합니다 =
            classes().that().resideInAPackage("..domain.usecase.*..")
                    .should().onlyAccessClassesThat().resideInAnyPackage("..domain.*..", "java..", "org..");

    // 'dependOn'은 다양한 위반을 포착한다. (유형 필드 포함, 유형 메소드 매개 변수 포함, 유형 확장 포함)

    @ArchTest
    static final ArchRule Service는_Controller에_의존해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.usecase.*..")
                    .should().dependOnClassesThat().resideInAPackage("..web..");

    @ArchTest
    static final ArchRule Repository_그리고_Entity는_Service에_의존해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.entity.*..")
                    .should().dependOnClassesThat().resideInAPackage("..domain.usecase.*..");

    @ArchTest
    static final ArchRule Service는_Controller_또는_기타_Serivce에_의해서만_의존해야_합니다 =
            classes().that().resideInAPackage("..domain.usecase.*..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..web..", "..domain.usecase.*..");

    @ArchTest
    static final ArchRule Service는_Domain_Layer에만_의존해야합니다 =
            classes().that().resideInAPackage("..domain.usecase.*..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..domain.*..", "java..", "javax..", "org..");
}
