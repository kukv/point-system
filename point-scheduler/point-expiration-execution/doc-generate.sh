#! /bin/bash

set -euo pipefail
cd "$(dirname "${0}")"

java -jar jig-cli-kt.jar \
  --jig.pattern.domain="^.+\\.domain\\.(model|type)\\.(?!.*(\\\$Companion|\\\$\\\$serializer)).+" \
  --directory.classes=build/classes/kotlin/main \
  --directory.resources=build/resources/main \
  --directory.sources=src/main/kotlin
