plugins {
    java
    idea
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("io.freefair.lombok") version "5.3.0"
}

group = "com.gorlah"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_15

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")
    implementation("com.github.husnjak:IGDB-API-JVM:1.0.1")
    implementation("com.google.guava:guava:30.0-jre")
    implementation("com.google.protobuf:protobuf-java:3.14.0")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.data:spring-data-elasticsearch")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}