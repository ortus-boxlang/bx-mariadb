# bx-mariadb

```
|:------------------------------------------------------:|
| ⚡︎ B o x L a n g ⚡︎
| Dynamic : Modular : Productive
|:------------------------------------------------------:|
```

<blockquote>
	Copyright Since 2023 by Ortus Solutions, Corp
	<br>
	<a href="https://www.boxlang.io">www.boxlang.io</a> |
	<a href="https://www.ortussolutions.com">www.ortussolutions.com</a>
</blockquote>

<p>&nbsp;</p>

This module provides a BoxLang JDBC driver for MariaDB, enabling integration between BoxLang applications and MariaDB databases through BoxLang's `queryExecute()` and datasource management.

## Features

- 🚀 **High Performance**: Built on the `org.mariadb.jdbc:mariadb-java-client` driver with performance-tuned connection defaults
- ⚙️ **Overridable Defaults**: Every default is a JDBC URL parameter you can override through `custom`
- 🔄 **BoxLang Integration**: Native support for `queryExecute()` and datasource definitions
- ⚖️ **HA Protocols**: Supports the `failover`, `loadbalance`, `replication` and `sequential` connection protocols
- ⚡ **Zero Configuration**: Works out of the box with a host, database and credentials

## Installation

### Via CommandBox (Recommended)

```bash
box install bx-mariadb
```

### Via BoxLang Module Installer

```bash
# Into the BoxLang HOME
install-bx-module bx-mariadb

# Or a local folder
install-bx-module bx-mariadb --local
```

## Quick Start

Once installed, define a datasource and use it:

```javascript
// Application.bx
this.datasources[ "myDB" ] = {
    "driver"  : "mariadb",
    "host"    : "localhost",
    "port"    : 3306,
    "database": "mydb",
    "username": "root",
    "password": "secret"
};

// Use it in your code
result = queryExecute( "SELECT 1 AS test", [], { "datasource": "myDB" } );
```

## Configuration Examples

