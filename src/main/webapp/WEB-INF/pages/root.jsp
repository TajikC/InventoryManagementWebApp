<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory Management</title>
    <meta charset="utf-8">

    <c:url value="/css/main.css" var="maincss" />
    <link rel="stylesheet" type="text/css" href="${maincss}" />
</head>
<body>
    <h1>Inventory Management System</h1>
    <div id="main-app">
        <nav id="nav-top-options">
            <ul id="nav-list">
                <li id="home-option" class="selected">Home</li>
                <li id="rent-option"><a>Rent</a></li>
                <li id="return-option"><a>Return</a></li>
                <li id="search-option"><a>Search</a></li>
                <li id="admin-option"><a>Administrative</a></li>
            </ul>
        </nav>
        <!-- hidden tabbing using sections-->
        <div id="tabbed-content">
            <section id="home-content" class="visible-content">
                <p>Welcome to this great inventory management system that was created for
                CS575 taught by Brian Mitchell. This project was done by Tajik Choudhury
                and Thang Dao. It utilizes a few things taught in class including, but not
                limited to, REST API, service oriented architecture, higher level web applications (AJAX),
                MVC framework (using Spring MVC), and functional languages (Scala Play framework).</p>
            </section>

            <section id="rent-content">
                <form id="rent-form">
                    <div id="rent-left-box">
                        <div id="rent-inventory-select-container">
                            <label for="rent-inventory-select">Inventory</label>
                            <select id="rent-inventory-select" multiple>
                                <c:forEach var="inventoryItem" items="${inventory}">
                                    <option id="rent-item-${inventoryItem.key}" data-inventory-id="${inventoryItem.key}" data-item-name="${inventoryItem.value.name}" data-item-type="${inventoryItem.value.type}" data-item-price="${inventoryItem.value.price}">${inventoryItem.value.name}</option>
                                </c:forEach>
                            </select>
                            <button id="rent-to-checkout" type="button">To Check out</button>
                        </div>
                        <div id="rent-checkout-select-container">
                            <label for="rent-checkout-select">Check Out</label>
                            <select id="rent-checkout-select" multiple>
                            </select>
                            <button id="rent-to-inventory" type="button">To Inventory</button>
                        </div>
                    </div>
                    <div id="rent-right-box">
                        <label for="rent-user-select">Patron Id</label>
                        <select id="rent-user-select">
                            <c:forEach var="patron" items="${patrons}">
                                <option data-patron-id="${patron.key}">${patron.value.firstName} ${patron.value.lastName} - ${patron.key}</option>
                            </c:forEach>
                        </select>

                        <h2>Selected Item Info</h2>
                        <dl id="rent-item-info">
                            <dt>Id</dt>
                            <dd></dd>
                            <dt>Name</dt>
                            <dd></dd>
                            <dt>Price</dt>
                            <dd></dd>
                        </dl>
                        <button id="rent-checkout-button" class="primary-action-button" type="button">Check out</button>
                    </div>
                </form>
            </section>

            <section id="return-content">

            </section>

            <section id="search-content">

            </section>

            <section id="admin-content">

            </section>
        </div>
    </div>

    <c:url value="/js/inventory-management.js" var="mainjs" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="${mainjs}"></script>
</body>
</html>
