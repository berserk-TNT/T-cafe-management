<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Products</title>
    <%@include file="/cp/layout/script/head.jsp"%>
</head>

<body style="background-color: whitesmoke">

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

            <h1 style="color: #1c304e">Add product</h1>
            <div class="col-lg-12" style="margin-bottom: 15px">
                <a href="/products">
                    <button type="button" class="btn btn-success">List of products</button>
                </a>
            </div>

            <div>
                <form method="post" enctype="multipart/form-data">
                    <div class="col-6 col-sm-6 form-group">
                        <label for="imageUrl" class="form-label">Image</label>
                        <input type="file" required class="form-control" id="imageUrl" accept="image/png, image/jpeg" name="imageUrl" size="80">
                    </div>
                    <div class="col-6 col-sm-6 form-group">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" required class="form-control" id="name" name="name">
                    </div>
                    <div class="col-6 col-sm-6 form-group">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" required class="form-control" id="price" name="price" min="1000" max="10000000">
                    </div>
                    <div class="col-6 col-sm-6 form-group">
                        <label for="quantity" class="form-label">Quantity</label>
                        <input type="number" required class="form-control" id="quantity" name="quantity" min="1" max="1000">
                    </div>
                    <button type="submit" class="btn btn-primary">Create</button>
                </form>
            </div>

            <div>
                <c:if test="${requestScope['isSuccess'] == true}">
                    <ul class="success" style="color: #00cb1f">
                        <li>Product is added successful!</li>
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