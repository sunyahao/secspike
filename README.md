## 这个系统做了什么
该系统实现了基本的登录功能、查看商品列表、查看商品详情、秒杀、下单、查看我的订单、注销等基本功能，完成了一个基本的秒杀业务。
## 开发工具
IntelliJ IDEA + Git + Navicat for Mysql + Chrome
## 压测工具
Jmeter
## 优化前系统业务流程图
![业务流程图](https://github.com/sunyahao/secspike/blob/master/src/main/resources/static/img/%E7%A7%92%E6%9D%80%E4%B8%9A%E5%8A%A1%E9%80%BB%E8%BE%91.png "业务流程图")
## 对流程图中各步骤进行优化
|ProcessID   |Problem   |Solution   |
| :------------ | :------------ | :------------ |
|P1&P2   |秒杀开始时系统承受极大的流量直接宕掉   |[页面级缓存thymeleafViewResolver](http://github "页面级缓存thymeleafViewResolver")   |
|P3&P4   |高峰期直接从数据库获取信息会让数据库直接挂掉   |[本地标记+redis预处理](http://1 "redis对象缓存")   |
|P5   |高峰期直接扣减库存往往会产生卖超问题   |[消息队列(请求入队列缓冲，异步下单，减少数据库压力)，客户端轮询](http://1 "消息队列")   |
|p5   |高并发写入数据库时会产生一致性问题   |[乐观锁(版本号机制)](http://1 "乐观锁(版本号机制)")   |
|P6   |高峰期订单ID可能会产生冲突   |[雪花算法](http://1 "雪花算法")   |
## 其他方面的一些优化
|ID   |Problem   |Solution   |
| :------------ | :------------ | :------------ |
|01   |用户直接通过url去访问秒杀接口   |[动态生成url隐藏秒杀接口](http://1 "动态生成url隐藏秒杀接口")   |