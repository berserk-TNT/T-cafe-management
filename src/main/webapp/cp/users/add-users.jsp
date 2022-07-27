<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add User</title>
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
                        <h1>Add User</h1>
                    </div>
                    <div class="col-lg-12" style="margin-bottom: 15px">
                        <a href="/users">
                            <button type="button" class="btn btn-success">List of users</button>
                        </a>
                    </div>
                </div>

                <div>
                    <form method="post">
                        <div class="col-4 col-sm-8 form-group">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" required class="form-control" id="username" name="username" maxlength="30">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" required class="form-control" id="password" name="password" maxlength="30">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="retypePassword" class="form-label">Retype password</label>
                            <input type="password" required class="form-control" id="retypePassword" name="retypePassword" maxlength="30">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="name" class="form-label">Name</label>
                            <input type="text" required class="form-control" id="name" name="name" maxlength="100">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="phone" class="form-label">Phone</label>
                            <input type="tel" required class="form-control" id="phone" name="phone" maxlength="20">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" required class="form-control" id="email" name="email" maxlength="100">
                        </div>
                        <div class="col-4 col-sm-8 form-group">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" required class="form-control" id="address" name="address" maxlength="255">
                        </div>
                        <button type="submit" class="btn btn-primary">Create</button>
                    </form>
                </div>

                <div>
                    <c:if test="${requestScope['isSuccess'] == true}">
                        <ul class="success" style="color: #00cb1f">
                            <li>User is created successful!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!requestScope['errors'].isEmpty()}">
                        <ul class="error" style="color: #cb001d">
                            <c:forEach items="${requestScope['errors']}" var="item">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
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