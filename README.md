# Healthhui-健康汇
<br>最贴心的健康助手</br>

###产品主要功能
  1. 健康咨询、健康食谱、医院大全等健康知识相关的信息聚合。
  2. 用户可以注册，登陆。
  3. 具有社交分享功能
  4. 未完待续

### 遵从的Android开发规范
https://github.com/futurice/android-best-practices/blob/master/translations/Chinese/README.cn.md
 1. 使用volley作http请求框架，由于apistore对于api请求的时候需要添加上自己注册帐号对应的appkey信息，所以在volley代码中我进行了改动。具体位置见：com.android.volley.toolbox.HurlStack.java performRequest  方法中修改：
 2. 使用Fragment替代Activity管理UI,便于后续可能对平板设配的适配。

###开发方式

###工程结构

###架构模型
  1. [Altlas](https://github.com/bunnyblue/OpenAtlas)，插件化开发(尚未启用)。

###打包方式

####使用gradle构建
1. gradle clean  
2. gradle build //构建所有渠道的apk，包括360 91 wandoujia
3. gradle assembleWandoujia //构建wandoujia渠道的apk
4. 具体参考build.gradle文件。针对每一个渠道可以配置不同的变量值，具体参考buildConfigField

###依赖的第三方库和服务
  1. [api store](http://apistore.baidu.com/)
  2. [volley](https://github.com/zhhp1121/Volley)
  3. [avos](https://leancloud.cn/?)(尚未使用)
  
####依赖apistore里面的服务有
  1. [健康咨询](http://apistore.baidu.com/apiworks/servicedetail/151.html)
  2. [健康食谱](http://apistore.baidu.com/apiworks/servicedetail/149.html) (尚未完成)
  3. [健康图书](http://apistore.baidu.com/apiworks/servicedetail/171.html) (尚未完成)
  4. [健康一问](http://apistore.baidu.com/apiworks/servicedetail/165.html) (尚未完成)
  5. [健康知识](http://apistore.baidu.com/apiworks/servicedetail/162.html) (尚未完成)
  6. [药品大全](http://apistore.baidu.com/apiworks/servicedetail/152.html) (尚未完成)
  7. [医院大全](http://apistore.baidu.com/apiworks/servicedetail/145.html) (尚未完成)
 
###使用的开发工具
  1. [android studio](http://www.android-studio.org/)
  2. [markman](http://www.getmarkman.com/)

