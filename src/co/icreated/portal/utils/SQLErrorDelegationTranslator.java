package co.icreated.portal.utils;

import java.sql.SQLException;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class SQLErrorDelegationTranslator extends SQLErrorCodeSQLExceptionTranslator {
	
	CLogger log = CLogger.getCLogger(SQLErrorDelegationTranslator.class);
	
    @Override
    protected DataAccessException
      customTranslate(String task, String sql, SQLException sqlException) {
    	//log.log(Level.INFO, "SQL:", sql);
    	log.log(Level.INFO, task, sqlException);
        return null;
    }
}