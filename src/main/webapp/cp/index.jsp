<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
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



        </div> <!-- content -->

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