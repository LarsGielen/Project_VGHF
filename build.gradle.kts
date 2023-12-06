plugins {
    kotlin("jvm") version "1.9.20"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
    application
}

group = "be.dbproject"
version = "1.0"

repositories {
    mavenCentral()
}

javafx {
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
}

dependencies {
    implementation("org.openjfx:javafx-controls:17")
    implementation("org.openjfx:javafx-fxml:17")

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.hibernate:hibernate-core:5.4.23.Final")
    implementation("org.xerial:sqlite-jdbc:3.32.3.2")
    implementation("com.zsoltfabok:sqlite-dialect:1.0")
    implementation("org.controlsfx:controlsfx:11.2.0")
    implementation(kotlin("reflect"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("be.dbproject.ProjectMain")
}
