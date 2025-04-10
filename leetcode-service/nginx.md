## macOS nginx configuration

```shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
brew install nginx
brew services start nginx
brew services stop nginx
brew services restart nginx
brew services reload nginx
brew services list
nginx -v
# 检查 Nginx 进程是否在运行
ps -ef | grep nginx
# 查找 Nginx 配置文件或二进制文件
# sudo find / -name "nginx" 2>/dev/null # 可能需要管理员权限
# 通过 Homebrew 检查
brew list | grep nginx


whereis nginx
nginx: /opt/homebrew/bin/nginx不是目录，而是二进制文件 /opt/homebrew/share/man/man8/nginx.8
# vim /etc/nginx/nginx.conf

# 查看配置文件位置
nginx -t
zhangwenzhi@bogon bin % nginx -t
nginx: the configuration file /opt/homebrew/etc/nginx/nginx.conf syntax is ok
nginx: configuration file /opt/homebrew/etc/nginx/nginx.conf test is successful
zhangwenzhi@bogon bin %  


# 查看 Homebrew 安装信息
brew info nginx
# nginx will load all files in /opt/homebrew/etc/nginx/servers/.



```



## 开发环境下的 Nginx 配置

servers/login.conf

```shell
server {
    # nginx.conf有80端口的配置，所以这里的8101端口可以自定义，如果还是设置80端口，会访问的是nginx.conf里的80端口的配置
    # 没修改过，默认的就是nginx的一个页面
    listen 8101;
    server_name localhost;
    
    # 加个前缀好像不管用，比如/fontend
    location / {
        # 代理到 Vite 开发服务器
        proxy_pass http://localhost:3002;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    # 处理后端 API 请求，假设所有以 /api 开头的请求都转发到后端服务
    location /api/ {
        # 代理到后端服务地址
        proxy_pass http://localhost:9096/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

## 生产环境下的 Nginx 配置

```shell
server {
    listen 8102;
    server_name localhost;

    # 处理前端静态文件
    root /path/to/your/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # 处理后端 API 请求，假设所有以 /api 开头的请求都转发到后端服务
    location /api/ {
        proxy_pass http://localhost:9096/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```