## python macOS 多版本切换

```shell
which -a python3
which -a python

# /opt/homebrew/bin/python3
#/Library/Frameworks/Python.framework/Versions/3.10/bin/python3
#/usr/local/bin/python3
#/usr/bin/python3
```

```shell
py() {
  local PYTHON_ROOT="/Library/Frameworks/Python.framework/Versions"
  
  # 如果没有参数，显示可用版本
  if [ -z "$1" ]; then
    echo "Available Python versions:"
    ls -1 "$PYTHON_ROOT"
    return 1
  fi

  # 获取输入的版本号（如 "3.13" 或 "3.10"）
  local PYTHON_VERSION="$1"
  local PYTHON_PATH="$PYTHON_ROOT/$PYTHON_VERSION"

  # 检查版本路径是否存在
  if [ ! -d "$PYTHON_PATH" ]; then
    echo "Error: Python $PYTHON_VERSION not found in $PYTHON_ROOT"
    echo "Available versions:"
    ls -1 "$PYTHON_ROOT"
    return 1
  fi

  # 设置环境变量
  export PYTHON_HOME="$PYTHON_PATH"
  export PATH="$PYTHON_HOME/bin:$PATH"

  # 验证版本
  echo "Switched to Python $PYTHON_VERSION"
  python3 --version
}
```

```shell
source ~/.zshrc  # 或 source ~/.bash_profile
py 3.8   # 切换到 Python 3.8
py 3.9   # 切换到 Python 3.9
python --version
pip --version      # 确保 pip 也指向当前 Python 版本
echo $PYTHON_HOME  # 查看路径
```