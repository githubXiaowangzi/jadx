import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
	java
	checkstyle
}

val jadxVersion: String by rootProject.extra

group = "io.github.skylot"
version = jadxVersion

dependencies {
	implementation("org.slf4j:slf4j-api:2.0.9")
	compileOnly("org.jetbrains:annotations:24.1.0")

	testImplementation("ch.qos.logback:logback-classic:1.4.11")
	testImplementation("org.hamcrest:hamcrest-library:2.2")
	testImplementation("org.mockito:mockito-core:5.7.0")
	testImplementation("org.assertj:assertj-core:3.24.2")

	testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testCompileOnly("org.jetbrains:annotations:24.1.0")
}

repositories {
	mavenCentral()
	// required for `aapt-proto` and `r8`
	google()
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

tasks {
	compileJava {
		options.encoding = "UTF-8"
	}
	jar {
		manifest {
			attributes("jadx-version" to jadxVersion)
		}
	}
	test {
		useJUnitPlatform()
		maxParallelForks = Runtime.getRuntime().availableProcessors()
		testLogging {
			showExceptions = true
			exceptionFormat = TestExceptionFormat.FULL
			showCauses = true
		}
	}
}
