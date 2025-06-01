## macOS curl请求

```shell
curl -X POST "http://localhost:9099/api/service" \
  -H "Content-Type: application/json" \
  -d '{
        "method": "login",
        "param": {
          "username": "admin",
          "password": "123456"
        }
      }'
```

```shell
{
  "header": {
    "timestamp": 1696300800000,
    "clientType": "web",
    "version": "1.0.0",
    "requestId": "req-20231004-123456"
  },
  "request": {
        "method": "login",
        "param": {
          "username": "admin",
          "password": "123456"
        }
      }
}
```


```shell
curl -X POST "http://localhost:9099/api/service" \
  -H "Content-Type: application/json" \
  -d '{
  "header": {
    "timestamp": 1696300800000,
    "clientType": "web",
    "version": "1.0.0",
    "requestId": "req-20231004-123456"
  },
  "request": {
        "method": "login",
        "param": {
          "username": "admin",
          "password": "123456"
        }
      }
}'
```