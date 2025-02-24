<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/15/2024
  Time: 11:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Example Category</title>
    <style>
        /* Main menu container */
        .menu {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            background-color: #333;
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
            background-color: #333;
            transition: background-color 0.3s;
        }

        .menu > li > a:hover {
            background-color: #555;
        }

        /* Submenu styling */
        li ul {
            display: none;
            position: absolute;
            top: 100%;
            left: 0;
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
    </style>
</head>
<body>
<ul class="menu">
    <c:forEach items="${categories}" var="category">
        <li id="${category.id}" class="hover-area">
            <a>${category.name}</a>
        </li>
    </c:forEach>
</ul>


<script>
    async function getSubCategoriesByFetch(categoryId) {
        const url = "http://localhost:8080//user/sub-category?id=" + categoryId;
        const encodedUrl = encodeURI(url);
        try {
            const res = await fetch(encodedUrl);
            render(await res.json(), categoryId);
        } catch (error) {
            console.error("Xato yuz berdi:", error);
        }
    }

    function render(subCategories, categoryId) {
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
                    "<li id=\""+subCategories[i].id+"\">" +
                    "<a>"+subCategories[i].name+"</a>" +
                    "</li>";
            }
        }

        categoryList.appendChild(newRow);
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
</body>
</html>
