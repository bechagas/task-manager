package com.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "projects")
public class Project {

   @DatabaseField(generatedId = true)
   private int id;

   @DatabaseField(canBeNull = false, width = 100)
   private String name;

   @DatabaseField(width = 500)
   private String description;

   public enum ProjectStatus {
       ACTIVE, ARCHIVED, CONCLUDED
   }

   @DatabaseField(defaultValue = "ACTIVE", unknownEnumName = "ACTIVE")
   private ProjectStatus status;

   @DatabaseField
   private Date createdAt;

   @DatabaseField
   private Date updatedAt;

   public Project(){
       this.createdAt = new Date();
       this.updatedAt = new Date();
       this.status = ProjectStatus.ACTIVE;
   }

   public Project(String name){
       this();
       this.name = name;
   }

   public Project(String name, String description){
       this(name);
       this.description = description;
   }

   public int getId() { return id; }
   public void setId(int id) { this.id = id; }

   public String getname() { return name; }
   public void setname(String name) { this.name = name; }

   public String getDescription() { return description; }
   public void setDescription(String description) { this.description = description; }

   public ProjectStatus getStatus() { return status; }
   public void setStatus(ProjectStatus status) { this.status = status; }

   public Date getCreatedAt() { return createdAt; }
   public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

   public Date getUpdatedAt() { return updatedAt; }
   public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

   public void triggerUpdate() {
       this.updatedAt = new Date();
   }
}