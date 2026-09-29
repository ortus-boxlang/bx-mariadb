# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

* * *

## [Unreleased]

### 🔄 Changed

- Performance defaults supported by MariaDB Connector/J (`prepStmtCacheSize`, `cachePrepStmts`, `useServerPrepStmts`, `useLocalSessionState`) are now default JDBC URL params (`defaultCustomParams`) instead of default Hikari properties, so they can be overridden via the datasource `custom` struct.
- Removed MySQL-only defaults that MariaDB Connector/J does not support.
- Readme now documents the default connection parameters.
- PR workflow now uses a concurrency group to avoid duplicate runs.

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
