<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/18/2024
  Time: 3:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chat-content {
            padding: 20px;
            height: 100vh;
            overflow-y: auto;
        }
    </style>
</head>
<body>

<div class="chat-content">
    <div class="container my-4">

        <c:forEach items="${orders}" var="order">
        <div class="accordion mb-3" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading-${order.orderId}">
                        <button class="accordion-button collapsed" type="button" style="background: #CEE1FE" data-bs-toggle="collapse" data-bs-target="#collapse-${order.orderId}" aria-expanded="false" aria-controls="collapse-${order.orderId}" onclick="getOrderProductsByFetch(${order.orderId})">
                            <ul class="row w-100">
                                <li class="col">Order Id: ${order.orderId}</li>
                                <li class="col">Order Status: ${order.orderStatus}</li>
                                <li class="col">Order Created Date: ${order.orderCreatedDate}</li>
                                <li class="col">Order Updated Date: ${order.orderUpdatedDate}</li>
                            </ul>
                        </button>
                    </h2>
                    <div id="collapse-${order.orderId}" class="accordion-collapse collapse" aria-labelledby="heading-${order.orderId}" data-bs-parent="#accordionExample">
                        <div class="accordion-body w-100" id="${order.orderId}">

                        </div>
                    </div>
                </div>

        </div>
        </c:forEach>

    </div>
</div>

<script>
    async function getOrderProductsByFetch(orderId) {
        const url = "http://localhost:8080//user/order-product?id=" + orderId;
        const encodedUrl = encodeURI(url);
        try {
            const res = await fetch(encodedUrl);
            setProductsToAccordion(await res.json(), orderId);
        } catch (error) {
            console.error("Xato yuz berdi:", error);
        }
    }

    function setProductsToAccordion(productList, accordionId) {
        let accordion = document.getElementById(accordionId);
        console.log(productList);
        let temp = "";
        for (let i = 0; i < productList.length; i++) {
            temp += (productList[i].productStatus === 'ERROR' ?
                    "<div class=\"row d-flex w-100 p-2 mb-2 border border-danger rounded-pill\">" :
                    productList[i].productStatus === 'ALREADY_ORDERED' ?
                    "<div class=\"row d-flex w-100 p-2 mb-2 border border-warning rounded-pill\">" :
                        "<div class=\"row d-flex w-100 p-2 mb-2 border border-success rounded-pill\">") +
                            "<div class=\"col-md-4 image-block p-3\">" +
                                "<img src=\"${pageContext.request.contextPath}/images/upload/"+productList[i].images+"\" alt=\""+productList[i].name+"\" style=\"height: 80px\">" +
                                "<p><strong>"+productList[i].name+"</strong></p>" +
                            "</div>" +
                            "<div class=\"col-md-4 p-3\">" +
                                "<p>"+productList[i].description+"</p>" +
                                "<p>quantity: "+productList[i].quantity+"</p>" +
                                "<p>price: "+productList[i].price+"</p>" +
                            "</div>";
            if (productList[i].productStatus === "ERROR") {
                temp += "<div class=\"col-md-4 p-3\">" +
                        "<p><strong class=\"p-2\" style='background: red'>"+productList[i].productStatus+"</strong></p>" +
                    "</div>";
            } else if (productList[i].productStatus === "ALREADY_ORDERED") {
                temp += "<div class=\"col-md-4 p-3\">" +
                    "<p><strong class=\"p-2\" style='background: yellow'>"+productList[i].productStatus+"</strong></p>" +
                    "</div>";
            } else {
                temp += "<div class=\"col-md-4 p-3\">" +
                        "<p><strong class=\"p-2\" style='background: green'>"+productList[i].productStatus+"</strong></p>" +
                    "</div>";
            }

                      temp += "</div>";
        }
        accordion.innerHTML = temp;
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
