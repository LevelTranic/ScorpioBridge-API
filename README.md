# ScorpioBridge-API

This API is used for interoperation between ScorpioBridge and the adapter.

## Import Dependencies

### Gradle Groovy
```groovy
repositories {
    maven {
        url = "https://repo.repsy.io/mvn/rdb/default"
        name = "tranic-repo"
    }
}

dependencies {
    compileOnly 'one.tranic:scorpio-api:1.1.0'
}

```

### Gradle Kotlin DSL
```kotlin
repositories {
    maven("https://repo.repsy.io/mvn/rdb/default") {
        name = "tranic-repo"
    }
}

dependencies {
    compileOnly("one.tranic:scorpio-api:1.1.0")
}
```

### Maven
```xml
    <repositories>
        <repository>
            <id>tranic-repo</id>
            <url>https://repo.repsy.io/mvn/rdb/default</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>one.tranic</groupId>
            <artifactId>scorpio-api</artifactId>
            <version>1.1.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

### MavenLoaderAPI
```yaml
repository:
  - https://repo.repsy.io/mvn/rdb/default
dependency:
  - one.tranic:scorpio-api:1.1.0
```

## Links
- [Javadoc](https://javadoc.tranic.one/scorpio-bridge-api/)