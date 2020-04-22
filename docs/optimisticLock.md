# 乐观锁
### 一、为什么要使用乐观锁
秒杀业务大量地并发请求直接扣减商品库存数据会造成商品超卖问题，这时使用乐观锁来避免脏数据地提交。
### 二、解决方案
基于version实现乐观锁，在商品表中建立一个version字段，在扣减库存前先获取version，在扣减时先判断version是否与数据库中的一致，若一致则直接扣减库存再将version+1，若不一致则重新查询version并进行比较，这样在高并发的情况下只有当version前后一致时才会扣减库存，保证了数据库的一致性。
### 三、采用技术

### 四、代码实操
````java
    @Override
    public Boolean reduceStockByVersion(long goodsId) {
        int attempt = 0;
        int res = 0;
        SpikeGoods sg = new SpikeGoods();
        sg.setGoodsId(goodsId);
        do{
            attempt++;
            try{
                sg.setVersion(goodsMapper.getVersionByGoodsId(goodsId));//获取version
                res = goodsMapper.reduceStockByVersion(sg);//若version一致则扣减库存
            }catch(Exception e){
                logger.error("version不一致");
                e.printStackTrace();
            }
            if(res != 0) break;
        }while(attempt < MAX_TRIES);//MAX_TRIES是最多的尝试扣减库存次数
        return res > 0;
    }
````