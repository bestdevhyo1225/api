package com.hyoseok.dynamicdatasource;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.*;

@AnalyzeClasses(packages = "com.hyoseok.dynamicdatasource")
public class CyclicDependencyRulesTest {

    @ArchTest
    static final ArchRule Domain_Layer_내부에는_순환적으로_호출되는_상황이_없어야_합니다 =
            slices().matching("..(domain).(*)..").should().beFreeOfCycles();
}
