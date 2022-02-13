# xdg-utils-java [![Java CI with Gradle](https://github.com/anyicomplex/xdg-utils-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/anyicomplex/xdg-utils-java/actions/workflows/gradle.yml) [![Publish to Maven Central](https://github.com/anyicomplex/xdg-utils-java/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/anyicomplex/xdg-utils-java/actions/workflows/gradle-publish.yml) ![License](https://img.shields.io/github/license/anyicomplex/xdg-utils-java)
**NOTE: This is NOT an implementation!** [xdg-utils](https://www.freedesktop.org/wiki/Software/xdg-utils/) wrapper for Java, only working on Linux.

## Current State
| Supported feature | Version |  State   |
|:-----------------:|:-------:|:--------:|
| xdg-desktop-icon  |  1.1.3  | Complete |
| xdg-desktop-menu  |  1.1.3  | Complete |
|     xdg-email     |  1.1.3  | Complete |
| xdg-icon-resource |  1.1.3  | Complete |
|     xdg-mime      |  1.1.3  | Complete |
|     xdg-open      |  1.1.3  | Complete |
|  xdg-screensaver  |  1.1.3  | Complete |
|   xdg-settings    |  1.1.3  | Complete |

## Add this repo to your project dependency
Step 1. Add the Maven Central repository to your build file
```groovy
allprojects {
	repositories {
		...
		mavenCentral()
	}
}
```

Step 2. Add the dependency
```groovy
dependencies {
    implementation 'io.github.anyicomplex:xdg-utils-java:1.1.3-beta1'
}
```

## System properties
`com.anyicomplex.xdg.utils.scriptPath`: the specific path to load script.
