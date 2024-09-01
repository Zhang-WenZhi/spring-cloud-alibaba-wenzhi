## 空.git项目上传远程仓库(简化)

```shell
git init -b main
git remote add origin https://github.com/Zhang-WenZhi/spring-cloud-alibaba-wenzhi.git
git pull origin main:main
# 这一步才可以看到main分支
git branch
git add .
git commit -m "init"
# git push -u origin main
git push origin main:main

```
