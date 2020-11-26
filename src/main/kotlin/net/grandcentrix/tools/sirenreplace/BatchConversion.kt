package net.grandcentrix.tools.sirenreplace

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.File
import kotlin.system.measureTimeMillis

@ExperimentalSerializationApi
fun main(args: Array<String>) {
    batchSirenMoveObject(
        path = args[0],
        sourceTopLevelKey = args[1],
        sourceClass = listOf(args[2], args[3]),
        sourceObjectKey = args[4],
        targetObjectKey = args[5]
    )
}

@ExperimentalSerializationApi
fun batchSirenMoveObject(
    path: String,
    sourceTopLevelKey: String,
    sourceClass: List<String>,
    sourceObjectKey: String,
    targetObjectKey: String
) {
    var openFileCounter = 0
    var modifiedFileCounter = 0

    val encodingFormat = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    val elapsedTime = measureTimeMillis {
        val files = File(path).listFiles() ?: return
        files.filter { it.path.endsWith(".json") }
            .forEach { file ->
                ++openFileCounter
                val reader = file.bufferedReader()
                val fileContents = reader.use { it.readText() }
                val inputJsonObject = Json.decodeFromString<JsonObject>(fileContents)

                // Convert the file contents
                val modifiedJsonObject = sirenMoveObject(
                    inputJsonObject,
                    sourceTopLevelKey,
                    sourceClass,
                    sourceObjectKey,
                    targetObjectKey
                )

                // If the modifiedJsonObject was null then there was nothing to convert -> skip it
                if (modifiedJsonObject != null) {
                    ++modifiedFileCounter
                    val modifiedFileContents = encodingFormat.encodeToString(modifiedJsonObject)
                    val writer = file.bufferedWriter()
                    writer.use { it.write(modifiedFileContents) }
                }
            }
    }
    println("SirenReplace modified $modifiedFileCounter files out of $openFileCounter in $elapsedTime ms")
}
