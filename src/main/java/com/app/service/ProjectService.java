package com.app.service;

import com.app.model.Project;
import com.app.dao.ProjectDAO;

import com.app.exception.ResourceNotFoundException;
import com.app.exception.SystemException;
import java.sql.SQLException;

import java.util.List;


public class ProjectService {
    private ProjectDAO projectDAO;

    public ProjectService() throws SystemException {
        try {
            this.projectDAO = new ProjectDAO();
        } catch (SQLException e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public List<Project> getAllProjects() throws SystemException {
        try {
            return this.projectDAO.findAll();
        } catch (SQLException e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public List<Project> getAllProjectsOrderedByCreatedAt() throws SystemException {
        try {
            return this.projectDAO.findAllOrderedByCreatedAt();
        } catch (Exception e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public Project getProjectById(int id) throws SystemException, ResourceNotFoundException {
        Project project;

        try {
            project = this.projectDAO.findById(id);
        } catch (SQLException e){
            throw new SystemException("[ERROR] Database has failed", e);
        }

        if (project == null){
            throw new ResourceNotFoundException("Project ID not found");
        }
        return project;
    }

    public List<Project> getProjectsByStatus(Project.ProjectStatus status) 
    throws SystemException, ResourceNotFoundException {

        List<Project> projects;

        try {
            projects = this.projectDAO.findByStatus(status);
        } catch (SQLException e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }

        if (projects.isEmpty()){
            throw new ResourceNotFoundException("No project was found with this status");
        }
        return projects;
    }

    public void createProject(Project newProject) throws SystemException {
        try {
            this.projectDAO.create(newProject);
        } catch (Exception e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public void updateProject(int id, Project project) throws SystemException {
        this.getProjectById(id);

        try {
            this.projectDAO.update(project);
        } catch (Exception e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public void deleteProject(int id) throws SystemException {
        this.getProjectById(id);

        try {
            this.projectDAO.delete(id);
        } catch (Exception e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }

    public void deleteProjectsByStatus(Project.ProjectStatus status) throws SystemException {
        try {
            this.projectDAO.deleteByStatus(status);
        } catch (Exception e) {
            throw new SystemException("[ERROR] Database has failed", e);
        }
    }
}
