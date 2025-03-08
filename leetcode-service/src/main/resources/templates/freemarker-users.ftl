<!DOCTYPE html>
<html>
<head>
    <title>用户列表（FreeMarker）</title>
</head>
<body>
    <h1>用户列表（FreeMarker）</h1>
    <ul>
        <!-- 使用 FreeMarker 的 <#list> 指令遍历用户列表 -->
        <#list users as user>
            <li>${user}</li>
        </#list>
    </ul>
</body>
</html>