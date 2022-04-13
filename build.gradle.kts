import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.androidGradle) apply false
    alias(libs.plugins.androidKotlin) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint(libs.versions.ktlint.get())
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(libs.versions.ktlint.get())
    }

    format("prettier") {
        target("**/*.md", "**/*.json", "**/*.xml")
        targetExclude(".idea/**")
        prettier(
            mapOf(
                "prettier" to libs.versions.prettier.get(),
                "@prettier/plugin-xml" to libs.versions.prettierPluginXml.get()
            )
        ).config(mapOf("xmlWhitespaceSensitivity" to "ignore"))
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = true
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.jvm.get()
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = libs.versions.jvm.get()
}
