## macOS brew用不了的情况下，java多版本切换

```shell
# 查看所有java版本
/usr/libexec/java_home -V

# 切换到指定版本[临时切换 JDK 版本]
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

# 切换到指定版本[永久切换 JDK 版本]
sudo ln -sfn /Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home /Library/Java/JavaVirtualMachines/jdk1.8.0
sudo ln -sfn /Library/Java/JavaVirtualMachines/jdk-17.0.1.jdk/Contents/Home /Library/Java/JavaVirtualMachines/jdk17.0.1

/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home

## zsh 配置
sudo ln -sfn /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home /Library/Java/JavaVirtualMachines/jdk17
sudo ln -sfn /Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home /Library/Java/JavaVirtualMachines/jdk23

# 验证
java -version

# 配置文件中添加环境变量
vim ~/.zshrc
# 添加以下内容
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
export PATH=$JAVA_HOME/bin:$PATH

source ~/.zshrc

vim ~/.bash_profile
# 添加以下内容
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
export PATH=$JAVA_HOME/bin:$PATH

source ~/.bash_profile

# .bash_profile 在 哪儿
echo $HOME/.bash_profile

# 删除.bash_profile 
rm $HOME/.bash_profile
rm ~/.bash_profile
# 验证
echo $HOME

```

`macOS java多版本切换【实际用的是这个】`

```shell
# 定义 JDK 路径
export JDK17_HOME="/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
export JDK23_HOME="/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home"

# 默认使用 JDK 17
export JAVA_HOME=$JDK17_HOME

# 将 JDK 的 bin 目录添加到 PATH
export PATH="$JAVA_HOME/bin:$PATH"

# 定义切换版本的函数
jdk() {
  if [ "$1" = "17" ]; then
    export JAVA_HOME=$JDK17_HOME
  elif [ "$1" = "23" ]; then
    export JAVA_HOME=$JDK23_HOME
  else
    echo "Unknown JDK version. Supported versions: 17, 23"
  fi
  # 更新 PATH
  export PATH="$JAVA_HOME/bin:$PATH"
  # 验证版本
  java -version
}
```
```shell
# 重新加载配置文件
source ~/.zshrc   # 或 source ~/.bash_profile

# 切换到 JDK 17
jdk 17

# 切换到 JDK 23
jdk 23

java -version       # 查看 Java 版本
echo $JAVA_HOME     # 查看当前 JAVA_HOME 路径

# 初始状态（默认 JDK 17）
$ java -version
openjdk version "17.0.5" 2022-10-18

# 切换到 JDK 23
$ jdk 23
openjdk version "23" 2024-03-19

# 再切换回 JDK 17
$ jdk 17
openjdk version "17.0.5" 2022-10-18

检查 jdk 函数是否正确定义
$ type jdk
# jdk is a shell function from /Users/zhangwenzhi/.zshrc
```