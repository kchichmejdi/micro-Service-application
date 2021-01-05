package com.mk.micro;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.mk.micro");

        noClasses()
            .that()
            .resideInAnyPackage("com.mk.micro.service..")
            .or()
            .resideInAnyPackage("com.mk.micro.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.mk.micro.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
