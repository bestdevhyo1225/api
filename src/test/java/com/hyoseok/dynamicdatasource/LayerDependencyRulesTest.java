package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class LayerDependencyRulesTest {

    // 'access'는 실제 액세스, 즉 필드 액세스, 메서드 호출에 의한 위반 만 포착한다.

    @ArchTest
    static final ArchRule Usecase는_Web에_접근해서는_안됩니다 =
            noClasses().that().resideInAPackage("..usecase.*..")
                    .should().accessClassesThat().resideInAPackage("..web..");

    @ArchTest
    static final ArchRule Domain은_Usecase에_접근해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.*..")
                    .should().accessClassesThat().resideInAPackage("..usecase.*..");

    @ArchTest
    static final ArchRule Usecase는_Web_또는_기타_Usecase에_의해서만_접근되어야_합니다 =
            classes().that().resideInAPackage("..usecase.*..")
                    .should().onlyBeAccessed().byAnyPackage("..web..", "..usecase.*..");

    @ArchTest
    static final ArchRule Usecase는_Domain_또는_기타_Usecase에만_접근해야합니다 =
            classes().that().resideInAPackage("..usecase.*..")
                    .should().onlyAccessClassesThat().resideInAnyPackage("..domain.*..", "..usecase.*..", "java..", "org..");

    // 'dependOn'은 다양한 위반을 포착한다. (유형 필드 포함, 유형 메소드 매개 변수 포함, 유형 확장 포함)

    @ArchTest
    static final ArchRule Usecase는_Web에_의존해서는_안됩니다 =
            noClasses().that().resideInAPackage("..usecase.*..")
                    .should().dependOnClassesThat().resideInAPackage("..web..");

    @ArchTest
    static final ArchRule Domain은_Usecase에_의존해서는_안됩니다 =
            noClasses().that().resideInAPackage("..domain.*..")
                    .should().dependOnClassesThat().resideInAPackage("..usecase.*..");

    @ArchTest
    static final ArchRule Usecase는_Web_또는_기타_Usecase에_의해서만_의존해야_합니다 =
            classes().that().resideInAPackage("..usecase.*..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..web..", "..usecase.*..");

    @ArchTest
    static final ArchRule Usecase는_Domain_또는_기타_Usecase에만_의존해야합니다 =
            classes().that().resideInAPackage("..usecase.*..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..domain.*..", "..usecase.*..", "java..", "javax..", "org..");
}
