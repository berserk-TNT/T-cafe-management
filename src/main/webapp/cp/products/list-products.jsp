<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Product List</title>
    <%@include file="/cp/layout/script/head.jsp"%>
    <link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
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

            <h1 style="color: #1c304e">Product List</h1>
            <div class="col-6 col-sm-6 form-group">
                <form class="input-group mb-3" action="/products">
                    <input type="search" name="q" class="form-control" id="myInput" value="${requestScope['q']}" placeholder="Search...">
                    <button type="submit">Search</button>
                </form>
            </div>

            <div class="col-lg-12" style="margin-bottom: 10px">
                <a href="/products?action=add">
                    <button type="button" class="btn btn-success">Add product</button>
                </a>
            </div>

            <table class="table table-hover" id="productTable">
                <thead class="table-primary">
                <tr style="color: black">
                    <th>ID</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th style="text-align: center">Quantity</th>
                    <th style="text-align: center">Action</th>
                </tr>
                </thead>

                <tbody class="table-warning">
                <c:forEach items="${requestScope['productList']}" var="item">
                    <tr>
                        <td style="color: #008a00">${item.getId()}</td>
                        <td>
<%--                            <a href="/products?action=detail&id=${item.getId()}">--%>
                                <img src="${item.getImageUrl()}" alt="" style="width: 80px; height: 80px">
<%--                            </a>--%>
                        </td>
                        <td style="color: #008a00">
<%--                            <a href="/products?action=detail&id=${item.getId()}">--%>
                                ${item.getName()}
<%--                            </a>--%>
                        </td>
                        <td style="color: #008a00"><fmt:formatNumber value="${item.getPrice()}" type="currency" pattern="#,### ₫"/></td>
                        <td style="text-align: center; color: #008a00">${item.getQuantity()}</td>
                        <td style="text-align: center; color: #008a00">
                            <a href="/products?action=edit&id=${item.getId()}">
                                <button type="button" class="btn btn-primary">Edit</button>
                            </a>
                            <a href="/products?action=delete&id=${item.getId()}" onclick="return confirm('Are you sure to delete this product?')">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <c:if test="${currentPage != 1}">
                <td><a href="/products?action=search&page=${currentPage - 1}&q=${requestScope['q']}">Previous</a></td>
            </c:if>

            <table border="1px">
                <tr>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="/products?action=search&page=${i}&q=${requestScope['q']}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>

            <c:if test="${currentPage lt noOfPages}">
                <td><a href="/products?action=search&page=${currentPage + 1}&q=${requestScope['q']}">Next</a></td>
            </c:if>

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

<%--    <script>--%>
<%--        function sortTable(n) {--%>
<%--            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;--%>
<%--            table = document.getElementById("productTable");--%>
<%--            switching = true;--%>
<%--            dir = "asc";--%>
<%--            while (switching) {--%>
<%--                switching = false;--%>
<%--                rows = table.rows;--%>
<%--                for (i = 1; i < (rows.length - 1); i++) {--%>
<%--                    shouldSwitch = false;--%>
<%--                    x = rows[i].getElementsByTagName("td")[n];--%>
<%--                    y = rows[i + 1].getElementsByTagName("td")[n];--%>
<%--                    if (dir === "asc") {--%>
<%--                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {--%>
<%--                            shouldSwitch= true;--%>
<%--                            break;--%>
<%--                        }--%>
<%--                    } else if (dir === "desc") {--%>
<%--                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {--%>
<%--                            shouldSwitch = true;--%>
<%--                            break;--%>
<%--                        }--%>
<%--                    }--%>
<%--                }--%>
<%--                if (shouldSwitch) {--%>
<%--                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);--%>
<%--                    switching = true;--%>
<%--                    switchcount ++;--%>
<%--                } else {--%>
<%--                    if (switchcount === 0 && dir === "asc") {--%>
<%--                        dir = "desc";--%>
<%--                        switching = true;--%>
<%--                    }--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>

<%--        function search() {--%>
<%--            var input, filter, table, tr, td, i, txtValue, element;--%>
<%--            element = document.querySelector("div>select").value--%>
<%--            input = document.getElementById("myInput");--%>
<%--            filter = input.value.toUpperCase();--%>
<%--            table = document.getElementById("productTable");--%>
<%--            tr = table.getElementsByTagName("tr");--%>
<%--            for (i = 0; i < tr.length; i++) {--%>
<%--                if (element == 0) {--%>
<%--                    td = tr[i].getElementsByTagName("td")[1];--%>
<%--                    if (td) {--%>
<%--                        txtValue = td.textContent || td.innerText;--%>
<%--                        if (txtValue.toUpperCase().indexOf(filter) > -1) {--%>
<%--                            tr[i].style.display = "";--%>
<%--                        } else {--%>
<%--                            tr[i].style.display = "none";--%>
<%--                        }--%>
<%--                    }--%>
<%--                }--%>
<%--                if (element == 1) {--%>
<%--                    td = tr[i].getElementsByTagName("td")[2];--%>
<%--                    if (td) {--%>
<%--                        txtValue = td.textContent || td.innerText;--%>
<%--                        if (txtValue.toUpperCase().indexOf(filter) > -1) {--%>
<%--                            tr[i].style.display = "";--%>
<%--                        } else {--%>
<%--                            tr[i].style.display = "none";--%>
<%--                        }--%>
<%--                    }--%>
<%--                }--%>
<%--                if (element == 2) {--%>
<%--                    td = tr[i].getElementsByTagName("td")[3];--%>
<%--                    if (td) {--%>
<%--                        txtValue = td.textContent || td.innerText;--%>
<%--                        if (txtValue.toUpperCase().indexOf(filter) > -1) {--%>
<%--                            tr[i].style.display = "";--%>
<%--                        } else {--%>
<%--                            tr[i].style.display = "none";--%>
<%--                        }--%>
<%--                    }--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>
<%--    </script>--%>

</body>
</html>