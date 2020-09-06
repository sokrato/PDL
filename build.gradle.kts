group = "io.github.sokrato"
version = "0.1.0-SNAPSHOT"

plugins {
    application

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.allopen")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.github.sokrato.gmate")
}

repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(platform("io.github.sokrato:bom:0.1.0-SNAPSHOT"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))

    // implementation("org.clojars.sokrato:toolkit:0.1.0")
//    implementation(files("/Users/xux/Documents/leth/clojure/toolkit/target/toolkit-0.1.1-SNAPSHOT.jar"))
//    implementation("org.clojure:clojure:1.10.1")
//    implementation("nrepl:nrepl:0.7.0")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.google.guava:guava")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

    runtimeOnly("com.zaxxer:HikariCP")
    implementation("org.mybatis:mybatis")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.xerial:sqlite-jdbc")
    runtimeOnly("com.github.gwenn:sqlite-dialect")
}

application {
    // Define the main class for the application.
    mainClassName = "com.xux.elib.AppKt"
}

// bootJar from spring plugin will do all these
//val fatJar = task("fatJar", type = Jar::class) {
//    archiveBaseName.set("${archiveBaseName.get()}-standalone")
//
//    manifest {
//        attributes["Implementation-Title"] = "Personal Digital Library"
//        attributes["Implementation-Version"] = project.version
//        attributes["Main-Class"] = application.mainClassName
//    }
//
//    from(configurations.runtimeClasspath.get().map {
//        if (it.isDirectory) it else zipTree(it)
//    })
//    with(tasks.jar.get() as CopySpec)
//}
//
//tasks.named("build") {
//    dependsOn(fatJar)
//}

// https://kotlinlang.org/docs/reference/using-gradle.html#compiler-options
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Jar> {
    from("src/main/clojure") {
        include("**")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

configurations {
    all {
        // or ./gradlew build --refresh-dependencies
        resolutionStrategy.cacheChangingModulesFor(1, "hours")
    }
}
