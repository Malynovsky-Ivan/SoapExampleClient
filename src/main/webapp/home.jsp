<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="validation.js"></script>
    <title>SOAP</title>
</head>
<body>
<form name="form" action="searchWord"  method="post" enctype="multipart/form-data">
    <div class="title" style="text-align: center;">
        <h2>Simple SOAP using</h2>
        <h3>Choose option:</h3>
    </div>
    <div>
        <fieldset>
            <legend><h3>Type word you want to find:</h3></legend>
            <input type="text" id="text" placeholder="Text to search" name="searchWord">
            <label for="text" id="nameID">Input word you want to search in file:</label>
            <input type="file" id="chooseFile" name="file">
            <label for="chooseFile">Choose file to upload:</label>
    </fieldset>
    </div>
    <input type="submit" value="Get result">
</form>
<form action="getFile" method="post">
    <div>
        <fieldset>
            <legend><h3>Choose file you want to download:</h3></legend>
            <c:forEach items="${files}" var="file">
            <input type="radio" id="${file}" value="${file}" name="fileName"/>
            <label for="${file}"><span></span>${file}</label>
            <p>
            </c:forEach>
        </fieldset>
    </div>
    <input type="submit" value="Get file">
    <EMBED SRC="./files/${fileName}" loop="1" height="280" width="450" autostart="true">
</form>
</body>
</html>