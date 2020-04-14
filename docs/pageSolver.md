# 页面级缓存
### 一、为什么要用页面级缓存
在秒杀时用户会频繁刷新页面，如果不使用缓存那么页面加载速度会很慢甚至会因为频繁访问数据库而使其崩溃。
### 二、解决方案
采用页面静态化技术，在服务器请求到来之前生成对应的html，在请求到来时再填充数据，再将这样的静态化页面设置有效时间并存储到缓存中，降低与数据库的交互次数，从而提高页面的访问速度。
### 三、采用技术
thymeleafViewResolver+Redis
### 四、代码实操
````java
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);//从缓存中获取html源代码
        if (!StringUtils.isEmpty(html)) {
            return html;
        }//如果html源代码成功从缓存中取到那就直接返回
        List<GoodsVO> goodsList = goodsService.getGoodsList();
        log.info("待秒杀商品的列表",goodsList);
        model.addAttribute("goodsList", goodsList);

        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
````
### 五、代码逻辑
```
从后端获得商品列表数据并将其和静态页面手动渲染，将渲染后的模板文件存储到缓存中并返回。
```
### 六、手动渲染过程解析
```
1、WebContext是thymeleaf的模板上下文对象，thymeleaf用其携带数据。
2、TemplateEngine是模板引擎对象，是用来解析对应的模板文件并将动态生成的数据和静态的模板页面组成视图文件的一个工具。
3、调用TemplateEngine的process方法后thymeleaf就会根据配置好的路径找到对应的模板并拼装动态数据和静态页面，然后返回视图文件了。