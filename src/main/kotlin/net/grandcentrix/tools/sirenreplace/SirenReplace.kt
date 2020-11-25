package net.grandcentrix.tools.sirenreplace

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

private const val SIREN_CLASS = "class"
private const val SIREN_PROPERTIES = "properties"

/**
 * Moves a [Siren](https://github.com/kevinswiber/siren) object to top level `properties`.
 *
 * For example usage see SirenReplaceTest.
 *
 * [inputJsonObject] is the main Siren JSON object in which the move is being performed.
 * [sourceTopLevelKey] is the key of the top level JSON element (must be an array) containing the JSON object which will
 * be moved.
 * [sourceClass] is the Siren `class` of the sub-object containing the object to be moved.
 * [sourceObjectKey] is the key of the object to be moved.
 * [targetObjectKey] is the new key (inside the top level `properties` object) where the contents of the object should
 * be moved.
 *
 * @return main Siren JSON object with the data moved inside it or `null` if the source object was not found.
 */
fun sirenMoveObject(
    inputJsonObject: JsonObject,
    sourceTopLevelKey: String,
    sourceClass: List<String>,
    sourceObjectKey: String,
    targetObjectKey: String
): JsonObject? {
    val topLevelArray = (inputJsonObject[sourceTopLevelKey] as JsonArray?)

    // We have to wrap the sourceClass in a JsonArray containing JsonPrimitives
    // to be able to later compare it with the parsed sirenClass
    val searchedSourceClass = JsonArray(sourceClass.map { JsonPrimitive(it) })
    val sourceParentObject = topLevelArray?.find {
        val sirenClass = (it as JsonObject)[SIREN_CLASS]
        (sirenClass as JsonArray?)?.containsAll(searchedSourceClass) == true
    }
    sourceParentObject as JsonObject?
    // If we didn't find the source object then just return null
    val sourceObject = sourceParentObject?.get(sourceObjectKey) ?: return null

    // Move the sourceObject to top level `properties` object
    // using targetObjectKey as its key
    val topLevelProperties = (inputJsonObject[SIREN_PROPERTIES] as JsonObject?)
    topLevelProperties?.toMutableMap()?.set(targetObjectKey, sourceObject)

    return inputJsonObject
}

