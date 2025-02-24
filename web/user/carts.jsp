<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/17/2024
  Time: 10:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chat-content {
            padding: 20px;
            height: 100vh;
            overflow-y: auto;
        }
        .greeting {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            /*max-width: 600px;*/
            align-items: center;
            justify-content: space-between;
        }
        .last {
            margin-left: auto;
        }
        .body-container {
            display: flex;
            flex-direction: column;
            min-height: 75vh; /* Sahifani to'liq qamrab olish */
        }
        .footer {
            margin-top: auto;
            /*background-color: lightgray;*/
            text-align: center;
            padding: 10px;
        }
    </style>
</head>
<body>
<div class="chat-content">
    <div class="container my-4">
        <div class="body-container">
        <c:set var="sum" value="0" />
        <c:forEach items="${carts}" var="cart">
        <div class="greeting mb-5 d-flex">
            <div class="item image-block me-5">
                <img src="${pageContext.request.contextPath}/images/upload/${cart.getProductImages()}" alt="${cart.getProductName()}" style="height: 80px">
                <p><strong>${cart.getProductName()}</strong></p>
            </div>
            <div class="item row me-5">
                <button class="btn btn-warning d-flex align-items-center col" style="height: 30px" onclick="decreaseQuantity(${cart.getId()}, '${cart.getProductName()}', ${cart.getProductPrice()})"><strong>-</strong></button>
                <p id="${cart.getId()}" class="col" style="height: 30px"><strong>${cart.getQuantity()}</strong></p>
                <button class="btn btn-warning d-flex align-items-center col" style="height: 30px" onclick="increaseQuantity(${cart.getId()}, '${cart.getProductName()}', ${cart.getProductPrice()})"><strong>+</strong></button>
            </div>
            <div class="item last">
                <p>price: <strong id="${cart.getProductName()}">${cart.getProductPrice()}</strong> $</p>
            </div>
        </div>
        <c:set var="sum" value="${sum + (cart.getProductPrice() * cart.getQuantity())}" />
        </c:forEach>
        </div>

        <div class="d-flex footer" style="justify-content: flex-end">
            <div class="me-3">
                <a href="/user/category-product" id="cancelBtn" class="btn btn-danger mb-2 block"><strong>Cancel</strong></a>
                <label for="agreementId" id="agreementLabel" hidden="until-found" style="width: 100px">Do you agree if we confirm your order despite there are some error carts?</label>
            </div>
            <div class="me-3">
                <button id="confirmBtn" class="btn btn-success mb-2"><strong>Confirm</strong></button>
                <form method="post" action="/user/orders">
                    <input type="checkbox" id="agreementId" name="agreement" hidden="until-found" class="mb-2">
                    <button type="submit" id="hiddenBtn" class="btn btn-success no-disable" hidden="until-found"><strong>Order</strong></button>
                </form>
            </div>
            <div class="">
                <p class="align-items-center">Total: <strong id="total">${sum}</strong> $</p>
            </div>
        </div>
    </div>
</div>

<script>
    const buttonStatus = localStorage.getItem("buttonConfirm");
    if(buttonStatus === "disabled"){
        cartButtonDisabler();
    }

    let totalPrice = ${sum};
    function increaseQuantity(quantity, price, productPrice)  {
        let p = document.getElementById(quantity);
        let pri = document.getElementById(price);
        let value = parseInt(p.textContent, 10);
        value = value + 1;
        let totalAmount = productPrice * value;
        p.innerText = value.toString();
        pri.innerText = totalAmount.toString();
        calculateAfterIncreaseTotalAmount(productPrice);
    }

    function decreaseQuantity(quantity, price, productPrice)  {
        let p = document.getElementById(quantity);
        let pri = document.getElementById(price);
        let value = parseInt(p.textContent, 10);
        value = value - 1;
        let totalAmount = productPrice * value;
        p.innerText = value.toString();
        pri.innerText = totalAmount.toString();
        calculateAfterDecreaseTotalAmount(productPrice);
    }

    function calculateAfterIncreaseTotalAmount(productPrice) {
        totalPrice = totalPrice + productPrice;
        let total = document.getElementById("total");
        total.innerText = totalPrice;
    }

    function calculateAfterDecreaseTotalAmount(productPrice) {
        totalPrice = totalPrice - productPrice;
        let total = document.getElementById("total");
        total.innerText = totalPrice;
    }

    document.getElementById('confirmBtn').addEventListener('click', cartButtonDisabler)

    function cartButtonDisabler() {
        const buttons = document.querySelectorAll('button');

        buttons.forEach(function (button) {
            if (!button.classList.contains('no-disable')) {
                button.disabled = true;
            }
        })

        const hiddenButton = document.getElementById('hiddenBtn');
        hiddenButton.hidden = false;
        hiddenButton.style.display = 'block';
        const checkboxInput = document.getElementById('agreementId');
        checkboxInput.hidden = false;
        checkboxInput.style.display = 'block';
        const agreementLabel = document.getElementById('agreementLabel');
        agreementLabel.hidden = false;
        agreementLabel.style.display = 'block';
        const cancelBtn =  document.getElementById('cancelBtn');
        cancelBtn.classList.add("disabled");
        localStorage.setItem("buttonConfirm","disabled");
    }
    document.getElementById('hiddenBtn').addEventListener('click', function () {
        localStorage.removeItem("buttonConfirm");
    })

    document.getElementById('confirmBtn').addEventListener('click', function () {
        const ps = document.querySelectorAll('p');

        const jsonArray = [];

        ps.forEach(function (p) {
            if (p.classList.contains('col')) {
                let quantity = parseInt(p.textContent);
                let cartId = parseInt(p.id);
                const jsonObject = {
                    id: cartId,
                    quantity: quantity
                };
                jsonArray.push(jsonObject);
            }
        })
        updateCart(jsonArray);
        console.log(jsonArray);
    });

    function updateCart (jsonRequest) {
        const url = "http://localhost:8080//user/update-cart"
        const encodedUrl = encodeURI(url);

        const options = {
            method: 'POST',
            body: JSON.stringify(jsonRequest)
        }
        fetch(encodedUrl, options)
            .then(response => response.json())
            .then(response => {

            })
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
