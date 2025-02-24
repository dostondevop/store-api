<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Category Product</title>
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
        a {
            color: inherit;
            text-decoration: none;
        }
        /* Main menu container */
        .menu {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
        }

        /* Top-level menu items */
        .menu > li {
            position: relative;
            margin: 0;
        }

        /* Top-level links styling */
        .menu > li > a {
            display: block;
            padding: 15px 20px;
            color: #fff;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .menu > li > a:hover {
            background-color: #555;
        }

        /* Submenu styling */
        li ul {
            display: none;
            position: absolute;
            top: 0;
            left: 100%;
            min-width: 200px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 1;
        }

        li:hover > ul {
            display: block;
        }

        /* Submenu items */
        ul li {
            position: relative;
        }

        /* Links in submenus */
        a {
            display: block;
            padding: 10px;
            color: #333;
            text-decoration: none;
            background-color: #f9f9f9;
            transition: background-color 0.3s;
        }

        .menu li ul li a:hover {
            background-color: #e0e0e0;
        }

        /* Nested submenus for further levels */
        .menu li ul li ul {
            top: 0;
            left: 100%;
            margin-top: -1px;
            border: 1px solid #ddd;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
        }

        /* Arrow indicators for nested menus */
        .menu li ul li:has(ul) > a::after {
            content: "►";
            float: right;
            margin-right: 10px;
            font-size: 12px;
            color: #888;
        }
        .circle-border {
            border: 1px solid darkred;
            border-radius: 50%;
            background: yellow
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">


        <!-- Left Sidebar Container -->
        <div class="col-md-3 col-lg-2 chat-sidebar p-3">
            <ul class="menu list-group">
                <c:forEach items="${categories}" var="category">
                    <c:choose>
                        <c:when test="${category.last}">
                            <li id="${category.id}" class="hover-area list-group-item" onclick="getCategoryProductByFetch('${category.id}')">
                                <a>${category.name}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li id="${category.id}" class="hover-area">
                                <a>${category.name}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>



        <!-- Right Chat Content Container -->

        <div class="col-md-9 col-lg-10 chat-content">
            <div style="display: flex; justify-content: flex-end">
                <a href="/user/order-list" type="button" class="btn btn-success d-inline-flex align-items-center justify-content-center p-2 me-5">My Orders</a>
                <a href="/user/carts" type="button" class="btn btn-success d-inline-flex align-items-center justify-content-center p-2"> Carts:
                    <strong id="cart-count" class="p-2">${cartsCount}</strong>
                </a>
            </div>
            <div class="container my-4">
                <div class="row" id="product-list">

                </div>
            </div>
        </div>

    </div>
</div>

<script>
    async function getCategoryProductByFetch(categoryId) {
        const url = "http://localhost:8080//user/products?id=" + categoryId;
        const encodedUrl = encodeURI(url);
        try {
            const res = await fetch(encodedUrl);
            render(await res.json());
        } catch (error) {
            console.error("Xato yuz berdi:", error);
        }
    }

    function render(categoryProducts) {
        let categoryProductList = document.getElementById("product-list");
        let temp = "";
        for (let i = 0; i < categoryProducts.length; i++) {
            temp += "<div class=\"col-md-3 mb-4\">" +
                "<div class=\"card h-100\">" +
                "<img src=\"${pageContext.request.contextPath}/images/upload/"+categoryProducts[i].images+"\" class=\"card-img-top\" alt=\""+categoryProducts[i].name+"\" style=\"object-fit: cover; width: 100%; height: 200px;\">" +
                "<div class=\"card-body\">" +
                "<h5 class=\"card-title\">"+categoryProducts[i].name+"</h5>" +
                "<p class=\"card-text\">"+categoryProducts[i].description+"</p>" +
                "<p class=\"card-text\"><strong>Price: $"+categoryProducts[i].price+"</strong></p>" +
                "<button class = \"btn btn-primary\" onclick = addCart("+categoryProducts[i].id+")> add cart </button>"   +
                "</div>" +
                "</div>" +
                "</div>";
        }
        categoryProductList.innerHTML = temp;
    }

    async function getSubCategoriesByFetch(categoryId) {
        const url = "http://localhost:8080//user/sub-category?id=" + categoryId;
        const encodedUrl = encodeURI(url);
        try {
            const res = await fetch(encodedUrl);
            renderCategory(await res.json(), categoryId);
        } catch (error) {
            console.error("Xato yuz berdi:", error);
        }
    }

    function renderCategory(subCategories, categoryId) {
        const categoryList = document.getElementById(categoryId);

        const existingSubMenu = categoryList.querySelector("ul");
        if (existingSubMenu) {
            categoryList.removeChild(existingSubMenu);
        }

        let newRow = document.createElement("ul");
        for (let i = 0; i < subCategories.length; i++) {
            if (!subCategories[i].last) {
                newRow.innerHTML +=
                    "<li id=\""+subCategories[i].id+"\" onmouseenter=\"getSubCategoriesByFetch("+subCategories[i].id+")\">" +
                    "<a>"+subCategories[i].name+"</a>" +
                    "</li>";
            } else {
                newRow.innerHTML +=
                    "<li id=\""+subCategories[i].id+"\" onclick=\"getCategoryProductByFetch('"+subCategories[i].id+"')\">" +
                    "<a>"+subCategories[i].name+"</a>" +
                    "</li>";
            }
        }

        categoryList.appendChild(newRow);
    }

    function addCart (productId){
        const url = "http://localhost:8080//admin/add-cart";
        const encodedUrl = encodeURI(url);
        const params = {
            productId: productId
        };
        const options = {
            method: 'POST',
            body: JSON.stringify( params )
        };
        fetch( encodedUrl, options )
            .then( response => response.json() )
            .then( response => {
                console.log(response)
                if (response.position === "error") {
                    alert("This product was in cart. You can't add one product twice to cart.")
                } else {
                    document.getElementById("cart-count").innerText = response.length
                }
            } );
    }

    document.querySelectorAll('.hover-area').forEach(area => {
        let triggered = false;

        area.addEventListener('mouseenter', (event) => {
            if (!triggered) {
                triggered = true;
                const areaId = event.target.id;
                getSubCategoriesByFetch(areaId)
            }
        });

        area.addEventListener('mouseleave', () => {
            triggered = false;
        });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
