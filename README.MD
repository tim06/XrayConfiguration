# XrayConfiguration

Kotlin Multiplatform Library

### Publish to MavenCentral

1) Registering a Sonatype account as described here: 
   https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
2) Add developer id, name, email and the project url to
   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
3) Add the secrets to `local.properties`:

```
signing.keyId=...
signing.password=...
signing.secretKeyRingFile=...
ossrhUsername=...
ossrhPassword=...
```

4) Run `./gradlew :xray-configuration:publishAllPublicationsToSonatypeRepository`

### Build platform artifacts

#### Android aar

- Run `./gradlew :xray-configuration:assembleRelease`
- Output: `/xray-configuration/build/outputs/aar/xray-configuration-release.aar`

#### JVM jar

- Run `./gradlew :xray-configuration:jvmJar`
- Output: `/xray-configuration/build/libs/xray-configuration-jvm-1.0.jar`

#### iOS Framework

- Run `./gradlew :xray-configuration:linkReleaseFrameworkIosArm64`
- Output: `/xray-configuration/build/bin/iosArm64/releaseFramework/xray-configuration.framework`

#### JS file

- Run `./gradlew :xray-configuration:jsBrowserProductionWebpack`
- Output: `/xray-configuration/build/dist/js/productionExecutable/xray-configuration.js`

#### macOS Framework

- Run `./gradlew :xray-configuration:linkReleaseFrameworkMacosArm64`
- Output: `/xray-configuration/build/bin/macosArm64/releaseFramework/xray-configuration.framework`

#### Linux static library

- Run `./gradlew :xray-configuration:linkReleaseStaticLinuxX64`
- Output: `/xray-configuration/build/bin/linuxX64/releaseStatic/libxray-configuration.a`

#### Windows static library

- Run `./gradlew :xray-configuration:linkReleaseStaticMingwX64`
- Output: `/xray-configuration/build/bin/mingwX64/releaseStatic/libxray-configuration.a`

#### Wasm binary file

- Run `./gradlew :xray-configuration:wasmJsBrowserDistribution`
- Output: `/xray-configuration/build/dist/wasmJs/productionExecutable/xray-configuration-wasm-js.wasm`
