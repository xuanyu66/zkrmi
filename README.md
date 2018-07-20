# zkrmi
  RMI:远程方法调用(Remote Method Invocation)。能够让在客户端Java虚拟机上的对象像调用本地对象一样调用服务端java 虚拟机中的对象上的方法。

 ![image](https://github.com/ButBueatiful/dotvim/raw/master/screenshots/vim-screenshot.jpg)
  #### RMI远程调用步骤：

1，客户调用客户端辅助对象stub上的方法

2，客户端辅助对象stub打包调用信息（变量，方法名），通过网络发送给服务端辅助对象skeleton

3，服务端辅助对象skeleton将客户端辅助对象发送来的信息解包，找出真正被调用的方法以及该方法所在对象

4，调用真正服务对象上的真正方法，并将结果返回给服务端辅助对象skeleton

5，服务端辅助对象将结果打包，发送给客户端辅助对象stub

6，客户端辅助对象将返回值解包，返回给调用者

7，客户获得返回值
