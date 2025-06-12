// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Esto afecta a los plugins y al classpath de buildscript
buildscript {
    configurations.classpath {
        resolutionStrategy {
            force("com.squareup:javapoet:1.13.0")
        }
    }
}

// Y esto a todas las demás configuraciones (módulo app, tests, etc.)
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    //id("com.google.gms.google-services") version "4.4.2" apply false
}