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
    <title>Order List</title>
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
                <li class="list-group-item"><a href="/admin/cart-list">Cart</a></li>
                <li class="list-group-item" style="background: yellow"><a href="/admin/order-list">Order</a></li>
            </ul>
        </div>



        <!-- Right Chat Content Container -->
        <div class="col-md-9 col-lg-10 chat-content">
<%--            <div class="container pb-3">--%>
<%--                <a href="#" class="btn btn-success" role="button" aria-pressed="true" style="${fn:contains(privileges, 'Create') ? '' : 'display: none;'}">ADD</a>--%>
<%--            </div>--%>

            <table class="table table-bordered table-responsive">
                <caption>List of orders</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">OrderId</th>
                    <th scope="col">UserName</th>
                    <th scope="col">UserPhoneNumber</th>
                    <th scope="col">UserEmail</th>
                    <th scope="col">OrderStatus</th>
                    <th scope="col">PromoCodeValue</th>
                    <th scope="col">PromoCodeType</th>
                    <th scope="col">PromoCodeStartDate</th>
                    <th scope="col">PromoCodeDays</th>
                    <th scope="col">PromoCodeFixedAmount</th>
                    <th scope="col">PromoCodePercent</th>
                    <th scope="col">PromoCodeMinAmount</th>
                    <th scope="col">OrderCreatedDate</th>
                    <th scope="col">OrderUpdatedDate</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="order">
                    <tr>
                        <th id="orderId" scope="row">${order.getOrderId()}</th>
                        <td>${order.getUserName()}</td>
                        <td>${order.getUserPhoneNumber()}</td>
                        <td>${order.getUserEmail()}</td>
                        <td id="orderStatus-${order.orderId}">${order.getOrderStatus()}</td>
                        <td>${order.getPromoCodeValue()}</td>
                        <td>${order.getPromoCodeType()}</td>
                        <td>${order.getPromoCodeStartDate()}</td>
                        <td>${order.getPromoCodeDays()}</td>
                        <td>${order.getPromoCodeFixedAmount()}</td>
                        <td>${order.getPromoCodePercent()}</td>
                        <td>${order.getPromoCodeMinAmount()}</td>
                        <td>${order.getOrderCreatedDate()}</td>
                        <td>${order.getOrderUpdatedDate()}</td>
                        <td><button type="button" class="btn btn-primary w-100"
                                    onclick="fetchProducts(${order.orderId})"
                                    data-bs-toggle="modal"
                                    data-bs-target="#productModal">
                            INFO
                        </button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="productModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="productModalLabel">Products</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="modal-body">
                <!-- Product details will be dynamically inserted here -->
            </div>
            <p id="modal-order-id" hidden></p>
            <button class="btn btn-success w-90 m-2" onclick="generateJson()" data-bs-dismiss="modal" aria-label="Close">Confirm</button>


        </div>
    </div>
</div>

<script>
    async function fetchProducts(orderId) {
        const url = "http://localhost:8080//user/order-product?id=" + orderId;
        const encodedUrl = encodeURI(url);
        try {
            const res = await fetch(encodedUrl);
            render(await res.json(), orderId);
        } catch (error) {
            console.error("Xato yuz berdi:", error);
        }
    }

    function render(productList, orderId) {
        let modalBody = document.getElementById("modal-body");
        let modalOrderId = document.getElementById("modal-order-id");
        modalOrderId.innerText = orderId;
        let temp = "";
        for (let i = 0; i < productList.length; i++) {
            temp += "<div class='mb-3 border p-3 rounded'>" +
                "<div class='d-flex align-items-center'>" +
                "<img src='${pageContext.request.contextPath}/images/upload/" + productList[i].images + "' " +
                "class='img-thumbnail me-3' " +
                "style='width: 100px; height: 100px;'>" +
                "<div class='ms-3'>" +
                "<h5>" + productList[i].name + "</h5>" +
                "</div>" +
                "<div class='ms-3'>" +
                "<h5>   Price: $" + productList[i].price + "  </h5>" +
                "</div>" +
                "<div class='ms-3'>" +
                "<h5>  Quantity: " + productList[i].quantity + "</h5>" +
                "</div>" +
                "<input class='ms-3' type='checkbox' value='" + productList[i].id + "' data-bs-target='' checked class='form-check-input'>" +
                "</div>" +
                "</div>";
        }
        modalBody.innerHTML = temp;
    }

    function generateJson() {
        const input = Array.from(document.getElementsByTagName("input"));
        console.log("inputs" + input)

        let modalOrderId = document.getElementById("modal-order-id");
        let orderId = parseInt(modalOrderId.textContent);

        let products = [];
        for (let i = 0; i <input.length; i++) {
            console.log("input: " + input[i])
            console.log("input type: " + input[i].type)
            if (input[i].type === 'checkbox') {
                let product = {
                    product_id: parseInt(input[i].value),
                    valid: input[i].checked
                }
                products.push(product);
            }
        }

        let orderStatus = 'ERROR';
        products.forEach((p) => {
            if(p.valid) {
                orderStatus = 'SUCCESS';
            }
        })

        let updateOrderJson =  {
            status: orderStatus,
            order_id: orderId,
            products: products
        }
        console.log(updateOrderJson);

        updateContent(updateOrderJson, orderId);
    }

    function updateContent(updateOrderJson, orderId) {
        const url = "http://localhost:8080//admin/update-order";
        const encodedUrl = encodeURI(url);
        const options = {
            method: 'POST',
            body: JSON.stringify( updateOrderJson )
        };
        fetch( encodedUrl, options )
            .then( response => response.json() )
            .then( response => {
                console.log(response)
                document.getElementById("orderStatus-" + orderId).innerText = response.orderStatus;
            } );
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
