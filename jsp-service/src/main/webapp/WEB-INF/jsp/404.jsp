<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - 页面未找到</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #fff;
        }

       .error-container {
            text-align: center;
        }

       .error-code {
            font-size: 120px;
            font-weight: bold;
            margin-bottom: 20px;
            text-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

       .error-message {
            font-size: 24px;
            margin-bottom: 30px;
        }

       .home-button {
            display: inline-block;
            background-color: #fff;
            color: #667eea;
            padding: 12px 24px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s, color 0.3s;
        }

       .home-button:hover {
            background-color: #e0e0e0;
        }
    </style>
</head>

<body>
    <div class="error-container">
        <div class="error-code">404</div>
        <div class="error-message">哎呀，你访问的页面好像走丢啦！</div>
        <a href="/jsp-service/login" class="home-button">返回登录页</a>
    </div>
</body>

</html>