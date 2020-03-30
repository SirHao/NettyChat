# NettyChat

基于Netty 4.1，使用Maven构建项目；自己动手再实现一遍：  掘金闪电侠《[Netty 入门与实战：仿写微信 IM 即时通讯系统](https://juejin.im/book/5b4bc28bf265da0f60130116/section)》



#### Update Log

+ 2020-3-13 新建项目，达成基于netty的C/S双向通信

+ 2020-3-15达成 序列化；协议设计、解析、编码；登录请求，登录响应；

  + server

  <img src="C:\Users\27751\AppData\Roaming\Typora\typora-user-images\image-20200315174914435.png" alt="image-20200315174914435" style="zoom:74%;" />

  + client

  <img src="C:\Users\27751\AppData\Roaming\Typora\typora-user-images\image-20200315174847651.png" alt="image-20200315174847651" style="zoom:73%;" />

+ 2020-3-30 新增登录之后，对于登录状态的存储，能够判断用户是否登录，如果登录，用户可以发送消息请求到服务端，服务端接收用户请求并通过消息响应包返回收到的消息；同时重构了handler，在pipline上将不同的包交给不同的Handler处理；尤其是统一复用接收时候的decode和encode;

  + server:

    <img src="C:\Users\27751\AppData\Roaming\Typora\typora-user-images\image-20200330152501128.png" alt="image-20200330152501128" style="zoom:70%;" />

  + client:

    <img src="C:\Users\27751\AppData\Roaming\Typora\typora-user-images\image-20200330152531541.png" alt="image-20200330152531541" style="zoom:60%;" />

> 目前存在bug:由于把登录的方式放在channel active的时候调用，scanner调用后关闭System in导致无法在其他地方使用Scanner读取命令行输入，目前先通过代码自动登录，后期迁移至其他地方登录



+ 优惠码

  <img src="C:\Users\27751\AppData\Roaming\Typora\typora-user-images\image-20200313174752116.png" alt="image-20200313174752116" style="zoom:80%;" />