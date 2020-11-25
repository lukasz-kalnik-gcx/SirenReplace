package net.grandcentrix.tools.sirenreplace

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * You need the Kotest IntelliJ Plugin to run the test.
 */
class SirenReplaceTest : FreeSpec({

    "SirenReplace on file 1 should move `light type` entity to `features`" {
        val inputJsonObject = Json.decodeFromString<JsonObject>(file1)
        val outputJsonObject = Json.decodeFromString<JsonObject>(file2)

        sirenMoveObject(
            inputJsonObject = inputJsonObject,
            sourceTopLevelKey = "entities",
            sourceClass = listOf("light", "type"),
            sourceObjectKey = "properties",
            targetObjectKey = "features"
        ) shouldBe outputJsonObject
    }
})

private const val file1 = """
{
  "actions": [
    {
      "fields": [
        {
          "max": 100,
          "min": 0,
          "name": "brightness",
          "type": "number",
          "value": 3.119
        }
      ],
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/brightness",
      "method": "PATCH",
      "name": "set-brightness-light",
      "type": "application/json"
    },
    {
      "fields": [
        {
          "name": "on",
          "type": "checkbox"
        }
      ],
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/switch",
      "method": "PATCH",
      "name": "switch-light",
      "type": "application/json"
    }
  ],
  "class": [
    "light",
    "properties"
  ],
  "entities": [
    {
      "class": [
        "light",
        "status"
      ],
      "properties": {
        "brightness": 3.119
      },
      "rel": [
        "de.bega.begaconnect://rels/light_status"
      ]
    },
    {
      "class": [
        "light",
        "type"
      ],
      "properties": {
        "colorizable": false,
        "dimmable": true,
        "temperaturable": false
      },
      "rel": [
        "de.bega.begaconnect://rels/light_type"
      ]
    }
  ],
  "links": [
    {
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/properties",
      "rel": [
        "self"
      ]
    },
    {
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984",
      "rel": [
        "de.bega.begaconnect://rels/light-detail"
      ]
    }
  ],
  "properties": {
    "id": "9cecec2e-6102-42a6-b81d-c60486f8a984",
    "name": "Parking lot luminaire B02"
  }
}
"""

private const val file2 = """
{
  "actions": [
    {
      "fields": [
        {
          "max": 100,
          "min": 0,
          "name": "brightness",
          "type": "number",
          "value": 3.119
        }
      ],
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/brightness",
      "method": "PATCH",
      "name": "set-brightness-light",
      "type": "application/json"
    },
    {
      "fields": [
        {
          "name": "on",
          "type": "checkbox"
        }
      ],
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/switch",
      "method": "PATCH",
      "name": "switch-light",
      "type": "application/json"
    }
  ],
  "class": [
    "light",
    "properties"
  ],
  "entities": [
    {
      "class": [
        "light",
        "status"
      ],
      "properties": {
        "brightness": 3.119
      },
      "rel": [
        "de.bega.begaconnect://rels/light_status"
      ]
    }
  ],
  "links": [
    {
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984/properties",
      "rel": [
        "self"
      ]
    },
    {
      "href": "http://localhost/lights/9cecec2e-6102-42a6-b81d-c60486f8a984",
      "rel": [
        "de.bega.begaconnect://rels/light-detail"
      ]
    }
  ],
  "properties": {
    "id": "9cecec2e-6102-42a6-b81d-c60486f8a984",
    "name": "Parking lot luminaire B02",
    "features": {
     "colorizable": false,
     "dimmable": true,
     "temperaturable": false
    }
  }
}
"""