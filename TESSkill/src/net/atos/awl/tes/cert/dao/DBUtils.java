/**
 * 
 */
package net.atos.awl.tes.cert.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author Gauri Chiplunkar
 * 
 */
public class DBUtils {
	@Resource(name = "jdbc/tescert")
	public static DataSource ds;

	@Resource(name = "/queries.properties")
	public static Properties queries;

	static {
		try {
			if (ds == null) {
				Context initContext;
				initContext = new InitialContext();
				Context envContext = (Context) initContext
						.lookup("java:/comp/env");
				ds = (DataSource) envContext.lookup("jdbc/tescert");
			}
			if (queries == null) {
				queries = new Properties();
				InputStream fs = DBUtils.class.getClassLoader().getResourceAsStream("queries.properties");
				queries.load(fs);

			}
		} catch (Exception e) {
			System.out.println("exception is "+e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static String getQuery(String queryId) {
		return queries.getProperty(queryId);
	}
}
