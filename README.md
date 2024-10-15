# hive-json
[![](https://jitpack.io/v/xLevitate/hive-json.svg)](https://jitpack.io/#xLevitate/hive-json)

An open-source Java library that allows for simple hashmap caching & storage supporting custom objects without any hassle. The library was made to be using alongside the Spigot API, however it works universally.

## Example Usage
Here's an example of how hive-json can be used in your code, in this example I'll be showcasing a simple example of how to store Minecraft player levels using the Spigot API.

```java
public class LevelStorage extends JSONStorage<UUID, Long> {
    public LevelStorage(Plugin plugin) {
        super(plugin.getDataFolder(), "levels.json", UUID.class, Long.class);

        // Load data from file
        load();
    }

    public long getLevel(UUID uuid) {
        return getStorage().getOrDefault(uuid, 1L);
    }

    public void changeLevel(UUID uuid, long newLevel) {
        update(uuid, l -> l = newLevel);
    }
}
```

You can now initialize this class and it will automatically create a HashMap of UUID & Long, load data from file if found to that HashMap, and you can run the save() function to save the data to file.

By using the getStorage() function you can access the HashMap and make any changes or get information from there. Additionally, you can use the update() function which allows you to modify values without needing to directly access the HashMap.

## Installation
hive-json can be added to your project using the JitPack repository, here's an example of how that can be done.

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.xLevitate:hive-json:1.1.0'
}
```

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.xLevitate</groupId>
        <artifactId>hive-json</artifactId>
        <version>1.1.0</version>
    </dependency>
</dependencies>
```

Additionally, you can add it using the library file, but that is only advised if you're having issues with Maven/Gradle.
