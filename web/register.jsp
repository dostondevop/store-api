<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11/8/2024
  Time: 1:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
        }
        .dropdown-menu {
            max-width: 250px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="/register" method="post">
        <div class="form-group mb-3">
            <label for="exampleInputName1">Name</label>
            <input type="text" class="form-control" id="exampleInputName1" placeholder="Name" name="name">
        </div>
        <div class="form-group mb-3">
            <label for="exampleInputPhoneNumber1">PhoneNumber</label>
            <input type="text" class="form-control" id="exampleInputPhoneNumber1" placeholder="PhoneNumber" name="phoneNumber">
        </div>
        <div class="form-group mb-3">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password">
        </div>
        <div class="form-group mb-3">
            <label for="exampleInputEmail1">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" name="email">
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>
            <div class="mb-3">
                <label for="role" class="form-label">Role</label>
                <select class="form-select" id="role" name="role">
                    <option value="">Role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                </select>
            </div>
        <div id="privilegesSection" class="container mt-3" style="display:none;">
            <div class="dropdown">
                <button class="btn btn-primary dropdown-toggle" type="button" id="multiSelectDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                    Select Roles
                </button>
                <ul class="dropdown-menu" aria-labelledby="multiSelectDropdown">
                    <li class="dropdown-item">
                        <input type="checkbox" name="privileges" value="Create" id="privileges1">
                        <label for="privileges1">Create</label>
                    </li>
                    <li class="dropdown-item">
                        <input type="checkbox" name="privileges" value="Update" id="privileges2">
                        <label for="privileges2">Update</label>
                    </li>
                    <li class="dropdown-item">
                        <input type="checkbox" name="privileges" value="Delete" id="privileges3">
                        <label for="privileges3">Delete</label>
                    </li>
                </ul>
            </div>
        </div>
        <button type="submit" class="btn btn-primary w-100 mt-3">Submit</button>
    </form>
</div>



<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.getElementById("role").addEventListener("change", function() {
        var role = this.value;
        var privilegesSection = document.getElementById("privilegesSection");
        if (role === "ADMIN") {
            privilegesSection.style.display = "block";
        } else {
            privilegesSection.style.display = "none";
        }
    });
</script>
</body>
</html>
