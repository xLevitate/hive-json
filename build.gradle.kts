plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.levitate"
version = "2.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.moshi:moshi-adapters:1.15.1")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("hive-json")
                description.set("A Java library that makes it simple to store hashmaps of data using JSON.")
            }
        }
    }

    repositories {
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
    }
}