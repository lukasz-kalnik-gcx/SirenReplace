# SirenReplace

A small tool for moving certain data object within a Siren JSON file. It is meant to run in batch in a directory with JSON files.
It only modifies files where the source object was found. The source object contents is then moved to the defined target object within
the top level Siren `properties` object.

This program is super specialized because it was created for solving some required conversion in BEGA project.
It can however be relatively easily modified to perform other modifications in JSON files. 

## How to run it
The program expects following command line arguments:
```shell script
<path/to/directory/with/json> <source-top-level-array-key> <source-parent-object-siren-class-first-word> <source-parent-object-siren-class-second-word> <source-object-key> <properties-target-object-key>
```

You can also create a run configuration in IntelliJ and enter the arguments there.

Example arguments for the BEGA project:
```shell script
/Users/lukaszkalnik/json entities light type properties features
```
