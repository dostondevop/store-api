package com.doston.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    private int id;
    private String name;
    private int parentId;
    private String parentName;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
    private boolean last;
    private String path;

    public Category(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.parentId = resultSet.getInt("parent_id");
        this.parentName = resultSet.getString("parent_name");
        this.createdDate = resultSet.getDate("created_date");
        this.updatedDate = resultSet.getDate("updated_date");
        this.createdBy = resultSet.getString("created_by");
        this.updatedBy = resultSet.getString("updated_by");
        this.last = resultSet.getBoolean("last");
        this.path = resultSet.getString("path");
    }

    public Category(String name, int parentId, String createdBy) {
        this.name = name;
        this.parentId = parentId;
        this.createdBy = createdBy;
    }

    public Category(int id, String name, int parentId, String updatedBy) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.updatedBy = updatedBy;
    }
}
