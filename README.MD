# XrayConfiguration
## Kotlin Multiplatform Library

### Import

#### KMP

```
implementation("io.github.tim06.xray-configuration:xray-configuration:1.0.8")
```

#### Android

```
implementation("io.github.tim06.xray-configuration:xray-configuration-android:1.0.8")
```

#### iOS

Use xcframework from releases

### Code

#### Parse xray uri
```
# Single
io.github.tim06.xrayConfiguration.parser.Parser.parse("xray_uri")
# Multiple
io.github.tim06.xrayConfiguration.parser.Parser.parse(["xray_uri_1", "xray_uri_2"])
```

#### Parse full xray config

```
io.github.tim06.xrayConfiguration.XrayConfiguration.fromJson("full_xray_configuration")
```

##### Configuration to json

```
io.github.tim06.xrayConfiguration.XrayConfiguration.raw()
```