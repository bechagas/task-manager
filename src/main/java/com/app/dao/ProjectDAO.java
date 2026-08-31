package com.app.dao;

import com.app.model.Project;
import com.app.database.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class ProjectDAO {
   private Dao<Project, Integer> dao;

   public ProjectDAO() throws SQLException {
       this.dao = DaoManager.createDao(DatabaseManager.getConnection(), Project.class);
   }
  
   // * ===== OPERAÇÕES BÁSICAS CRUD =====

   public List<Project> findAll() throws SQLException {
       return dao.queryForAll();
   }

   public Project findById(int id) throws SQLException {
       return dao.queryForId(id);
   }

   public void create(Project project) throws SQLException {
       dao.create(project);
   }

   public void update(Project project) throws SQLException {
       project.triggerUpdate();
       dao.update(project);
   }

   public void delete(int id) throws SQLException {
       dao.deleteById(id);
   }

   // * ===== CONSULTAS ESPECÍFICAS =====
  
   public List<Project> findByStatus(Project.ProjectStatus status) throws SQLException {
       return dao.queryBuilder()
           .where()
           .eq("status", status)
           .query();
   }

   public List<Project> findByName(String name) throws SQLException {
       return dao.queryBuilder()
           .where()
           .eq("name", "%" + name + "%")
           .query();
   }

   public List<Project> findAllOrderedByCreatedAt() throws SQLException {
       return dao.queryBuilder()
           .orderBy("createdAt", false)
           .query();
   }

   // * ===== OPERAÇÕES DE ATUALIZAÇÃO ESPECÍFICAS =====

   public void UpdateName(int projectId, String newName) throws SQLException {
       UpdateBuilder<Project, Integer> updateBuilder = dao.updateBuilder();

       updateBuilder
           .updateColumnValue("name", newName)
           .updateColumnValue("updatedAt", new Date())
           .where()
           .eq("id", projectId);
      
       updateBuilder.update();
   }

   public void UpdateDescription(int projectId, String newDescription) throws SQLException {
       UpdateBuilder<Project, Integer> updateBuilder = dao.updateBuilder();

       updateBuilder
           .updateColumnValue("description", newDescription)
           .updateColumnValue("updatedAt", new Date())
           .where()
           .eq("id", projectId);

       updateBuilder.update();
   }

   public void updateStatus(int projectId, Project.ProjectStatus newStatus) throws SQLException {
       UpdateBuilder<Project, Integer> updateBuilder = dao.updateBuilder();

       updateBuilder
           .updateColumnValue("status", newStatus)
           .updateColumnValue("updatedAt", new Date())
           .where()
           .eq("id", projectId);

       updateBuilder.update();
   }

   // * ===== VERIFICAÇÕES =====

   public boolean exists(int id) throws SQLException {
       return dao.queryForId(id) != null;
   }

   public boolean existsByName(String name) throws SQLException {
       List<Project> projects = dao.queryBuilder()
           .where()
           .eq("name", name)
           .query();
      
       return !projects.isEmpty();
   }

   // * ===== OPERAÇÕES EM MASSA =====

   public void deleteByStatus(Project.ProjectStatus status) throws SQLException {
       dao.delete(
           dao.queryBuilder()
               .where()
               .eq("status", status)
               .query()
       );
   }
}