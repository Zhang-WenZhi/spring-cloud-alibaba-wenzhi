<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录页面</title>
    <style>
        /* 全局样式 */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f06, #9f6);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* 登录容器样式 */
        .login-container {
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 320px;
            text-align: center;
        }

        /* 标题样式 */
        h1 {
            color: #333;
            margin-bottom: 20px;
            font-size: 28px;
        }

        /* 输入框容器样式 */
        .input-group {
            margin-bottom: 20px;
        }

        /* 标签样式 */
        label {
            display: block;
            text-align: left;
            color: #666;
            margin-bottom: 5px;
            font-size: 14px;
        }

        /* 输入框样式 */
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }

        /* 输入框聚焦样式 */
        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #007BFF;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        /* 按钮样式 */
        button {
            width: 100%;
            padding: 12px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        /* 按钮悬停样式 */
        button:hover {
            background-color: #0056b3;
        }

        /* 错误消息样式 */
        .error-message {
            color: red;
            margin-top: 10px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>用户登录</h1>
        <%
            String error = request.getParameter("error");
            if ("1".equals(error)) {
                out.println("<p class='error-message'>用户名或密码错误，请重试。</p>");
            }
        %>
        <form action="/jsp-service/home" method="post">
            <div class="input-group">
                <label for="username">用户名:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-group">
                <label for="password">密码:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">登录</button>
        </form>
    </div>
</body>
</html>