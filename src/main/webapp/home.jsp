<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <link rel="stylesheet" type="text/css" href="style.css">
    <title>SOAP</title>
</head>
<body>
<form name="form" action="searchWord" method="post" enctype="multipart/form-data">
    <div class="title" style="text-align: center;">
        <h2>Simple SOAP using</h2>
        <h3>Choose option:</h3>
    </div>
    <div>
        <fieldset>
            <legend><h3>Type word you want to count in file:</h3></legend>
            <input type="text" id="text" placeholder="Text to search" name="searchWord" requiredMessage="Please type your word!" required="true">
            <label for="text" id="nameID">Input word you want to search in file:</label>
            <input type="file" id="chooseFile" name="file" required>
            <label for="chooseFile">Choose file to upload:</label>
        </fieldset>
    </div>
    <div>
        <a>Result: ${countMatches}</a>
        <input id="countButton" type="submit" value="Get result">
    </div>
</form>
<form action="getFile" method="post">
    <div>
        <fieldset>
            <legend><h3>Choose file you want to download:</h3></legend>
            <c:forEach items="${files}" var="file">
            <input type="radio" id="${file}" value="${file}" name="fileName" required/>
            <label for="${file}"><span></span>${file}</label>
            <p>
                </c:forEach>
        </fieldset>
    </div>
    <div>
        <c:if test="${not empty fileName}">
            <h3 class="one">Name: ${fileName}</h3>
        </c:if>
        <input type="submit" value="Get file" id="videoFileButton">
    </div>
    <c:choose>
        <c:when test="${not empty fileName}">
            <video src="./files/${fileName}" loop="1" height="280" width="450" autostart="false" controls="controls"/>
        </c:when>
        <c:otherwise>
            <img src="./files/videoscreen.png" height="280" width="450">
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>