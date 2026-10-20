# TicketRush Database

这个目录提供和当前 Spring Boot + MyBatis-Plus 项目适配的 MySQL 建库脚本。

## 使用方式

```bash
mysql -u root -p < database/ticketrush_mysql.sql
```

项目当前连接配置在 `src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticketrush
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

## 表说明

- `events`：当前 `/api/events` 接口实际使用的活动表。
- `users`：为注册/登录功能预留的用户表。
- `ticket_orders`：为后续抢票订单流程预留的订单表。
- `stock_logs`：为库存扣减、释放、审计预留的库存流水表。

当前代码只直接使用 `events` 表。后续添加用户、下单、支付或库存锁定逻辑时，可以在现有表结构上继续扩展 Mapper、实体和 Service。
