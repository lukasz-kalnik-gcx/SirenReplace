package net.grandcentrix.tools.sirenreplace

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.File

fun main(args: Array<String>) {
    batchSirenMoveObject(
        path = args[0],
        sourceTopLevelKey = args[1],
        sourceClass = listOf(args[2], args[3]),
        sourceObjectKey = args[4],
        targetObjectKey = args[5]
    )
}

fun batchSirenMoveObject(
    path: String,
    sourceTopLevelKey: String,
    sourceClass: List<String>,
    sourceObjectKey: String,
    targetObjectKey: String
) {
    val files = File(path).listFiles() ?: return
    files.filter { it.path.endsWith(".json") }
        .forEach { file ->
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

            if (modifiedJsonObject != null) {
                val modifiedFileContents = Json.encodeToString(modifiedJsonObject)
                val writer = file.bufferedWriter()
                writer.use { it.write(modifiedFileContents) }
            }
        }
}
