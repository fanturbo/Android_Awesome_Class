## Android_Awesome_Class
aswome class used in android
### 1.CenteredImageSpan.java
参考链接：http://stackoverflow.com/questions/25628258/align-text-around-imagespan-center-vertical

效果：图文混排是文字和图片垂直居中显示
### 2.HttpUtil.java

效果：写小例子需要简单的网络请求的时候使用
### 3.MySwipeRefreshLayout.java
源码链接：https://github.com/Freelander/Elephant

效果图：和知乎客户端差不多
### 4.RoundImageView.java
参考链接：http://blog.csdn.net/lmj623565791/article/details/41967509

效果：见名知意啊！RoundImageView!

### 5.SwipeBackFrameLayout.java
参考链接：http://www.jianshu.com/p/59be4551c418

效果： ViewDragHelper实现Activity滑动跳转
### 6.ToggleButton.java
参考链接：https://github.com/zcweng/ToggleButton
在library里用到了facebook的rebound library，所以如果有冲突的情况（比如别的library也引用了rebound）的话直接复制此类单独使用。
### 7.TokenInterceptor.java
效果：retorfit拦截器重新组装请求体例子
一般情况下比如说token失效的话intercepter里直接addHeader重新请求的话是没有问题的，但是如果requestBody变化的话在网上没有找到相关例子，所以写了这个。
### 8.VerticalPager.java
参考链接：https://github.com/grantland/android-verticalpager

效果图：竖直方向的viewPager
### 9.WebViewFragment.java
参考链接：
http://blog.isming.me/2015/12/21/android-webview-upload-file/

效果：当html页面中的js执行获取文件打开手机上的文件浏览器（或者相册），Fragment（或者Activity）会首先拿到result，应该传递给js。注意：对3.0之前,3.0之后5.0之前，4.1三种情况的系统分贝处理。
