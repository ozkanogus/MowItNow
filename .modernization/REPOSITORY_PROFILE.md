# Repository Profile

## Baseline

- Spring Boot 3.4.4
- Java 17 toolchain
- Gradle 8.13
- springdoc 2.3.0
- seven mower-domain tests plus one context test
- no CI, container, validation layer, or meaningful operating documentation

The baseline test task passes when Gradle is explicitly pointed to the locally
installed Java 17 toolchain. The application models are mutable Lombok classes,
and parsing accepts invalid dimensions, positions, directions, and commands too
late or silently.

## Target

- Java 21, Spring Boot 4.1.1, Gradle 9.7.1;
- immutable request/value models and explicit validation;
- deterministic API error responses and broader tests;
- Java 21 CI, non-root container image, and complete README.
