package com.app.database;

import com.app.model.Project;
import com.app.model.Task;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class DatabaseManager {
   private static final String URL = "jdbc:sqlite:task_manager.db";
   private static ConnectionSource connectionSource;
  
   public static ConnectionSource getConnection() throws SQLException {
       if (connectionSource == null) {
           connectionSource = new JdbcConnectionSource(URL);
          
           TableUtils.createTableIfNotExists(connectionSource, Project.class);
           TableUtils.createTableIfNotExists(connectionSource, Task.class);
       }
       return connectionSource;
   }
  
   public static void close() throws SQLException {
       if (connectionSource != null) {
           connectionSource.close();
       }
   }
}