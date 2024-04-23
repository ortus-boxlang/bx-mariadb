/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ortus.boxlang.modules.mariadb;

import java.util.Map;

import ortus.boxlang.runtime.config.segments.DatasourceConfig;
import ortus.boxlang.runtime.dynamic.casters.StringCaster;
import ortus.boxlang.runtime.jdbc.drivers.DatabaseDriverType;
import ortus.boxlang.runtime.jdbc.drivers.GenericJDBCDriver;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.Struct;
import ortus.boxlang.runtime.types.util.StructUtil;

/**
 * The MariaDB JDBC Driver
 * https://mariadb.com/docs/server/connect/programming-languages/java/connect/
 */
public class MariaDBDriver extends GenericJDBCDriver {

	protected static final String				DEFAULT_PROTOCOL			= "";
	protected static final Map<String, String>	AVAILABLE_PROTOCOLS			= Map.of(
	    "failover", "Alias of the loadbalance HA mode",
	    "loadbalance", "Balances read and write load between configured nodes",
	    "replication", "Balances read and write load between configured primary servers. Balances read load between configured replica servers",
	    "sequential", "Provides connection failover between configured nodes"
	);

	/**
	 * Default Hikari Properties For MariaDB Performance
	 * https://cdn.oreillystatic.com/en/assets/1/event/21/Connector_J%20Performance%20Gems%20Presentation.pdf
	 */
	protected static final IStruct				DEFAULT_HIKARI_PROPERTIES	= Struct.of(
	    // This sets the number of prepared statements that the driver will cache per connection
	    "prepStmtCacheSize", 250,
	    // This is the maximum length of a prepared SQL statement that the driver will cache
	    "prepStmtCacheSqlLimit", 2048,
	    // Neither of the above parameters have any effect if the cache is in fact disabled, as it is by default
	    "cachePrepStmts", true,
	    // If available, use it to get a big boost in performance
	    "useServerPrepStmts", true,
	    "useLocalSessionState", true,
	    "rewriteBatchedStatements", true,
	    "cacheResultSetMetadata", true,
	    "cacheServerConfiguration", true,
	    "elideSetAutoCommits", true,
	    "maintainTimeStats", false
	);

	/**
	 * The protocol in use for the jdbc connection
	 */
	protected String							protocol					= DEFAULT_PROTOCOL;

	/**
	 * Constructor
	 */
	public MariaDBDriver() {
		super();
		this.name					= new Key( "Mariadb" );
		this.type					= DatabaseDriverType.MARIADB;
		// org.apache.derby.jdbc.ClientDriver For client connections
		this.driverClassName		= "org.mariadb.jdbc.Driver";
		this.defaultDelimiter		= "&";
		this.defaultCustomParams	= Struct.of();
		this.defaultProperties		= DEFAULT_HIKARI_PROPERTIES;
	}

	@Override
	public String buildConnectionURL( DatasourceConfig config ) {
		// Validate the database
		String database = ( String ) config.properties.getOrDefault( "database", "" );
		if ( database.isEmpty() ) {
			throw new IllegalArgumentException( "The database property is required for the MariaDB JDBC Driver" );
		}

		// Validate the host
		String host = ( String ) config.properties.getOrDefault( "host", "localhost" );
		if ( host.isEmpty() ) {
			host = "localhost";
		}

		// Verify if we have a protocol
		this.protocol = ( String ) config.properties.getOrDefault( "protocol", "" );
		if ( protocol.length() > 0 && !AVAILABLE_PROTOCOLS.containsKey( protocol ) ) {
			throw new IllegalArgumentException(
			    String.format(
			        "The protocol '%s' is not valid for the MariaDB Driver. Available protocols are %s",
			        this.protocol,
			        AVAILABLE_PROTOCOLS.keySet().toString()
			    )
			);
		}

		// Append the : to the protocol if it exists
		if ( protocol.length() > 0 ) {
			protocol += ":";
		}

		// Port
		String port = StringCaster.cast( config.properties.getOrDefault( "port", "3306" ) );
		if ( port.isEmpty() || port.equals( "0" ) ) {
			port = "3306";
		}

		// If the custom parameters are a string, convert them to a struct
		if ( config.properties.get( Key.custom ) instanceof String castedParams ) {
			config.properties.put( Key.custom, StructUtil.fromQueryString( castedParams, this.defaultDelimiter ) );
		}

		// Build the connection URL with no host info
		return String.format(
		    "jdbc:mariadb:%s//%s:%s/%s?%s",
		    protocol,
		    host,
		    port,
		    database,
		    customParamsToQueryString( config )
		);
	}

}
