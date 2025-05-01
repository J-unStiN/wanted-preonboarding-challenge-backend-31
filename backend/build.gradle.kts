plugins {
        java
        id("org.springframework.boot") version "3.4.5"
        id("io.spring.dependency-management") version "1.1.7"
        id("org.flywaydb.flyway") version "10.15.2"
    }

    group = "com.ex"
    version = "0.0.1-SNAPSHOT"

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
    }

flyway {
    url = "jdbc:postgresql://localhost:5432/backend"
    user = "postgres"
    password = "postgres"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    baselineOnMigrate = true
    driver = "org.postgresql.Driver"
}

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.flywaydb:flyway-core")
        implementation("org.flywaydb:flyway-database-postgresql")
        compileOnly("org.projectlombok:lombok")
        runtimeOnly("org.postgresql:postgresql")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        // flywayPlugins 대신 일반 dependencies 블록에서 설정
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // 부트 실행 전에 Flyway 마이그레이션이 실행되도록 의존성 추가
    tasks.named("bootRun") {
        dependsOn(tasks.named("flywayMigrate"))
    }