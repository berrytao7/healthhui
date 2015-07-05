# AnySearch

随便查，使用api store(http://apistore.baidu.com/apiworks/servicedetail/113.html)提供的服务进行查询。
使用volley作http请求框架，由于api store对于api请求的时候需要添加上自己注册帐号对应的app key信息，所以在volley代码中我进行了改动。具体位置见：

com.android.volley.toolbox.HurlStack.java

performRequest  方法中添加 

```java
//peterzhang add code for app key
connection.setRequestProperty("apikey","自己注册帐号后对应的apikey字符串")
