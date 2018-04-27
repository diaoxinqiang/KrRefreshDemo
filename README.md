# KrRefreshDemo
根据《36氪》反编译提取下拉刷新控件,仅供学习使用
#### 博客地址:[android逆向工程之顺手牵羊(36氪,作业盒子)](https://www.jianshu.com/p/e29a5aa4d49d)

####  反编译效果展示
#####  《36氪》下拉刷新([APK下载地址](http://download.36kr.com/)) 

各位老铁可以下载体验一下这个app的***下拉刷新***,绝对让你很有收获
###### 原生效果:
![36氪下拉刷新.gif](https://upload-images.jianshu.io/upload_images/1232173-71790b7ec1ca9dfe.gif)
###### 《36氪》下拉刷新控件评价
- **非常流畅**,能极大缓解用户等待数据时的**焦灼感**. 这点非常牛,市面上的应用多如牛毛,其中很多应用下拉刷新控件的质量却很堪忧.鄙人以为《36氪》的下拉刷新体验在应用市场上绝对名列前茅.他们的UE交互设计师一定花了很多时间精力设计它.
- 下拉时有缩放效果,等待刷新时填充图标的动效,整个过程**行云流水**
- 有一种美感在其中.

**那么问题来了,假如你的项目中要实现这样一个控件,你需要多长时间?**
1天? 2天?
我的答案是**1个小时**
反编译提取控件,理解其中绘制逻辑.找UI改改样式,over.


###### 反编译提取控件效果:
![36氪-下拉刷新控件提取.gif](https://upload-images.jianshu.io/upload_images/1232173-33437801eeba5a49.gif?imageMogr2/auto-orient/strip)


嘻嘻,下面我会给个关于反编译技巧的教程,市面上大多数的应用脱了裤子给我们看,刺激吧?

####2. 《36氪》下拉刷新:

##### 2.1正常情况下,不考虑反编译,自己实现的思路分析:
1. 实现跟随用户上拉下拉 蓝色方框的缩放动画(使用值动画)
2. 刷新过程中的动画,如果使用自定义view画出来,估计成本会比较高,如果使用lottie会省事很多
3. 不过做出来说不定还有bug,使用反编译,这样的担忧会减轻很多,毕竟36kr这个下拉控件经过庞大用户无数次的考验.



##### 2.2通过反编译提取
######  2.2.1目标:
还原实现下拉刷新控件
######  2.2.2 会遇到的困难:
 ①控件位置难找;
②资源文件分散;
③代码经过混淆,代码逻辑需要跟着作者实现思路走一遍
######  2.2.3 提取过程:
1. 提取jar包以及资源文件
使用apktool 或使用反编译集成工具
这一步没啥难度,建议读者想跟着实践一下的话,首选反编译集成工具.
用命令行工具会很麻烦,光是插件的安装就这么多,更别提安装过程的环境问题.
>1.ShakaApkTool
2.Apktool
3.Dex2Jar
4.Zipalign
5.SignApk
6.JDGUI

我是直接github上找到一个mac工具软件:[android crack tool]([https://github.com/Jermic/Android-Crack-Tool](https://github.com/Jermic/Android-Crack-Tool)
)
![android crack tool.png](https://upload-images.jianshu.io/upload_images/1232173-776f89d8469cef81.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

傻瓜化操作后获得如下文件:

![image.png](https://upload-images.jianshu.io/upload_images/1232173-110f8f90796362a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2.随便新建一个AS项目,将jar包添加到libs 然后add as library

3.在android studio中使用analyse apk
![找到项目路径.png](https://upload-images.jianshu.io/upload_images/1232173-f11a9083cb65f918.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

4.定位下拉刷新控件的代码位置

这一步需要耐心,因为不太好找,需要猜一下.

a,找到mainActivity

![mainActivity位置](https://upload-images.jianshu.io/upload_images/1232173-acf68506c4b2bde0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

b.在mainActivity中找到使用该控件的fragment

![找到使用该控件的fragment.png](https://upload-images.jianshu.io/upload_images/1232173-a1922d6e01e8c8bd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


c.**关键**:在fragment中找到控件(需要一点点小耐心)
一开始没找着,想了一下,这个fragment肯定是使用了下拉刷新的,位置没找错.
那问题出现在哪里呢?

![HomeFragment2中没有这个控件.png](https://upload-images.jianshu.io/upload_images/1232173-b2bf2a0084141e75.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
推测36Kr的程序员对这个下拉刷新动作进行了封装.可能在BaseFragment中,然而也没有!
同样的,在Rxfragment中也没找着.

![BaseFragment没找到这个控件.png](https://upload-images.jianshu.io/upload_images/1232173-d375506982b87a7b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

回到HomeFragment2中,看能不能找到点蛛丝马迹

果然找到了一点线索:有关于refresh关键字

![image.png](https://upload-images.jianshu.io/upload_images/1232173-eb3c79355423172a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

原来HomeFragment2是另一个fragment的容器,找错位置了,回到MainActivity中找其他fragment
在SubscribeHomeFragment中马上就找到了

![image.png](https://upload-images.jianshu.io/upload_images/1232173-4de22aa7ee09e148.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1232173-b604ef4c41bce2dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1232173-4a218cfe4d3692c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
in.srain.cube.views.ptr.PtrFrameLayout //第一反应是网上的开源库,github上一搜索,果然~
````
36Kr使用比较出名的下拉刷新库github地址:**[android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)**

d.根据下拉刷新头部KrHeader以及资源R文件定位资源文件

![layout的id.png](https://upload-images.jianshu.io/upload_images/1232173-b5100e6fc4cd7a0c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![布局文件id.png](https://upload-images.jianshu.io/upload_images/1232173-99933818c6ce9010.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
根据header_kr这个id去搜索定位布局文件

e. 根据KrHeader的变量LottieAnimationView b找到lottie动画
根据[lottie文档官网](http://airbnb.io/lottie/android/android.html#getting-started),动画文件一般放在asserts文件或res/raw中
![image.png](https://upload-images.jianshu.io/upload_images/1232173-fe7acde070d5ed5e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


至此,这个控件已经被完完全全的抽取出来了

![image.png](https://upload-images.jianshu.io/upload_images/1232173-016b38467acceca2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
