# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

* * *

## [Unreleased]

### 🐛 Fixed

- `downloadBoxLang` now falls back to the stable BoxLang release jar when no snapshot exists for the configured `boxlangVersion`. BoxLang only publishes snapshots for its next dev version, so a `development` build against a released version (e.g. `1.17.6`) failed with a 404 and no snapshot was published.
- Fixed the circular task dependency (`assemble` -> `shadowJar` -> `build`) that broke `./gradlew build` on Gradle 9, and declared that `createModuleStructure` depends on `jar`.

### 🛠 Build

- CI now runs Gradle through the project wrapper (`./gradlew`) instead of the Gradle setup action and a separately pinned Gradle version.

### 🔄 Changed

- Performance defaults supported by MariaDB Connector/J (`prepStmtCacheSize`, `cachePrepStmts`, `useServerPrepStmts`, `useLocalSessionState`) are now default JDBC URL params (`defaultCustomParams`) instead of default Hikari properties, so they can be overridden via the datasource `custom` struct.
- Removed MySQL-only defaults that MariaDB Connector/J does not support.
- Readme now documents the default connection parameters.
- PR workflow now uses a concurrency group to avoid duplicate runs. The format check job now runs on `ubuntu-latest` (the retired `ubuntu-20.04` runner left the PR workflow queued forever). Removed the CommandBox `format:check` step (no such script exists; `./gradlew spotlessCheck` is the format check).

## [1.2.0] - 2025-06-24

### updated

- Bumps org.mariadb.jdbc:mariadb-java-client from 3.3.3 to 3.5.3.

## [1.1.0] - 2025-05-27

### Added

- Updated to latest module templates

## [1.0.0] - 2024-06-13

- First iteration of this module

[unreleased]: https://github.com/ortus-boxlang/bx-mariadb/compare/v1.2.0...HEAD
[1.2.0]: https://github.com/ortus-boxlang/bx-mariadb/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/ortus-boxlang/bx-mariadb/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/ortus-boxlang/bx-mariadb/compare/71b91f7b2dbc12e7de9fb13a4a769fce8fe4bfdd...v1.0.0
