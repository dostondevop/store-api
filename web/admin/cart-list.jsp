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
    <title>Cart List</title>
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
                <li class="list-group-item"><a href="/admin/category-list">Category</a></li>
                <li class="list-group-item"><a href="/admin/product-list">Product</a></li>
                <li class="list-group-item" style="background: yellow"><a href="/admin/cart-list">Cart</a></li>
                <li class="list-group-item"><a href="/admin/order-list">Order</a></li>
            </ul>
        </div>



        <!-- Right Chat Content Container -->
        <div class="col-md-9 col-lg-10 chat-content">
<%--            <div class="container pb-3">--%>
<%--                <a href="#" class="btn btn-success" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Create') ? '' : 'display: none;'}" data-bs-toggle="modal" data-bs-target="#addCartModal">ADD</a>--%>
<%--            </div>--%>

            <table class="table table-bordered table-responsive">
                <caption>List of carts</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">UserId</th>
                    <th scope="col">UserName</th>
                    <th scope="col">UserPhoneNumber</th>
                    <th scope="col">UserEmail</th>
                    <th scope="col">ProductId</th>
                    <th scope="col">ProductName</th>
                    <th scope="col">ProductPrice</th>
                    <th scope="col">ProductImages</th>
                    <th scope="col">ProductParams</th>
                    <th scope="col">ProductColours</th>
                    <th scope="col">ProductDescription</th>
                    <th scope="col">ProductDiscount</th>
                    <th scope="col">ProductFromDelivery</th>
                    <th scope="col">ProductToDelivery</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">CreatedDate</th>
                    <th scope="col">UpdatedDate</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cartList}" var="cart">
                    <tr>
                        <th scope="row">${cart.getId()}</th>
                        <td>${cart.getUserId()}</td>
                        <td>${cart.getUserName()}</td>
                        <td>${cart.getUserPhoneNumber()}</td>
                        <td>${cart.getUserEmail()}</td>
                        <td>${cart.getProductId()}</td>
                        <td>${cart.getProductName()}</td>
                        <td>${cart.getProductPrice()}</td>
                        <td>${cart.getProductImages()}</td>
                        <td>${cart.getProductParams()}</td>
                        <td>${cart.getProductColours()}</td>
                        <td>${cart.getProductDescription()}</td>
                        <td>${cart.getProductDiscount()}</td>
                        <td>${cart.getProductFromDelivery()}</td>
                        <td>${cart.getProductToDelivery()}</td>
                        <td>${cart.getQuantity()}</td>
                        <td>${cart.getCreatedDate()}</td>
                        <td>${cart.getUpdatedDate()}</td>
<%--                        <td><a href="#" class="btn btn-danger" role="button" aria-pressed="true" data-id="${cart.getId()}" data-bs-toggle="modal" data-bs-target="#deleteModal" style="${fn:contains(privileges, 'Delete') ? '' : 'display: none;'}">Delete</a>--%>
<%--                            <a href="#" class="btn btn-warning" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Update') ? '' : 'display: none;'}">Update</a></td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="addCartModal" tabindex="-1" aria-labelledby="addCartModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addCartModalLabel">Add Cart</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addCartForm" action="/admin/add-cart" method="post">
                            <div class="mb-3">
                                <label for="userId" class="form-label">User Id</label>
                                <input type="number" class="form-control" id="userId" name="userId" placeholder="Enter user ID" required>
                            </div>
                            <div class="mb-3">
                                <label for="productId" class="form-label">Product ID</label>
                                <input type="number" class="form-control" id="productId" name="productId" placeholder="Enter product ID">
                            </div>
                            <div class="mb-3">
                                <label for="quantity" class="form-label">Quantity</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" placeholder="Enter quantity">
                            </div>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal Page -->
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel">Confirmation</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Do you want to remove this data?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                        <!-- Ha tugmasi, o'chirish uchun forma bilan -->
                        <form id="deleteForm" action="/admin/delete-cart" method="get">
                            <input type="hidden" name="id" id="productIdToDelete" value="">
                            <button type="submit" class="btn btn-danger">Yes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>

<script>
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-id');
            document.getElementById('productIdToDelete').value = productId;
        });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
