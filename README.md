# 并发编程学习总结

### 1 线程安全性
##### 1.1 什么是线程安全
> 当**多个**线程访问某个类时, 不管运行时环境采用何种调度方式或者这些线程将如何交替执行, 并且在**主调代码**中**不**需要任何**额外的同步或协同**, 这个类都能表现出正确的行为, 那么就称这个类是**线程安全**的

##### 1.2 竞态条件(Race Condition)
> 当某个计算的正确性取决于多个线程的交替执行时, 那么就会发生竞态条件. 换句话说, 就是正确的结果要取决于运气. 最常见的竞态条件就是"**先检查后执行**(Check-Then-Act)"操作, 即通过一个可能失效的观测结果来决定下一步的动作.

##### 1.3 原子/原子操作
> 假定有两个操作A 和B, 如果从执行A 的线程来看, 当另一个线程执行B 时, 要么将B **全部执行完**, 要么**完全不执行**B, 那么A 和B 对彼此来说就是原子的. 原子操作是指, 对于访问同一个状态的所有操作(包括该操作本身)来说, 这个操作是一个以原子方式执行的操作.

##### 1.4 内置锁
- 独占性
- 可见性(经常被忽略)
    - volatile
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/volatile.png)

##### 1.5 用锁来保护状态
> 对于可能被多个线程同时访问的可变状态变量, 在访问它时都需要持有**同一**个锁, 在这种情况下, 我们称状态变量是由这个锁保护的.

- 多个状态变量下反例
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%A4%9A%E4%B8%AA%E7%8A%B6%E6%80%81%E5%8F%98%E9%87%8F%E4%B8%8B%E5%8F%8D%E4%BE%8B.png)

##### 1.6 可见性
- 非线程安全的可变整数类
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%9D%9E%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E7%9A%84%E5%8F%AF%E5%8F%98%E6%95%B4%E6%95%B0%E7%B1%BB.png)
- 线程安全的可变整数类
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E7%9A%84%E5%8F%AF%E5%8F%98%E6%95%B4%E6%95%B0%E7%B1%BB.png)

##### 1.7 volatile(保证可见性不保证原子性)
- 当且仅当满足以下所有条件时, 才应当使用volatile 变量(java 运算符对应多条jvm 指令)
    - 对变量的写入操作不依赖变量的当前值, 或者你能确保只有单个线程更新变量的值
    - 该变量不会与其他状态变量一并纳入不变性条件
    - 在访问变量时不需要加锁

##### 1.8 线程封闭(Thread Confinement)
> 当某个对象**封闭**在**一个线程**中是, 这种用法将自动实现线程安全性, 即使被封闭的对象本身不是线程安全的.
- ThreadLocal
- 动态数据源0
- MDC

##### 1.9 安全发布
- 不安全发布
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%8D%E5%AE%89%E5%85%A8%E5%8F%91%E5%B8%83.png)
- 安全发布的常用模式
    - 在静态初始化函数中初始化一个对象引用
    - 将对象引用保存到volatile 类型的域或者AtomicReference 对象中
    - 将对象引用保存到某个正确构造对象的final 类型域中
    - 将对象引用保存到一个由锁保护的域中

---

### 2 jdk concurrent util
##### 2.1 并发容器
- ConcurrentHashMap
    - ConcurrentModificationException
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ConcurrentModificationException1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ConcurrentModificationException2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ConcurrentModificationException3.png)
- CopyOnWriteArrayList
    - 在每次修改时, 都会创建并重新发布一个新的容器副本
    - 仅当迭代操作远远多于修改操作时, 才应该使用
- CopyOnWriteArraySet

##### 2.2 阻塞队列
- LinkedBlockingQueue
- ArrayBlockingQueue

##### 2.3 同步工具类
- CountDownLatch
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch.png)
- FutureTask
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/FutureTask.png)
- Semaphore
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/Semaphore.png)
- CyclicBarrier
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CyclicBarrier1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CyclicBarrier2.png)

---

### 3 Executor
##### 3.1 为何要使用Executor
- 简单web 服务器的几种方式
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%AE%80%E5%8D%95web%20%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%AE%80%E5%8D%95web%20%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%AE%80%E5%8D%95web%20%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F3.png)
- ThreadPool vs NewThread
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ThreadPool%20vs%20NewThread1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ThreadPool%20vs%20NewThread2.png)

##### 3.2 Executor 详细配置
- 静态工厂方法
    - newFixedThreadPool
    - newCachedThreadPool
    - newSingleTHreadExecutor
    - newScheduledThreadPool
