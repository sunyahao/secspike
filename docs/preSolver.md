# 本地标记与redis预处理
### 一、为什么要先进行预处理
在秒杀高峰期时用户频繁的读写数据会导致数据库压力大增甚至挂掉，因此进行预处理能大大提高数据库性能。
### 二、解决方案
在前端进行请求拦截，将商品数量以及用户是否已购买过商品记录存储在本地和redis缓存中，如果商品已售罄或用户已购买过那么就拦截请求。
### 三、采用技术
HashMap+Redis
### 四、代码实操
````java
        boolean flag = spikeGoodsService.checkPath(goodsId,path);
        if(!flag){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //获取该商品的内存标记，true表示已经秒杀完毕
        User user = (User) session.getAttribute("user");
        if(!localOverMap.containsKey(goodsId)){
            initialInfo();
        }
        boolean over = localOverMap.get(goodsId);
        log.info("over:"+over);
        if(over){
            return Result.error(CodeMsg.SECSPIKE_OVER);
        }
        //该商品还未被秒杀完毕或还未初始化

        //判断商品是否售罄
        long stockCount = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
        /**
         * 小于0有两种情况：
         * 1、未被初始化，该商品数量还未被存储到redis中，所以下一步要进行初始化，初始化后如果大于0说明该商品的确是未被初始化造成的小于0
         * 2、该商品库存为0，这时候进行初始化时获得的商品列表中不包括该商品(会被canKill过滤)，因此在进行redis的decr操作时商品数量仍是小于0并且
         *    localOverMap仍是false,表示该商品已经售罄需要标记
         * 3、重新初始化，预减库存会造成缓存中的库存数量小于等于实际库存中的数量，原因：用户a多次点击抢购会造成多次预减库存而实际库存不变，
         *    因此当缓存中的库存为0时要将数据库中的数据与之同步
         */
        if(stockCount < 0){
            //假定未被初始化，初始化一次，如果该商品已被初始化过那么initialInfo()不会对该商品信息造成影响
            initialInfo();
            long stockCount2 = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
            //此时仍小于0说明该商品的确已售罄
            if(stockCount2 < 0){
                localOverMap.put(goodsId,true);
                return Result.error(CodeMsg.SECSPIKE_OVER);
            }
        }

        //判断用户是否重复秒杀
        if(redisService.exists(OrderKey.getSpikeOrderByUserAndGoods,""+goodsId+user.getUsername())){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        OrderInfo orderInfo = orderService.getOrder(Long.valueOf(user.getUsername()),goodsId);
        if(orderInfo != null){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
		
		public void initialInfo(){
        //获取当前还可以继续进行秒杀的商品列表
        List<GoodsVO> list = goodsService.getGoodsList();
        if(list == null) return ;
        for(GoodsVO goods:list){
            redisService.set(GoodsKey.getGoodsStock,String.valueOf(goods.getGoodsId()),goods.getStockCount());
            //false表示该id号的商品当前还有库存
            localOverMap.put(goods.getGoodsId(),false);
        }
    }
````
### 五、代码逻辑
```
由于采用了预减库存，所以缓存中的商品数量一定是小于等于数据库中的商品数量，那么从缓存中获得的商品数量小于0时要先将数据库中的商品数量同步到缓存中，再判断商品数量是否小于0，如果仍是小于0那么就说明商品已售罄，然后可以将该记录标记到本地中，下一次再次有人购买商品时可以不用访问缓存和数据库直接做出判断，提高了数据库性能。
```