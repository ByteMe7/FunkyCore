# Funky Core API
Funky Core is the depency that all mods from FunkyStudios stem from

## Installation

### Modify your `build.gradle` :
```gradle
repositoritories {
  maven {
    url "https://maven.pkg.github.com/ByteMe7/FunkyCore"
  }
}
```

```gradle
dependencies {
  modImplementation "net.funkystudios.funkycore.funky-core:${version}"
}
```

### Modify you `fabric.mod.json` :

```json
"depends": {
  "funky-core" : version
}
```
