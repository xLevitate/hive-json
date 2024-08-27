# hive-json
[![](https://jitpack.io/v/xLevitate/hive-json.svg)](https://jitpack.io/#xLevitate/hive-json)

An open-source Java library that allows for simple hashmap caching & storage supporting custom objects without any hassle. The library was made to be using alongside the Spigot API, however it works universally.

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
## Example Usage
Here's an example of how hive-json can be used in your code, in this example I'll be showcasing a simple example of how to store Minecraft player levels using the Spigot API.

```java
public class LevelStorage extends JSONStorage<UUID, Long> {
    public LevelStorage(Plugin plugin) {
        super(plugin.getDataFolder(), "levels.json", UUID.class, Long.class);

        // Load data from file
        load();
    }
}
```

You can now initialize this class and it will automatically create a HashMap of UUID & Long, load data from file if found to that HashMap, and you can run the save() function to save the data to file.

By using the getStorage() function you can access the HashMap and make any changes or get information from there.
