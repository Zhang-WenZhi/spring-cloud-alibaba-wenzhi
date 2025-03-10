<%@ page language="java" contentType="text/html; charset=UTF - 8"
         pageEncoding="UTF - 8"%>
<%@ page import="java.util.*" %>
<%
    request.setCharacterEncoding("UTF-8");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    // 简单的验证逻辑，实际应用中应从数据库或其他存储中验证
    if ("admin".equals(username) && "123456".equals(password)) {
        // 登录成功，设置会话属性
        session.setAttribute("username", username);
        response.sendRedirect("home.jsp");
    } else {
        // 登录失败，重定向回登录页面并携带错误信息
        response.sendRedirect("login.jsp?error=1");
    }
%>