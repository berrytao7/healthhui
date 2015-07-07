# AnySearch

随便查，使用api store(http://apistore.baidu.com/apiworks/servicedetail/113.html)提供的服务进行查询。
使用volley作http请求框架，由于api store对于api请求的时候需要添加上自己注册帐号对应的app key信息，所以在volley代码中我进行了改动。具体位置见：

com.android.volley.toolbox.HurlStack.java

performRequest  方法中添加 

```ruby
//peterzhang add code for app key
connection.setRequestProperty("apikey","自己注册帐号后对应的apikey字符串")
```
#使用gradle构建
1. gradle clean  
2. gradle build //构建所有渠道的apk，包括360 91 wandoujia
3. gradle assembleWandoujia //构建wandoujia渠道的apk
4. 具体参考build.gradle文件。针对每一个渠道可以配置不同的变量值，具体参考buildConfigField
