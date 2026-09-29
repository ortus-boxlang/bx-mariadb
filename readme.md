# ⚡︎ BoxLang Module: MariaDBJDBC Driver

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

This module provides a BoxLang JDBC driver for MariaDB.  This module is part of the BoxLang project.

## Default Connection Parameters

Defaults are appended to the JDBC URL and can be overridden with the datasource `custom` struct.

| Parameter | Default | Purpose |
|-----------|---------|---------|
| `returnMultiValuesGeneratedIds` | `true` | Returns all generated keys on inserts |
| `prepStmtCacheSize` | `250` | Number of prepared statements cached per connection |
| `cachePrepStmts` | `true` | Enables the prepared statement cache |
| `useServerPrepStmts` | `true` | Uses server-side prepared statements |
| `useLocalSessionState` | `true` | Uses local session state instead of extra server round trips |

```javascript
this.datasources[ "myDB" ] = {
    "driver"  : "mariadb",
    "database": "mydb",
    "username": "root",
    "password": "secret",
    "custom"  : {
        "useServerPrepStmts": false,
        "connectTimeout": 5000
    }
};

// Or inline
queryExecute( "SELECT 1", [], {
    "datasource": { "driver": "mariadb", "database": "mydb", "username": "root", "password": "secret" }
} );
```

## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com).  Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more.  If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

 > "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
