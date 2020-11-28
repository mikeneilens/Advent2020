plugins {
    kotlin("jvm") version "1.4.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}