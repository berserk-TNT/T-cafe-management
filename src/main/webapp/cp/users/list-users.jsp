<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User List</title>
    <%@include file="/cp/layout/script/head.jsp"%>
</head>

<body>
<!-- Begin page -->
<div id="wrapper">

    <!-- Topbar Start -->
    <div class="navbar-custom">
        <%@include file="/cp/layout/navbar/topnav-menu-right.jsp"%>

        <!-- LOGO -->
        <%@include file="/cp/layout/navbar/logo-box.jsp"%>

        <%@include file="/cp/layout/navbar/topnav-menu-left.jsp"%>
    </div>
    <!-- end Topbar -->

    <!-- ========== Left Sidebar Start ========== -->
    <%@include file="/cp/layout/script/left-side-menu.jsp"%>
    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <h1>User List</h1>
            <div class="col-lg-12" style="margin-bottom: 15px">
                <a href="/users?action=add">
                    <button type="button" class="btn btn-success">Create User</button>
                </a>
            </div>

            <div class="col-6 col-sm-6 form-group">
                <form class="input-group mb-3" action="/users?action=search">
                    <input type="search" name="key" class="form-control" id="myInput" value="${requestScope['key']}" placeholder="Search...">
                    <button type="submit">Search</button>
                </form>
            </div>
            <table class="table table-hover">
                <thead class="table-primary">
                    <tr style="color: black">
                        <th>ID</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Date Created</th>
                        <th style="text-align: center">Action</th>
                    </tr>
                </thead>
                <tbody class="table-warning">
                <c:forEach items="${requestScope['userList']}" var="item">
                    <tr>
                        <td>${item.getId()}</td>
                        <td>
                            <a href="/users?action=detail&id=${item.getId()}">${item.getName()}</a>
                        </td>
                        <td>${item.getPhone()}</td>
                        <td>${item.getEmail()}</td>
                        <td>${item.getAddress()}</td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${item.getTimeCreated()}"/></td>
                        <td style="text-align: center">
                            <a href="/users?action=edit&id=${item.getId()}">
                                <button type="button" class="btn btn-primary">Edit</button>
                            </a>
                            <a href="/users?action=delete&id=${item.getId()}">
                                <button type="button" class="btn btn-danger" onclick="return confirm('Are you sure to delete this user?')">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>  <!-- content -->

    <script>
        function deleteUserItem(idUser){
            if (confirm("Do you want to delete this user!") == true) {
                //text = "You pressed OK!";
                //console.log("Xoa..............")
                window.location.href = "/users?action=edit&id=${item.getId()}";

            } else {
                //text = "You canceled!";
                //console.log("Huy..............")
            }
        }
    </script>
        <!-- Footer Start -->
        <%@include file="/cp/layout/script/footer.jsp"%>
        <!-- end Footer -->

    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- Right Sidebar -->
<%@include file="/cp/layout/script/right-bar.jsp"%>
<!-- /Right-bar -->

<!-- Vendor js -->
<script src="/assets\js\vendor.min.js"></script>

<!-- Third Party js-->
<script src="/assets\libs\peity\jquery.peity.min.js"></script>
<script src="/assets\libs\apexcharts\apexcharts.min.js"></script>
<script src="/assets\libs\jquery-vectormap\jquery-jvectormap-1.2.2.min.js"></script>
<script src="/assets\libs\jquery-vectormap\jquery-jvectormap-us-merc-en.js"></script>

<!-- Dashboard init -->
<script src="/assets\js\pages\dashboard-1.init.js"></script>

<!-- App js -->
<script src="/assets\js\app.min.js"></script>

</body>
</html>