<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Detail</title>
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

            <div class="container">
                <div class="row">
                    <div class="col-lg-6">
                        <h1>User information</h1>
                    </div>
                    <div class="col-lg-12" style="margin: 15px">
                        <a href="/users">
                            <button type="button" class="btn btn-success">List of users</button>
                        </a>
                    </div>
                </div>

                <div>
                    <table>
                        <tr>
                            <td style="color: yellow">ID:</td>
                            <td style="font-weight: bolder">${requestScope['user'].id}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Username:</td>
                            <td style="font-weight: bolder">${requestScope['user'].username}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Password:</td>
                            <td style="font-weight: bolder">${requestScope['user'].password}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Name:</td>
                            <td style="font-weight: bolder">${requestScope['user'].name}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Phone:</td>
                            <td style="font-weight: bolder">${requestScope['user'].phone}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Email:</td>
                            <td style="font-weight: bolder">${requestScope['user'].email}</td>
                        </tr>
                        <tr>
                            <td style="color: yellow">Address:</td>
                            <td style="font-weight: bolder">${requestScope['user'].address}</td>
                        </tr>
                    </table>

                </div>

            </div>
        </div>  <!-- content -->

        <!-- Footer Start -->
        <%@include file="/cp/layout/script/footer.jsp"%>
        <!-- end Footer -->

    </div>

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