- 生命周期
    - 使用ExecutorService 后会存在一非守护线程, 若不手动结束, 则JVM 不会退出
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F2.png)
- 设置线程池大小
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E8%AE%BE%E7%BD%AE%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%A4%A7%E5%B0%8F1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E8%AE%BE%E7%BD%AE%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%A4%A7%E5%B0%8F2.png)
    - Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
- 工作队列 ArrayBlockingQueue & LinkedBlockingQueue & SynchronousQueue
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%B7%A5%E4%BD%9C%E9%98%9F%E5%88%971.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%B7%A5%E4%BD%9C%E9%98%9F%E5%88%972.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%B7%A5%E4%BD%9C%E9%98%9F%E5%88%973.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%B7%A5%E4%BD%9C%E9%98%9F%E5%88%974.png)
- submit 源码分析
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/submit%20%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%901.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/submit%20%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%902.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/submit%20%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%903.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/submit%20%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%904.png)
- 饱和策略
    - 默认策略(AbortPolicy)
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%BB%98%E8%AE%A4%E7%AD%96%E7%95%A5%28AbortPolicy%291.png)
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%BB%98%E8%AE%A4%E7%AD%96%E7%95%A5%28AbortPolicy%292.png)
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%BB%98%E8%AE%A4%E7%AD%96%E7%95%A5%28AbortPolicy%293.png)
    - AbortPolicy
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/AbortPolicy.png)
    - CallerRunsPolicy
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CallerRunsPolicy1.png)
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CallerRunsPolicy2.png)
    - DiscardPolicy
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/DiscardPolicy.png)
    - DiscardOldestPolicy
        - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/DiscardOldestPolicy.png)
- 自定义线程工厂
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BA%BF%E7%A8%8B%E5%B7%A5%E5%8E%821.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BA%BF%E7%A8%8B%E5%B7%A5%E5%8E%822.png)

##### 3.3 Executor 注意事项
- compatible with ThreadLocal
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/compatible%20with%20ThreadLocal1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/compatible%20with%20ThreadLocal2.png)

---

### 4 取消与关闭
##### 4.1 已请求取消
- 标志位
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E6%A0%87%E5%BF%97%E4%BD%8D1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E6%A0%87%E5%BF%97%E4%BD%8D2.png)

##### 4.2 中断方法
- api
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/api.png)
 - InterruptException
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/InterruptException1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/InterruptException2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/InterruptException3.png)

##### 4.3 通过Future 来实现取消
- 通过Future 来取消任务
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%80%9A%E8%BF%87Future%20%E6%9D%A5%E5%8F%96%E6%B6%88%E4%BB%BB%E5%8A%A11.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%80%9A%E8%BF%87Future%20%E6%9D%A5%E5%8F%96%E6%B6%88%E4%BB%BB%E5%8A%A12.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%80%9A%E8%BF%87Future%20%E6%9D%A5%E5%8F%96%E6%B6%88%E4%BB%BB%E5%8A%A13.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%80%9A%E8%BF%87Future%20%E6%9D%A5%E5%8F%96%E6%B6%88%E4%BB%BB%E5%8A%A14.png)

##### 4.4 处理非正常的线程终止
- UncaughtExceptionHandler
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/UncaughtExceptionHandler1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/UncaughtExceptionHandler2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/UncaughtExceptionHandler3.png)

##### 4.5 JVM 关闭
- 守护线程
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%AE%88%E6%8A%A4%E7%BA%BF%E7%A8%8B.png)
- 关闭钩子
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%85%B3%E9%97%AD%E9%92%A9%E5%AD%90.png)

---

### 5 性能
##### 5.1 减少锁的竞争
- 缩小锁的范围
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%BC%A9%E5%B0%8F%E9%94%81%E7%9A%84%E8%8C%83%E5%9B%B41.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E7%BC%A9%E5%B0%8F%E9%94%81%E7%9A%84%E8%8C%83%E5%9B%B42.png)
- 减小锁的粒度
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%87%8F%E5%B0%8F%E9%94%81%E7%9A%84%E7%B2%92%E5%BA%A61.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%87%8F%E5%B0%8F%E9%94%81%E7%9A%84%E7%B2%92%E5%BA%A62.png)
- 锁分段
    - ConcurrentHashMap
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ConcurrentHashMap.png)

---

### 6 显示锁
##### 6.1 ReentrantLock
- lock
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/lock.png)
- tryLock
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/tryLock.png)
- tryLock(within time)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/tryLock%28within%20time%29.png)
- lockInterruptibly
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/lockInterruptibly.png)

##### 6.2 ReentrantReadWriteLock
- 使用场景
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%BD%BF%E7%94%A8%E5%9C%BA%E6%99%AF.png)
- 锁降级
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%94%81%E9%99%8D%E7%BA%A71.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%94%81%E9%99%8D%E7%BA%A72.png)

---

