import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("dev.kikugie.loom-back-compat")
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("dev.deftu.gradle.bloom") version "0.2.0"
    id("me.modmuss50.mod-publish-plugin") version "1.1.0"
}

val modid = property("mod.id") as String
val modname = property("mod.name") as String
val modversion = property("mod.version") as String
val mcversion = property("minecraft_version") as String
val versionrange = property("minecraft_version_range") as String
val loaderversion = property("loader_version") as String
val oneconfigVersion = property("oneconfig_version") as String

// Minecraft 26+ targets Java 25; 1.21.x targets Java 21.
val javaVersion = if (stonecutter.eval(stonecutter.current.version, ">=26")) 25 else 21

base {
    archivesName.set("$modid-$modversion+$mcversion")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
    maven("https://maven.parchmentmc.org") {
        content { includeGroup("org.parchmentmc") }
    }
    maven("https://maven.deftu.dev/releases") {
        content { includeGroup("dev.deftu") }
    }
    maven("https://maven.fabricmc.net/releases") {
        content { includeGroup("net.fabricmc") }
    }
    //maven("https://maven.terraformersmc.com/releases") {
    maven("https://maven.gnomecraft.net/releases/") {
        content { includeGroup("com.terraformersmc") }
    }
    maven("https://central.sonatype.com/repository/maven-snapshots") {
        content { includeGroup("net.kyori") }
    }
}

stonecutter {
    constants["fabric"] = true
    constants["forge"] = false
    constants["neoforge"] = false
    constants["forge_like"] = false
}

loom {
    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
        runDir = "../../run"
    }

    runConfigs.remove(runConfigs["server"])
}

dependencies {
    minecraft("com.mojang:minecraft:$mcversion")

    val parchmentVersion = findProperty("parchment_version")?.toString()?.takeUnless { it.isBlank() }
    if (stonecutter.eval(stonecutter.current.version, "<26")) {
        mappings(loom.layered {
            officialMojangMappings()
            if (parchmentVersion != null) {
                parchment("org.parchmentmc.data:parchment-$mcversion:$parchmentVersion@zip")
            }
        })
    }

    modImplementation("net.fabricmc:fabric-loader:$loaderversion")
    modImplementation("org.polyfrost.oneconfig:$mcversion-fabric:$oneconfigVersion")

    optionalProp("fabric_api_version") { fabricApi ->
        modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApi")
    }

    implementation("org.polyfrost.oneconfig:commands:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:config:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:config-impl:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:events:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:internal:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:ui:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:utils:$oneconfigVersion")
}

sourceSets {
    main {
        java.srcDir(rootProject.file("src/ducks/java"))
        kotlin.srcDir(rootProject.file("src/ducks/kotlin"))
    }
}

bloom {
    replacement("@MOD_ID@", modid)
    replacement("@MOD_NAME@", modname)
    replacement("@MOD_VERSION@", modversion)
}

tasks.processResources {
    val props = mapOf(
        "mod_id" to modid,
        "mod_name" to modname,
        "mod_version" to modversion,
        "mc_version" to versionrange,
        "minecraft_version_range" to versionrange,
        "loader_version" to loaderversion,
        "java_version" to "JAVA_$javaVersion",
    )

    inputs.properties(props)

    filesMatching(listOf("fabric.mod.json", "mixins.overflowparticles.json")) {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
}

java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

tasks.jar {
    inputs.property("archivesName", base.archivesName)
    exclude("club/sk1er/patcher/**")
    exclude("dev/isxander/particlesenhanced/**")

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}

fun <T> optionalProp(name: String, block: (String) -> T?): T? =
    findProperty(name)?.toString()?.takeUnless { it.isBlank() }?.let(block)

val modrinthMinecraftVersionOverride = mapOf(
    "26.1" to listOf("26.1", "26.1.1", "26.1.2")
)

val modrinthId = listOf("oneconfig.publish.modrinth", "publish.modrinth").firstNotNullOfOrNull { findProperty(it) }?.toString()?.takeIf { it.isNotBlank() }
val modrinthToken = listOf("oneconfig.publish.modrinth.token", "publish.modrinth.token", "modrinth.token").firstNotNullOfOrNull { findProperty(it) }?.toString()?.takeIf { it.isNotBlank() }
val minecraftVersion = modrinthMinecraftVersionOverride[mcversion] ?: listOf(mcversion)
val publishJarTaskName = if ("remapJar" in tasks.names) "remapJar" else "jar"
val changelogs = rootProject.file("CHANGELOG.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."

publishMods {
    file = tasks.named<AbstractArchiveTask>(publishJarTaskName).flatMap { it.archiveFile }

    displayName = modversion
    version = "v$modversion"
    changelog = changelogs
    type = STABLE

    modLoaders.add("fabric")

    dryRun = modrinthId == null || modrinthToken == null

    if (modrinthId != null) {
        modrinth {
            projectId = modrinthId
            accessToken = modrinthToken.orEmpty()

            minecraftVersions.addAll(minecraftVersion)

            requires("oneconfig")
            requires("fabric-language-kotlin")
        }
    }
}
