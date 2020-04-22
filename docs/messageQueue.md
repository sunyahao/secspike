# 本地标记与redis预处理
### 一、为什么要使用消息队列
高峰期时流量暴增，后端压力巨大，使用消息队列进行流量削峰能提高后端的性能。
### 二、解决方案
1、将用户信息和商品信息写入消息队列发送端，在接收端进行秒杀业务的具体处理。2、客户端不断地轮询秒杀结果
### 三、采用技术
RabbitMQ
### 四、代码实操
```java
//将用户信息和商品信息写入队列
//将用户id和商品id送入队列中
        OrderMessage message = new OrderMessage();
        message.setGoodsId(goodsId);
        message.setUserId(Long.valueOf(user.getUsername()));
        sender.sendOrderMessage(MQConfig.LOG_USER_EXCHANGE_NAME,MQConfig.LOG_USER_ROUTING_KEY,message);
        return Result.success(CodeMsg.SECSPIKE_WAIT);
```
```java
//在接收端进行业务地具体处理
@RabbitListener(queues = MQConfig.LOG_USER_QUEUE_NAME)
    public void receive(String message){
        System.out.println(1);
        OrderMessage msg = RedisService.stringToBean(message,OrderMessage.class);
        long userId = msg.getUserId();
        long goodsId = msg.getGoodsId();

        //因为消息队列有延迟性，所以每轮到一条消息都要判断该商品是否还有库存,避免超卖
        int stockCount = goodsService.getStockCount(goodsId);
        if(stockCount <= 0){
            log.info("用户"+userId+",商品号为"+goodsId+"的商品已售罄");
            return ;
        }

//        因为消息队列有延迟性，所以可能存在用户多次购买的现象，比如消息队列中有一条未被处理到的a的购买记录，而客户端的a再次购买了该商品，那么就会
//        进入到该队列中来
        OrderInfo orderInfo = orderService.getOrder(userId,goodsId);
        if(orderInfo != null){
            log.info("用户"+userId+"已经购买过商品号为"+goodsId+"的商品");
            return ;
        }
        spikeGoodsService.secSpike(userId, goodsId);
    }
```
```java
//回应前端地轮询请求
@PostMapping("/getResult")
    @ResponseBody
    public Result<String> getRes(@RequestParam("goodsId") long goodsId, HttpSession session){
        User user = (User) session.getAttribute("user");
        String orderId = spikeGoodsService.getSpikeResult(Long.valueOf(user.getUsername()), goodsId);
        return Result.success(orderId);
    }
```