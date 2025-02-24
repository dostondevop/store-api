<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/7/2024
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chat-sidebar {
            background-color: #f8f9fa;
            border-right: 1px solid #e0e0e0;
            height: 100vh;
        }
        .chat-content {
            padding: 20px;
            height: 100vh;
            overflow-y: auto;
        }
        /* Har bir ustunning kengligini bir xil qilish */
        .table td, .table th {
            width: 150px; /* Ustun kengligini kerakli o'lchamga o'zgartirishingiz mumkin */
            max-width: 180px;
            overflow: auto;
            white-space: nowrap;
        }
        a {
            color: inherit; /* Link rangini ota element rangiga moslashtiradi */
            text-decoration: none; /* Tag chiziqni olib tashlaydi */
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">


        <!-- Left Sidebar Container -->
        <div class="col-md-3 col-lg-2 chat-sidebar p-3">
            <!--            <h5></h5>-->
            <ul class="list-group">
                <li class="list-group-item" style="background: yellow"><a href="/admin/category-list">Category</a></li>
                <li class="list-group-item"><a href="/admin/product-list">Product</a></li>
                <li class="list-group-item"><a href="/admin/cart-list">Cart</a></li>
                <li class="list-group-item"><a href="/admin/order-list">Order</a></li>
            </ul>
        </div>



        <!-- Right Chat Content Container -->
        <div class="col-md-9 col-lg-10 chat-content">
            <div class="container pb-3" >
                <button class="btn btn-success" type="button" style="${fn:contains(privileges, 'Create') ? '' : 'display: none;'}" aria-pressed="true" data-bs-toggle="modal" data-bs-target="#addCategoryModal">ADD</button>
            </div>

            <table class="table table-bordered table-responsive">
                <caption>List of categories</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">ParentId</th>
                    <th scope="col">ParentName</th>
                    <th scope="col">CreatedDate</th>
                    <th scope="col">UpdatedDate</th>
                    <th scope="col">CreatedBy</th>
                    <th scope="col">UpdatedBy</th>
                    <th scope="col">Last</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categoryList}" var="category">
                    <tr>
                        <th scope="row">${category.getId()}</th>
                        <td>${category.getName()}</td>
                        <td>${category.getParentId()}</td>
                        <td>${category.getParentName()}</td>
                        <td>${category.getCreatedDate()}</td>
                        <td>${category.getUpdatedDate()}</td>
                        <td>${category.getCreatedBy()}</td>
                        <td>${category.getUpdatedBy()}</td>
                        <td>${category.isLast()}</td>
                        <td><a href="/admin/delete-category?id=${category.getId()}" class="btn btn-danger" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Delete') ? '' : 'display: none;'}">Delete</a>
                            <a href="#" class="btn btn-warning" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Update') ? '' : 'display: none;'}" data-bs-toggle="modal" data-bs-target="#updateModal"
                               onclick="setModalValues(${category.getId()}, '${category.getName()}', ${category.getParentId()})">Update</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


        <!-- Modal -->
        <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addCategoryModalLabel">Add Category</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addCategoryForm" action="/admin/add-category" method="post">
                            <div class="mb-3">
                                <label for="name" class="form-label">Name</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="Enter category name" required>
                            </div>
                            <div class="mb-3">
                                <label for="parentId" class="form-label">Parent ID</label>
                                <input type="number" class="form-control" id="parentId" name="parentId" placeholder="Enter parent category ID">
                            </div>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Update Modal -->
<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateModalLabel">Update Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="updateForm" action="/admin/update-category" method="post">
                    <input type="hidden" id="categoryId" name="id">
                    <div class="mb-3">
                        <label for="categoryName" class="form-label">Name</label>
                        <input type="text" class="form-control" id="categoryName" name="name">
                    </div>
                    <div class="mb-3">
                        <label for="categoryParentId" class="form-label">ParentId</label>
                        <input type="number" class="form-control" id="categoryParentId" name="parentId">
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Set values in the modal
    function setModalValues(id, name, parentId) {
        document.getElementById('categoryId').value = id;
        document.getElementById('categoryName').value = name;
        document.getElementById('categoryParentId').value = parentId;
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
