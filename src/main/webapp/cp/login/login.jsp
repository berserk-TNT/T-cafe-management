<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/assets/css/login.css">
    <script src="/assets/js/login.js"></script>

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script type="text/javascript"
            src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
</head>
<body>
<div class="container">
    <header>Login Account</header>
    <form method="post">
        <div class="input-field">
            <input type="text" name="username" size="30" required>
            <label>Username</label>
        </div>
        <div class="input-field">
            <input class="password" type="password" name="password" size="30" required>
            <label>Password</label>
        </div>
        <div class="button">
            <div class="inner">
                <button type="submit">LOGIN</button>
            </div>
        </div>
    </form>
    <div class="footer">
        <c:if test="${requestScope['isSuccess'] == true}">
            <ul class="success">
                <li>Login success</li>
            </ul>
        </c:if>
        <c:if test="${requestScope['message'] != null}">
            <ul class="error">
                <li>${requestScope['message']}</li>
            </ul>
        </c:if>
        <c:if test="${!requestScope['errors'].isEmpty()}">
            <ul class="error">
                <c:forEach items="${requestScope['errors']}" var="item">
                    <li>${item}</li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</div>
</body>
</html>
