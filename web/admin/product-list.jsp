
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/7/2024
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
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
                <li class="list-group-item"><a href="/category-list">Category</a></li>
                <li class="list-group-item"><a href="/product-list">Product</a></li>
                <li class="list-group-item"><a href="/cart-list">Cart</a></li>
                <li class="list-group-item"><a href="/order-list">Order</a></li>
            </ul>
        </div>



        <!-- Right Chat Content Container -->
        <div class="col-md-9 col-lg-10 chat-content">
            <div class="container pb-3">
                <button class="btn btn-success" type="button" aria-pressed="true" data-bs-toggle="modal" data-bs-target="#addProductModal">ADD</button>
            </div>

            <table class="table table-bordered table-responsive">
                <caption>List of products</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Images</th>
                    <th scope="col">Params</th>
                    <th scope="col">Colours</th>
                    <th scope="col">Description</th>
                    <th scope="col">Discount</th>
                    <th scope="col">FromDelivery</th>
                    <th scope="col">ToDelivery</th>
                    <th scope="col">CreatedDate</th>
                    <th scope="col">UpdatedDate</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${productList}" var="product">
                        <tr>
                            <th scope="row">${product.getId()}</th>
                            <td>${product.getName()}</td>
                            <td>${product.getPrice()}</td>
                            <td>${product.getImages()}</td>
                            <td>${product.getParams()}</td>
                            <td>${product.getColours()}</td>
                            <td>${product.getDescription()}</td>
                            <td>${product.getDiscount()}</td>
                            <td>${product.getFromDelivery()}</td>
                            <td>${product.getToDelivery()}</td>
                            <td>${product.getCreatedDate()}</td>
                            <td>${product.getUpdatedDate()}</td>
                            <td><a href="#" class="btn btn-danger" role="button" aria-pressed="true">Delete</a>
                                <a href="#" class="btn btn-warning" role="button" aria-pressed="true">Update</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>


        <div class="container my-4">
            <!-- Add Product Modal -->
            <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addProductModalLabel">Add Product</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="productForm" action="/add-product" method="post">
                                <!-- Basic Product Information -->
                                <div class="mb-3">
                                    <label for="name" class="form-label">Product Name</label>
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>

                                <div class="mb-3">
                                    <label for="price" class="form-label">Price</label>
                                    <input type="number" class="form-control" id="price" name="price" required>
                                </div>

                                <div class="mb-3">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="discount" class="form-label">Discount (%)</label>
                                    <input type="number" class="form-control" id="discount" name="discount">
                                </div>

                                <div class="mb-3">
                                    <label for="fromDelivery" class="form-label">From Delivery</label>
                                    <input type="number" class="form-control" id="fromDelivery" name="fromDelivery">
                                </div>

                                <div class="mb-3">
                                    <label for="toDelivery" class="form-label">To Delivery</label>
                                    <input type="number" class="form-control" id="toDelivery" name="toDelivery">
                                </div>

                                <!-- Params Section -->
                                <div class="mb-3">
                                    <button class="btn btn-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#paramsSection" aria-expanded="false" aria-controls="paramsSection">
                                        Params
                                    </button>
                                    <div class="collapse mt-2" id="paramsSection">
                                        <div id="paramsContainer">
                                            <div class="row g-3 mb-2 param-row">
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="paramName[]" placeholder="Parameter Name">
                                                </div>
                                                <div class="col-md-4">
                                                    <select class="form-control" name="paramType[]">
                                                        <option value="text">Text</option>
                                                        <option value="number">Number</option>
                                                        <option value="date">Date</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="paramValue[]" placeholder="Parameter Value">
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-outline-primary" id="addParamBtn">+ Add Parameter</button>
                                    </div>
                                </div>

                                <!-- Images Section -->
                                <div class="mb-3">
                                    <label for="images" class="form-label">Images</label>
                                    <input type="file" class="form-control" id="images" name="images[]" multiple>
                                </div>

                                <!-- Colours Section -->
                                <div class="mb-3">
                                    <button class="btn btn-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#coloursSection" aria-expanded="false" aria-controls="coloursSection">
                                        Colours
                                    </button>
                                    <div class="collapse mt-2" id="coloursSection">
                                        <div id="coloursContainer">
                                            <div class="mb-3 colour-row">
                                                <div class="row g-3">
                                                    <div class="col-md-6">
                                                        <input type="text" class="form-control" name="colourName[]" placeholder="Colour Name">
                                                    </div>
                                                    <div class="col-md-6">
                                                        <input type="file" class="form-control" name="colourImages[]" multiple>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-outline-primary" id="addColourBtn">+ Add Colour</button>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-primary">Add Product</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Add new parameter row dynamically
            document.getElementById('addParamBtn').addEventListener('click', function () {
                const paramsContainer = document.getElementById('paramsContainer');
                const newParamRow = document.createElement('div');
                newParamRow.classList.add('row', 'g-3', 'mb-2', 'param-row');
                newParamRow.innerHTML = `
            <div class="col-md-4">
                <input type="text" class="form-control" name="paramName[]" placeholder="Parameter Name">
            </div>
            <div class="col-md-4">
                <select class="form-control" name="paramType[]">
                    <option value="text">Text</option>
                    <option value="number">Number</option>
                    <option value="date">Date</option>
                </select>
            </div>
            <div class="col-md-4">
                <input type="text" class="form-control" name="paramValue[]" placeholder="Parameter Value">
            </div>
        `;
                paramsContainer.appendChild(newParamRow);
            });

            // Add new colour row dynamically
            document.getElementById('addColourBtn').addEventListener('click', function () {
                const coloursContainer = document.getElementById('coloursContainer');
                const newColourRow = document.createElement('div');
                newColourRow.classList.add('mb-3', 'colour-row');
                newColourRow.innerHTML = `
            <div class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" name="colourName[]" placeholder="Colour Name">
                </div>
                <div class="col-md-6">
                    <input type="file" class="form-control" name="colourImages[]" multiple>
                </div>
            </div>
        `;
                coloursContainer.appendChild(newColourRow);
            });

            // Form submit event listener
            document.getElementById('productForm').addEventListener('submit', function (event) {
                event.preventDefault();
                const formData = new FormData(this);
                const params = [];
                const colours = [];

                // Process params
                const paramNames = formData.getAll('paramName[]');
                const paramTypes = formData.getAll('paramType[]');
                const paramValues = formData.getAll('paramValue[]');
                paramNames.forEach((name, index) => {
                    let value = paramValues[index];
                    if (paramTypes[index] === "number") value = Number(value);
                    if (paramTypes[index] === "date") value = new Date(value).toISOString().split('T')[0];
                    params.push({ [name]: value });
                });

                // Process colours
                const colourNames = formData.getAll('colourName[]');
                const colourImages = formData.getAll('colourImages[]');
                colourNames.forEach((name, index) => {
                    colours.push({
                        [name]: Array.from(colourImages[index].files).map(file => ({ path: file.name })) // Store image names
                    });
                });

                // Continue the result JSON object
                const result = {
                    name: formData.get('name'),
                    price: Number(formData.get('price')),
                    description: formData.get('description'),
                    discount: Number(formData.get('discount')),
                    fromDelivery: Number(formData.get('fromDelivery')),
                    toDelivery: Number(formData.get('toDelivery')),
                    params: params,
                    colours: colours
                };

                // Here you could make an AJAX request to send the result to the server
                console.log(result); // Logging for testing; you can replace with an actual AJAX request

                fetch('/addProduct', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(result)  // JSON formatida yuborish
                });
            });

        </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
            max-width: 220px;
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
            <ul class="list-group">
                <li class="list-group-item"><a href="/admin/category-list">Category</a></li>
                <li class="list-group-item" style="background: yellow"><a href="/admin/product-list">Product</a></li>
                <li class="list-group-item"><a href="/admin/cart-list">Cart</a></li>
                <li class="list-group-item"><a href="/admin/order-list">Order</a></li>
            </ul>
        </div>

        <!-- Right Chat Content Container -->
        <div class="col-md-9 col-lg-10 chat-content">
            <div class="container pb-3">
                <button class="btn btn-success" type="button" aria-pressed="true" style="${fn:contains(privileges, 'Create') ? '' : 'display: none;'}" data-bs-toggle="modal" data-bs-target="#addProductModal">ADD</button>
            </div>

            <table class="table table-bordered table-responsive">
                <caption>List of products</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Images</th>
                    <th scope="col">Params</th>
                    <th scope="col">Colours</th>
                    <th scope="col">Description</th>
                    <th scope="col">Discount</th>
                    <th scope="col">FromDelivery</th>
                    <th scope="col">ToDelivery</th>
                    <th scope="col">CreatedDate</th>
                    <th scope="col">UpdatedDate</th>
                    <th scope="col">CreatedBy</th>
                    <th scope="col">UpdatedBy</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productList}" var="product">
                    <tr>
                        <th scope="row">${product.getId()}</th>
                        <td>${product.getName()}</td>
                        <td>${product.getPrice()}</td>
                        <td>${product.getImages()}</td>
                        <td>${product.getParams()}</td>
                        <td>${product.getColours()}</td>
                        <td>${product.getDescription()}</td>
                        <td>${product.getDiscount()}</td>
                        <td>${product.getFromDelivery()}</td>
                        <td>${product.getToDelivery()}</td>
                        <td>${product.getCreatedDate()}</td>
                        <td>${product.getUpdatedDate()}</td>
                        <td>${product.getCreatedBy()}</td>
                        <td>${product.getUpdatedBy()}</td>
                        <td><a href="/admin/delete-product?id=${product.getId()}" class="btn btn-danger" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Delete') ? '' : 'display: none;'}">Delete</a>
                            <a href="#" class="btn btn-warning" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Update') ? '' : 'display: none;'}" data-bs-toggle="modal" data-bs-target="#updateModal"
                               onclick="setModalValues(${product.getId()}, '${product.getName()}', ${product.getPrice()}, '${product.getDescription()}',
                                       ${product.getDiscount()}, ${product.getFromDelivery()}, ${product.getToDelivery()})">Update</a>
                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#detailsModal"
                                    onclick="showJsonInModal('${product.getImages()}', '${product.getParams()}', '${product.getColours()}')">
                                ...
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Add Product Modal -->
        <div class="container my-4">
            <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addProductModalLabel">Add Product</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="productForm" action="/admin/add-product" method="post" enctype="multipart/form-data">
                                <!-- Basic Product Information -->
                                <div class="mb-3">
                                    <label for="name" class="form-label">Product Name</label>
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>

                                <div class="mb-3">
                                    <label for="price" class="form-label">Price</label>
                                    <input type="number" class="form-control" id="price" name="price" required>
                                </div>

                                <div class="mb-3">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="discount" class="form-label">Discount (%)</label>
                                    <input type="number" class="form-control" id="discount" name="discount">
                                </div>

                                <div class="mb-3">
                                    <label for="fromDelivery" class="form-label">From Delivery</label>
                                    <input type="number" class="form-control" id="fromDelivery" name="fromDelivery">
                                </div>

                                <div class="mb-3">
                                    <label for="toDelivery" class="form-label">To Delivery</label>
                                    <input type="number" class="form-control" id="toDelivery" name="toDelivery">
                                </div>

                                <div class="mb-3">
                                    <label for="categories" class="form-label">Categories</label>
                                    <select class="form-select" id="categories" name="category">
                                        <option value="">Category</option>
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category}">${category}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Params Section -->
                                <div class="mb-3">
                                    <button class="btn btn-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#paramsSection" aria-expanded="false" aria-controls="paramsSection">
                                        Params
                                    </button>
                                    <div class="collapse mt-2" id="paramsSection">
                                        <div id="paramsContainer">
                                            <div class="row g-3 mb-2 param-row">
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="paramName[]" placeholder="Parameter Name">
                                                </div>
                                                <div class="col-md-4">
                                                    <select class="form-control" name="paramType[]">
                                                        <option value="text">Text</option>
                                                        <option value="number">Number</option>
                                                        <option value="date">Date</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="paramValue[]" placeholder="Parameter Value">
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-outline-primary" id="addParamBtn">+ Add Parameter</button>
                                    </div>
                                </div>

                                <!-- Images Section -->
                                <div class="mb-3">
                                    <label for="images" class="form-label">Images</label>
                                    <input type="file" class="form-control" id="images" name="images[]" multiple>
                                </div>

                                <!-- Colours Section -->
                                <div class="mb-3">
                                    <button class="btn btn-secondary" type="button" data-bs-toggle="collapse" data-bs-target="#coloursSection" aria-expanded="false" aria-controls="coloursSection">
                                        Colours
                                    </button>
                                    <div class="collapse mt-2" id="coloursSection">
                                        <div id="coloursContainer">
                                            <div class="mb-3 colour-row">
                                                <div class="row g-3">
                                                    <div class="col-md-6">
                                                        <input type="text" class="form-control" name="colourName-0" placeholder="Colour Name">
                                                    </div>
                                                    <div class="col-md-6">
                                                        <input type="file" class="form-control" name="colourImages-0[]" multiple>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-outline-primary" id="addColourBtn">+ Add Colour</button>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-primary">Save Product</button>
                                </div>
                            </form>
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
                        <form id="updateForm" action="/admin/update-product" method="post">
                            <input type="hidden" id="productId" name="id">
                            <div class="mb-3">
                                <label for="productName" class="form-label">Name</label>
                                <input type="text" class="form-control" id="productName" name="name">
                            </div>
                            <div class="mb-3">
                                <label for="productPrice" class="form-label">Price</label>
                                <input type="number" class="form-control" id="productPrice" name="price">
                            </div>
                            <div class="mb-3">
                                <label for="productDescription" class="form-label">Description</label>
                                <input type="text" class="form-control" id="productDescription" name="description">
                            </div>
                            <div class="mb-3">
                                <label for="productDiscount" class="form-label">Discount</label>
                                <input type="number" class="form-control" id="productDiscount" name="discount">
                            </div>
                            <div class="mb-3">
                                <label for="productFromDelivery" class="form-label">FromDelivery</label>
                                <input type="number" class="form-control" id="productFromDelivery" name="fromDelivery">
                            </div>
                            <div class="mb-3">
                                <label for="productToDelivery" class="form-label">ToDelivery</label>
                                <input type="number" class="form-control" id="productToDelivery" name="toDelivery">
                            </div>
                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <!-- Info Modal -->
        <div class="modal fade" id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="detailsLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailsLabel">Product JSON Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h6>Images:</h6>
                        <pre id="jsonImages"></pre>
                        <h6>Params:</h6>
                        <pre id="jsonParams"></pre>
                        <h6>Colours:</h6>
                        <pre id="jsonColours"></pre>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Add dynamic parameter fields
    document.getElementById("addParamBtn").addEventListener("click", function() {
        var container = document.getElementById("paramsContainer");
        var newRow = document.createElement("div");
        newRow.classList.add("row", "g-3", "mb-2", "param-row");
        newRow.innerHTML = `
            <div class="col-md-4">
                <input type="text" class="form-control" name="paramName[]" placeholder="Parameter Name">
            </div>
            <div class="col-md-4">
                <select class="form-control" name="paramType[]">
                    <option value="text">Text</option>
                    <option value="number">Number</option>
                    <option value="date">Date</option>
                </select>
            </div>
            <div class="col-md-4">
                <input type="text" class="form-control" name="paramValue[]" placeholder="Parameter Value">
            </div>`;
        container.appendChild(newRow);
    });

    let groupCount = 0;

    // Add dynamic color fields
    document.getElementById("addColourBtn").addEventListener("click", function() {
        groupCount++;
        var container = document.getElementById("coloursContainer");
        var newRow = document.createElement("div");
        newRow.classList.add("mb-3", "colour-row");
        const temp = "<div class=\"row g-3\">" +
                        "<div class=\"col-md-6\">" +
                            "<input type=\"text\" class=\"form-control\" name=\"colourName-"+groupCount+"\" placeholder=\"Colour Name\">" +
                        "</div>" +
                        "<div class=\"col-md-6\">" +
                            "<input type=\"file\" class=\"form-control\" name=\"colourImages-"+groupCount+"[]\" multiple>" +
                        "</div>" +
                    "</div>";
        newRow.innerHTML = temp;
        container.appendChild(newRow);
        console.log(`Added input set with groupCount: "+groupCount+"`);
    });

    // Set values in the modal
    function setModalValues(id, name, price, description, discount, fromDelivery, toDelivery) {
        document.getElementById('productId').value = id;
        document.getElementById('productName').value = name;
        document.getElementById('productPrice').value = price;
        document.getElementById('productDescription').value = description;
        document.getElementById('productDiscount').value = discount;
        document.getElementById('productFromDelivery').value = fromDelivery;
        document.getElementById('productToDelivery').value = toDelivery;
    }

    function showJsonInModal(images, params, colours) {
        document.getElementById('jsonImages').textContent = JSON.stringify(JSON.parse(images), null, 2);
        document.getElementById('jsonParams').textContent = JSON.stringify(JSON.parse(params), null, 2);
        document.getElementById('jsonColours').textContent = JSON.stringify(JSON.parse(colours), null, 2);
    }

</script>

</body>
</html>
