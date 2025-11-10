jdeps --ignore-missing-deps -q --recursive  --multi-release 21 --print-module-deps --class-path './target/deps/*'  './target/web-automation-1.0-ASNAPSHOT.jar' > deps.info

jlink --add-modules $(cat deps.info) --strip-debug --compress 2 --no-header-files --no-man-pages --output ./target/helix-jre