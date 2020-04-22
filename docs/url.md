# 动态生成url隐藏秒杀接口
### 一、为什么要动态生成url隐藏秒杀接口
如果秒杀接口是固定的那么用户可能会直接通过该接口秒杀到商品。
### 二、解决方案
1、在秒杀前先向服务端请求得到秒杀地址(使用MD5生成)，将该地址存储到缓存中并返回到前端
2、前端通过这个url去访问秒杀接口
3、后端从缓存中得到url地址并与前端发送的url地址进行比较，如果相同则进行秒杀逻辑，未通过则返回非法请求
### 三、采用技术
Redis+MD5
### 四、代码实操
````java
    /**
     * 获取动态地址
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/getPath")
    @ResponseBody
    public Result<String> getPath(long goodsId){
        String path = null;
        if(redisService.exists(GoodsKey.getGoodsUrl,""+goodsId)){
            path =  redisService.get(GoodsKey.getGoodsUrl,""+goodsId, String.class);
            return Result.success(path);
        }
        path = spikeGoodsService.createPath(goodsId);
        return Result.success(path);
    }
````

```java
/**
     * 生成商品的动态地址
     * @param goodsId
     * @return
     */
    public String createPath(long goodsId){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(2);
        String path = MD5Util.md5(String.valueOf(idWorker.nextId()));
        redisService.set(GoodsKey.getGoodsUrl,""+goodsId,path);
        return path;
    }
```

```java
/**
     * 判断url地址是否与缓存中的相同
     * @param goodsId
     * @param path
     * @return
     */
    public Boolean checkPath(long goodsId, String path){
        if(path == null){
            return false;
        }
        String path1 = redisService.get(GoodsKey.getGoodsUrl,""+goodsId,String.class);
        if(path1.equals(path)){
            return true;
        }
        return false;
    }
```