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
<form name="form" action="searchWord" onsubmit="return validateForm()" method="post" enctype="multipart/form-data">
    <div class="title" style="text-align: center;">
        <h2>Simple SOAP using</h2>
        <h3>Choose option:</h3>
    </div>
    <div>
        <fieldset>
            <legend><h3>Type word you want to find:</h3></legend>
            <input type="text" id="text" placeholder="Text to search">
            <label for="text" id="nameID">Input word you want to search in file:</label>
            <input type="file" id="chooseFile">
            <label for="chooseFile">Choose file to upload:</label>
    </fieldset>
    </div>
    <input type="submit" value="Get result">
</form>
<form name="form" action="getFile" method="post">
    <div>
        <fieldset>
            <legend><h3>Choose file you want to download:</h3></legend>
            <input type="radio" id="r1" name="rr"/>
            <label for="r1"><span></span>File 1</label>
            <p>
                <input type="radio" id="r2" name="rr"/>
                <label for="r2"><span></span>File 2</label>
            <p>
                <input type="radio" id="r3" name="rr"/>
                <label for="r3"><span></span>File 3</label>
            <p>
                <input type="radio" id="r4" name="rr"/>
                <label for="r4"><span></span>File 4</label>
            <p>
                <input type="radio" id="r5" name="rr"/>
                <label for="r5"><span></span>File 5</label>
        </fieldset>
    </div>
    <input type="submit" value="Get result">
</form>
</body>
</html>