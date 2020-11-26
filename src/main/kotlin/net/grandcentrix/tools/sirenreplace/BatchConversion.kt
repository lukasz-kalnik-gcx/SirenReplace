package net.grandcentrix.tools.sirenreplace

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.nio.file.Files
import java.nio.file.Paths

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
    Files.list(Paths.get(path))
        .filter(Files::isRegularFile)
        .filter { it.endsWith(".json") }
        .forEach { filePath ->
            val reader = Files.newBufferedReader(filePath)
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
                val writer = Files.newBufferedWriter(filePath)
                writer.use { it.write(modifiedFileContents) }
            }
        }
}
