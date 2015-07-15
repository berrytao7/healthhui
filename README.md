# Healthhui-健康汇
<br>最贴心的健康助手</br>

###遵从的Android开发规范
https://github.com/futurice/android-best-practices
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
###使用的开发工具
  1. [android studio](http://www.android-studio.org/)
  2. [markman](http://www.getmarkman.com/)

