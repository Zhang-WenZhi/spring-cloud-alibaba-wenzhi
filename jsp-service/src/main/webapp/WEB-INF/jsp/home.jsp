<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh - CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>主页</title>
    <style>
        body {
            font - family: 'Roboto', sans - serif;
            background: #f4f4f4;
            margin: 0;
            padding: 0;
        }

       .header {
            background: linear - gradient(to bottom, #009688, #00796B);
            color: white;
            text - align: center;
            padding: 20px 0;
            box - shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        h1 {
            margin: 0;
            font - size: 32px;
        }

       .content {
            padding: 30px;
        }

       .welcome - message {
            color: #333;
            font - size: 20px;
            margin - bottom: 20px;
        }

       .feature {
            background: white;
            border - radius: 10px;
            box - shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin - bottom: 20px;
            text - align: center;
        }

       .feature h2 {
            color: #009688;
            font - size: 24px;
            margin - top: 0;
        }

       .feature p {
            color: #666;
            font - size: 16px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>欢迎来到主页</h1>
</div>
<div class="content">
    <p class="welcome - message">欢迎，<%= session.getAttribute("username") %>！</p>
    <div class="feature">
        <h2>功能 1</h2>
        <p>这里可以描述功能 1 的具体内容。</p>
    </div>
    <div class="feature">
        <h2>功能 2</h2>
        <p>这里可以描述功能 2 的具体内容。</p>
    </div>
</div>
</body>
</html>