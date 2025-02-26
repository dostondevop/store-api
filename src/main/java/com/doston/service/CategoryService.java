package com.doston.service;

import com.doston.dao.CategoryDao;
import com.doston.model.Category;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    public List<Category> listCategory() {
        return categoryDao.getCategories(new Category());
    }

    public Category addCategory(Category category) {
        return categoryDao.addCategory(category);
    }

    public void deleteCategory(int categoryId) {
        categoryDao.deleteCategory(categoryId);
    }

    public Category updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }

    public List<String> listCategoryPaths() {
        List<String> listCategoryPaths = new ArrayList<>();
        List<Category> categories = categoryDao.getCategories(new Category());

        for (Category category: categories) {
            if (category.isLast()) {
                String path = category.getPath();
                String replace = path.replace('/', '➡');
                listCategoryPaths.add(replace);
            }
        }
        return listCategoryPaths;
    }

    public int getCategoryIdByPath(String path) {
        return categoryDao.getCategoryIdByPath(path);
    }

    public List<Category> getSubCategories(int categoryId) {
        return categoryDao.getSubCategories(categoryId);
    }

    public List<Category> getMainCategories() {
        List<Category> categories = new ArrayList<>();
        return listCategory().stream().filter(c -> c.getParentId() == 0).toList();
    }
}
