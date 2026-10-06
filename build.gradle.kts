@file:Suppress( "UnstableApiUsage", "HasPlatformType")
plugins {
	id( "org.quiltmc.loom" ) version "1.2.3"
//	id( "org.jetbrains.kotlin.jvm" ) version "1.9.0"
	idea
}

repositories {
	mavenCentral()
	maven( "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven" )
	maven( "https://oss.sonatype.org/content/repositories/snapshots" )
	maven( "https://server.bbkr.space/artifactory/libs-release" )
	maven( "https://maven.terraformersmc.com/releases" )
	maven( "https://repsy.io/mvn/enderzombi102/mc" )
	maven( "https://maven.ladysnake.org/releases" )
	maven( "https://maven.saps.dev/minecraft" )
	maven( "https://maven.architectury.dev" )
	maven( "https://maven.nucleoid.xyz" )
	maven( "https://maven.shedaniel.me" )
	maven( "https://jitpack.io" )
	exclusiveContent {
		forRepository { maven( "https://api.modrinth.com/maven" ) }
		filter { includeGroup ( "maven.modrinth" ) }
	}
	exclusiveContent {
		forRepository { flatDir { dirs( "libs" ) } }
		filter { includeGroup( "local" ) }
	}
}

val loaderVersion = "0.14.21"
val qloaderVersion = "0.19.2"
val minecraftVersion = "1.19.2"
ext["minecraftVersion"] = minecraftVersion

typealias ZipFile = java.util.zip.ZipFile
typealias Charset = java.nio.charset.Charset
val mixinsJar = configurations.detachedConfiguration( dependencies.create( "org.quiltmc:quilt-loader:$qloaderVersion" ) )
	.resolve()
	.first()
	.let( ::ZipFile )
	.use { it.getInputStream( it.getEntry( "quilt_installer.json" ) ).readBytes() }
	.toString( Charset.defaultCharset() )
	.split( "\n" )
	.first { line -> "net.fabricmc:sponge-mixin:" in line }
	.trim()
	.let { it.substring( 9 .. it.length - 3 ) }
	.let { configurations.detachedConfiguration( dependencies.create( it ) ) }
	.resolve()
	.first()
	.also { println( "Mixins jar is located at: `~${File.separator}${it.toRelativeString( File( System.getProperty( "user.home" ) ).normalize() )}`" ) }

loom {
	runtimeOnlyLog4j.set( true )

	val config: net.fabricmc.loom.configuration.ide.RunConfigSettings.() -> Unit = {
		runDir = "run"
		isIdeConfigGenerated = true
		vmArg( "-javaagent:\"$mixinsJar\"" )
		vmArg( "-XX:+AllowEnhancedClassRedefinition" )
		vmArg( "-Xmx3G" )
		vmArg( "-ea:com.enderzombi102.elysium" )
		vmArg( "-Dmixin.debug.export=true" )
		vmArg( "-Dlog4j.configurationFile=\"file:///D:/Programming/minecraft/elysium/log4j2.xml\"" )
		vmArg( "-Dloader.disable_forked_guis=true" )
		vmArg( "-Dloader.transform_cache.disable_preload=true" )
		vmArg( "-Dloader.transform_cache.disable_optimised_compression=true" )
		programArg( "--username=player" )
	}
	runConfigs.named( "client" ).configure( config )
	runConfigs.named( "server" ).configure( config )
}