See [BoxLang's Defining Datasources](https://boxlang.ortusbooks.com/boxlang-language/syntax/queries#defining-datasources) documentation for full examples on where and how to construct a datasource connection pool.

### Application-Level Datasource

```javascript
this.datasources[ "myDB" ] = {
    "driver"  : "mariadb",
    "host"    : "db.example.com",
    "port"    : 3306,
    "database": "mydb",
    "username": "app",
    "password": "secret"
};
```

### Inline Datasource

Define the datasource directly in the `queryExecute()` options:

```javascript
result = queryExecute(
    "SELECT * FROM users WHERE id = ?",
    [ 1 ],
    {
        "datasource": {
            "driver"  : "mariadb",
            "host"    : "localhost",
            "database": "mydb",
            "username": "root",
            "password": "secret"
        }
    }
);
```

### Protocols

Set `protocol` to use a high availability connection mode. Valid values are `failover` (alias of `loadbalance`), `loadbalance`, `replication` and `sequential`. Any other value throws an error.

```javascript
this.datasources[ "haDB" ] = {
    "driver"  : "mariadb",
    "protocol": "sequential",
    "host"    : "db.example.com",
    "database": "mydb",
    "username": "app",
    "password": "secret"
};
```

## Default Connection Parameters

Every default below is appended to the JDBC URL as a query string parameter. Because they are URL parameters and not fixed pool properties, you can override any of them with the `custom` struct.

Only options supported by MariaDB Connector/J are included.

| Parameter | Default | Purpose |
|-----------|---------|---------|
| `returnMultiValuesGeneratedIds` | `true` | Returns all generated keys on inserts |
| `prepStmtCacheSize` | `250` | Number of prepared statements the driver caches per connection |
| `cachePrepStmts` | `true` | Enables the prepared statement cache |
| `useServerPrepStmts` | `true` | Uses server-side prepared statements for a large performance boost |
| `useLocalSessionState` | `true` | Uses local session state instead of extra round trips to the server |

### Overriding Defaults

Add any [MariaDB Connector/J parameter](https://mariadb.com/docs/server/connect/programming-languages/java/connect/) to `custom`. Your values win over the defaults, and the remaining defaults still apply.

```javascript
this.datasources[ "myDB" ] = {
    "driver"  : "mariadb",
    "database": "mydb",
    "username": "root",
    "password": "secret",
    "custom"  : {
        // Override a default
        "useServerPrepStmts": false,
        // Add your own
        "connectTimeout": 5000
    }
};
```

`custom` can also be a query string:

```javascript
"custom": "useServerPrepStmts=false&connectTimeout=5000"
```

## Usage Examples

### Basic Database Operations

```javascript
// Create a table
queryExecute( "
    CREATE TABLE IF NOT EXISTS users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
", [], { "datasource": "myDB" } );

// Insert data
queryExecute(
    "INSERT INTO users ( name, email ) VALUES ( ?, ? )",
    [ "John Doe", "john@example.com" ],
    { "datasource": "myDB" }
);

// Query data
users = queryExecute(
    "SELECT * FROM users WHERE email = ?",
    [ "john@example.com" ],
    { "datasource": "myDB" }
);

// Update data
queryExecute(
    "UPDATE users SET name = ? WHERE id = ?",
    [ "John Smith", 1 ],
    { "datasource": "myDB" }
);
```

### Working with Transactions

```javascript
transaction {
    queryExecute(
        "INSERT INTO users ( name, email ) VALUES ( ?, ? )",
        [ "User 1", "user1@test.com" ],
        { "datasource": "myDB" }
    );
    queryExecute(
        "INSERT INTO users ( name, email ) VALUES ( ?, ? )",
        [ "User 2", "user2@test.com" ],
        { "datasource": "myDB" }
    );
}
```

## Development

### Prerequisites

- Java 21+
- BoxLang Runtime (the build targets 1.3.0, see `gradle.properties`)
- Gradle (wrapper included)

### Building from Source

```bash
# Clone the repository
git clone https://github.com/ortus-boxlang/bx-mariadb.git
cd bx-mariadb

# Build the module
./gradlew build

# Run tests
./gradlew test

# Create the module structure for local testing
./gradlew createModuleStructure
```

### Project Structure

```
bx-mariadb/
├── src/
│   ├── main/
│   │   ├── bx/
│   │   │   └── ModuleConfig.bx          # Module configuration
│   │   ├── java/
│   │   │   └── ortus/boxlang/modules/
│   │   │       └── mariadb/
│   │   │           └── MariaDBDriver.java  # JDBC driver implementation
│   │   └── resources/
│   └── test/
│       └── java/                        # Unit tests
├── build.gradle                         # Build configuration
├── box.json                             # ForgeBox module manifest
└── readme.md                            # This file
```

### Testing

```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "MariaDBDriverTest"
```

### Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for your changes
5. Ensure all tests pass (`./gradlew test`)
6. Format your code (`./gradlew spotlessApply`)
7. Commit your changes (`git commit -m 'Add amazing feature'`)
8. Push to the branch (`git push origin feature/amazing-feature`)
9. Open a Pull Request

## Troubleshooting

### Common Issues

#### Database property is required

```
The database property is required. Ensure your datasource configuration includes:
"database": "mydb"
```

#### Invalid protocol

```
The protocol 'x' is not valid for the MariaDB Driver. Available protocols are [failover, loadbalance, replication, sequential]
```

## Resources

- **Documentation**: [BoxLang Database Guide](https://boxlang.ortusbooks.com/boxlang-language/syntax/queries)
- **MariaDB Connector/J**: [Documentation](https://mariadb.com/docs/server/connect/programming-languages/java/connect/)
- **Issues & Support**: [GitHub Issues](https://github.com/ortus-boxlang/bx-mariadb/issues)
- **ForgeBox**: [bx-mariadb Package](https://forgebox.io/view/bx-mariadb)

## Changelog

See [changelog.md](changelog.md) for a complete list of changes and version history.

## License

Licensed under the Apache License, Version 2.0. See [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) for details.

## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com). Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more. If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

> "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