### 7 条件队列
##### 7.1 条件队列解决问题
- ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%97%E8%A7%A3%E5%86%B3%E9%97%AE%E9%A2%98.png)
> "条件队列"这个名字来源于: 它使得一组线程(称之为等待线程集合)能够通过某种方式来等待特定的条件变成真

- 为何要使用条件队列
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%BA%E4%BD%95%E8%A6%81%E4%BD%BF%E7%94%A8%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%971.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%BA%E4%BD%95%E8%A6%81%E4%BD%BF%E7%94%A8%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%972.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%BA%E4%BD%95%E8%A6%81%E4%BD%BF%E7%94%A8%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%973.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%BA%E4%BD%95%E8%A6%81%E4%BD%BF%E7%94%A8%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%974.png)

##### 7.2 wait/notify/notifyAll
- wait
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/wait1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/wait2.png)
- notify/nofityAll
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/notify_nofityAll1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/notify_nofityAll2.png)

##### 7.3 如何使用条件队列
- 条件谓词
    > 条件谓词是使某个操作成为状态依赖的前提条件. 在有界缓存中, 只有当缓存不为空时, take 方法才能执行, 否则必须等待. 对take 方法来说, 它的条件条件谓词就是"缓存不为空"

- 过早唤醒
    - nofityAll 时, 条件维持为真, 等到线程获取monitor 时, 条件为假
    - notifyAll 时, 条件为假, 如BoundedBuffer "非满" "非空" 条件谓词共用一个条件队列
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E8%BF%87%E6%97%A9%E5%94%A4%E9%86%92.png)
- 通知
    > 每当在等待一个条件时, 一定要确保在条件谓词变为真时, 通过某种方式发出通知
    
    - 若多个条件谓词等待同一条件队列, 则使用nofifyAll 而非notify, 否则容易产生信号丢失问题

##### 7.4 Condition
- 为何要使用
    - BoundedBuffer, 两个条件谓词均使用同一条件队列, 使用notifyAll, 造成性能损耗
    - 便于分析, 不同条件谓词对应不同条件队列, 对应不同signal 入口
- 与内置条件队列区别
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%8E%E5%86%85%E7%BD%AE%E6%9D%A1%E4%BB%B6%E9%98%9F%E5%88%97%E5%8C%BA%E5%88%AB.png)
- ArrayBlockingQueue 源码
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ArrayBlockingQueue%20%E6%BA%90%E7%A0%811.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ArrayBlockingQueue%20%E6%BA%90%E7%A0%812.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ArrayBlockingQueue%20%E6%BA%90%E7%A0%813.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ArrayBlockingQueue%20%E6%BA%90%E7%A0%814.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/ArrayBlockingQueue%20%E6%BA%90%E7%A0%815.png)
- LinkedBlockingQueue 源码
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/LinkedBlockingQueue%20%E6%BA%90%E7%A0%811.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/LinkedBlockingQueue%20%E6%BA%90%E7%A0%812.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/LinkedBlockingQueue%20%E6%BA%90%E7%A0%813.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/LinkedBlockingQueue%20%E6%BA%90%E7%A0%814.png)

##### 7.5 AbstractQueuedSynchronizer(AQS)
- 为何使用
    - 处理大量细节, 等待线程采用FIFO 队列操作顺序
    - lock, condition 两时刻阻塞; AQS 一时刻阻塞
-  AbstractQueuedSynchronizer
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/AbstractQueuedSynchronizer1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/AbstractQueuedSynchronizer2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/AbstractQueuedSynchronizer3.png)
- CountDownLatch
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch1.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch2.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch3.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch4.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch5.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch6.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch7.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch8.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/CountDownLatch9.png)

--- 

### 8 Java 内存模型
- 重排序
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E9%87%8D%E6%8E%92%E5%BA%8F.png)
- 不安全发布
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E4%B8%8D%E5%AE%89%E5%85%A8%E5%8F%91%E5%B8%83_%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B.png)
- 安全的发布
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%AE%89%E5%85%A8%E7%9A%84%E5%8F%91%E5%B8%831.png)
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%AE%89%E5%85%A8%E7%9A%84%E5%8F%91%E5%B8%832.png)
- 初始化过程的安全性
    - > 初始化安全性将确保, 对于被正确构造的对象, 所有线程都能看到由构造函数为对象各个final 域设置的正确值, 而不管采用何种方式来发布对象
    
    - ![image](http://zhyyu-learn.oss-cn-shenzhen.aliyuncs.com/learn-concurrency/%E5%88%9D%E5%A7%8B%E5%8C%96%E8%BF%87%E7%A8%8B%E7%9A%84%E5%AE%89%E5%85%A8%E6%80%A7.png)