dependencies {
	minecraft( "com.mojang:minecraft:$minecraftVersion" )
	mappings( "net.fabricmc:yarn:$minecraftVersion+build.21:v2" )
	modImplementation( "org.quiltmc:quilt-loader:$qloaderVersion" )

	implementation( "org.jetbrains:annotations:23.0.0" )
	implementation( annotationProcessor( include( "com.github.llamalad7.mixinextras:mixinextras-fabric:0.2.0-rc.5" )!! )!! )
	implementation( include( "org.apfloat:apfloat:1.11.0" )!! )
	implementation( include( "com.fathzer:javaluator:3.0.3" )!! )
	modImplementation( "org.quiltmc.quilted-fabric-api:quilted-fabric-api:4.0.0-beta.30+0.76.0-1.19.2" )
	modImplementation( "maven.modrinth:origins:1.7.1" )
	implementation( include( "com.enderzombi102:EnderLib:0.3.3" )!! )
	modImplementation( "maven.modrinth:USLVyT7V:fZRj04a2" )
	modImplementation( "local:calio:v1.7.0" )
	modImplementation( "local:apoli:624eb4d094" )
	modImplementation( "maven.modrinth:numismatic-overhaul:0.2.8+1.19" )
	modImplementation( "maven.modrinth:goml-reserved:1.6.4+1.19.2" )
	modImplementation( include( "io.github.cottonmc:Jankson-Fabric:4.1.1+j1.2.1" )!! )
	modImplementation( include( "io.github.cottonmc:LibGui:6.4.0+1.19" )!! )
	modImplementation( "maven.modrinth:trinkets:3.4.2" )
	modCompileOnly( "me.shedaniel:RoughlyEnoughItems-api-fabric:9.1.619" )
	modImplementation( "dev.onyxstudios.cardinal-components-api:cardinal-components-api:5.0.2" )
	modImplementation( "me.lucko:fabric-permissions-api:0.2-SNAPSHOT" )
	modImplementation( "maven.modrinth:vanish:zr3pJkB2" )
	modImplementation( "maven.modrinth:slotlink:5.1.3" )

	modRuntimeOnly( "com.terraformersmc:modmenu:4.2.0-beta.2" )
	modRuntimeOnly( "me.shedaniel:RoughlyEnoughItems-fabric:9.1.619" )


	// needed for datapack / modpack
	modImplementation( "local:reach-entity-attributes:2.3.0" )
	modImplementation( "dev.architectury:architectury-fabric:6.5.85" )
	modImplementation( "io.github.ladysnake:PlayerAbilityLib:1.6.0" )
	modImplementation( "local:NotEnoughBreeding:1.19.2-1.0.0-fabric" )
	modImplementation( "maven.modrinth:farmers-delight-fabric:1.3.9" )
	modImplementation( "maven.modrinth:altorigingui:v1.0.0+1.19.2" )
	modImplementation( "maven.modrinth:playeranimator:1.0.2-fabric" )
	modImplementation( "maven.modrinth:spell-power:0.9.12+1.19-fabric" )
	modImplementation( "maven.modrinth:spell-engine:0.9.24+1.19-fabric" )
	modImplementation( "maven.modrinth:epic-knights-shields-armor-and-weapons:CRd9JWXP" )
	modImplementation( "local:archers_arsenal:1.0.1v192" )
	modImplementation( "maven.modrinth:dramatic-doors:1.19.2-2.0.1" )
	modCompileOnly( "maven.modrinth:playerex:3.5.4+1.19.2" )
	compileOnly( project( ":supplementaries", "namedElements" ) )
	modImplementation( "maven.modrinth:owo-lib:0.9.3+1.19" )
	modImplementation( "eu.pb4:player-data-api:0.2.2+1.19.2" )
	modImplementation("maven.modrinth:TaH1eXdZ:fabric-1.19.2-1.4.2")
	modImplementation( "maven.modrinth:geckolib:ATPZfRS1" )

	// Fucking GOML-reserved
	modImplementation( "com.jamieswhiteshirt:rtree-3i-lite:0.3.0" )
	modImplementation( "eu.pb4:polymer-all:0.2.28+1.19.2" )
	modImplementation( "eu.pb4:polymer:0.2.25+1.19.2" )
	modImplementation( "eu.pb4:sgui:1.1.5+1.19.1" )
	modImplementation( "eu.pb4:placeholder-api:2.0.0-pre.1+1.19.2" )
	modImplementation( "eu.pb4:common-protection-api:1.0.0" )
	modImplementation( "eu.pb4:hologram-api:0.2.2+1.19" )
	modImplementation( "fr.catcore:server-translations-api:1.4.18+1.19.2" )

	modImplementation( "dev.latvian.mods:kubejs-fabric:1902.6.0-build.142" )
	modImplementation("me.shedaniel.cloth:cloth-config-fabric:8.2.88") {
		exclude( group="net.fabricmc.fabric-api" )
	}
}

tasks.withType<ProcessResources> {
    inputs.property( "version", project.version )
    inputs.property( "minecraft_version", minecraftVersion )
    inputs.property( "loader_version", loaderVersion )
    filteringCharset = "UTF-8"

    filesMatching( "fabric.mod.json" ) {
        expand(
            "version" to version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion
        )
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set( 17 )
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//	kotlinOptions.jvmTarget = "17"
//	kotlinOptions.languageVersion = "1.9"
//}

java.toolchain.languageVersion.set( JavaLanguageVersion.of( 17 ) )

tasks.withType<Jar> {
	filesMatching( "LICENSE" ) {
		rename { "${it}_$archiveBaseName"}
	}
}

val cleanLogs by tasks.registering {
	description = "Cleans the logs folder"

	shouldRunAfter( tasks.runClient )
	shouldRunAfter( tasks.runServer )

	doFirst {
		// delete all logs
		file( "run/logs" ).listFiles()?.forEach( File::delete )
	}
}
