# ZcrproAndroid

### 项目简介

google官方推出了mvp架构之后一直没有时间去细看，最近正好赶上公司项目上3.0的技术选型便拉下来研究了一番，看完之后感触良多，针对于公司的业务自己也来定制一番



### 一：Model层

#### 1.网络请求

[OkHttp](http://square.github.io/okhttp/) 和 [Retrofit](http://square.github.io/retrofit/) 

> **OkHttp 是安卓6.0以后的官方 Http Client**，而 **Retrofit 则让我们只需要定义一个 Java interface 就可以发起 RESTful API 请求**，它们都是 Square 公司的开源项目，这里我使用的分别是 Okhttp3 和 Retrofit2。

#### 2.持久化

 [SqlDelight](https://github.com/square/sqldelight) 和 [SqlBrite](https://github.com/square/sqlbrite) 

> SqlDelight 可以**根据建表的 SQL 语句自动生成 Java model interface，interface 的每个方法就是这张表的每一列**。SqlDelight通过从 SQL 语句来生成 JAVA 模型代码。这样的好处是，所有 SQL 语句都位于同一个位置，通过查看 SQL 语句可以清楚的了解需要实现的功能和数据库的结构,也便于管理以及java类访问。

>SqlBrite是对 Android 系统的 SQLiteOpenHelper 的封装，对SQL操作引入了响应式语义 （Rx）（用来在 RxJava 中使用）

#### 3.Immutable/Value types

这个概念对有些朋友来说可能还比较陌生，简单来说就是一个**数据对象**一旦构造完成，就再也无法修改了。这样有什么好处呢？最大的好处就是多线程访问可以省去很多同步控制，因为它们是不可变的，一旦构造完成，就不会存在多线程竞争访问问题了。更多关于 immutable 的介绍，可以[参阅 wikipedia](https://en.wikipedia.org/wiki/Immutable_object)。

为了让我们的对象具有 immutable 的特性，我使用了 Google 的 [AutoValue](https://github.com/google/auto/tree/master/value) 库，**AutoValue 可以根据我们定义的 abstract 类自动生成具有 immutable 特性的实现类，还能替我们处理好 equals，null 安全性，以及实现 builder 模式**。

#### 4.数据库访问

数据库访问使用的就是上文提到的 [SqlBrite](https://github.com/square/sqlbrite) 了。SqlBrite 对 SQLiteOpenHelper 和 ContentResolver 进行了轻量的封装，同时为查询操作提供 Reactive API（响应式编程），后续对数据表的操作，都会触发 Observable 的更新，让我们各个界面的数据同步问题得以优雅的解决。

响应式编程已经被提出来很久了，RxJava 这个 Java 语言的响应式编程库也发布两年了，它和我们熟知的 *观察者模式* 有一定相似之处，订阅者可以订阅数据源，除了观察者模式，RxJava 还具备对事件流的强大处理能力，更多关于 RxJava 的内容，建议阅读这篇[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083)。

设想这样的场景，我们有一个好友列表界面，展示每个好友的信息，其中一项就是备注名称，点击某个好友的那一列，我们就进入到一个好友的 profile 界面，展示好友完整的个人信息，再点击这个界面的编辑按钮，进入到一个编辑备注名称的界面。在最后的编辑界面编辑完成之后，我们怎么保证前面的两个界面上展示的数据都能够同步更新？

实现方法有很多，但我觉得下面的这种方式几乎是最优的：所有界面展示的数据都是直接从数据源（DB）获取的，数据源一旦更新，就通知所有的界面更新数据，这样所有的界面显示的数据都是不需要维护的，这和 React 的 props 思想类似，我们不是通过修改（维护）界面持有的数据来更新界面，而是每次都给界面一个完整的最新的数据，这样能很大程度上简化界面的代码。实际上数据本来就只有一份（数据源那里），这样做既省去了我们进行数据一致性维护的复杂逻辑，也是非常直观的。

SqlBrite 提供的 Reactive API 很好地符合这一特性，而 Rx 提供的强大的事件流处理机制，也让我们后续的有些逻辑实现起来非常简洁。此前我一直使用 [StorIO](https://github.com/pushtorefresh/storio) 来提供 Rx API，除了 Rx API，StorIO 还对数据库访问进行了高度封装，我们访问数据库只需要进行三种操作：get，put，delete。但它的封装程度过高，导致我们很难定制一些特定的需求。

例如全量更新一张数据表，StorIO 只能先执行一次 delete all，在执行一次 put all，而它们又是无法在同一个 transaction 中进行的，实际上 StorIO 并没有暴露出 transaction 接口，它只是保证 put 一个 list 时会使用 transaction。这样一次全量更新会触发 subscriber 的两次更新，会让我们的界面在强制刷新时经历一个 `有 -> 无 -> 有` 的变化，比较影响用户体验。

SqlBrite 则和 StorIO 完全相反，几乎没有进行封装，我们使用的都是 DB 的 API，**API 的封装程度越低，我们所获得的能力就越强大，当然我们需要为此付出相应的代价**。使用 SqlBrite 进行 DB 访问时，我们还需要和 Cursor 打交道，不过这当然难不倒我们这群聪明的程序员，所以我才果断放弃了 StorIO。我们完全可以自己在 SqlBrite 的基础上进行一些封装，简化使用方的代码，同时我们又具备了更高的可定制能力。

#### 5.Json 序列化与反序列化

在这里我引入了 [auto-value-gson](https://github.com/rharter/auto-value-gson) 来解决这个问题，它是一个 AutoValue 的扩展，**可以自动生成 gson adapter 代码**。关于 AutoValue 的扩展，感兴趣的朋友可以阅读 [auto-value-gson 作者的这篇文章](http://ryanharter.com/blog/2016/03/22/autovalue/)。AutoValue 及其系列扩展可以大大减少我们要编写的代码，而且生成的代码正确性和效率都经过了广泛的测试，值得信赖，非常建议大家了解和使用。

#### Model层最终

_____> Observable<T>   DATABASE

_____> Observable<T>   RX

有兴趣的可以看看大神[Jack Wharton在国外著名新闻站点Reddit中的回答](https://www.reddit.com/r/androiddev/comments/48yieg/anyone_use_sqlbrite_andor_sqldelight/)。这里做出翻译。

SqlBrite和SqlDelight都是对象映射（OM，Object Mappers）而不是对象关系映射（ORM，Object/Relational Mappers）。

ORM 其实并不是一个优秀的框架。很多平台的 ORM 实现都有性能和内存的问题。我们也不会编写ORM。

SqlBrite只是让你方便在 RxJava 中使用 Sql 操作而已，并且额外添加了对数据库表数据更新通知的机制。只是一个 SQLiteOpenHelper 的轻量级封装，并不关心你的对象是如何实现的，也不关心你的数据库。同样，SqlBrite也不支持对象映射和类型安全的查询，通常这些功能并不比直接使用SQL 语句更加方便。虽然在 Java 中操作 SQL 语言有一个比较好的框架 — jOOQ 。但是在 Android 中使用 jOOQ 就是杀鸡用牛刀了！

SqlDelight 的做法是从 SQL 语句来生成 JAVA 模型代码。 这样的好处是，所有 SQL 语句都位于同一个位置，通过查看 SQL 语句可以清楚的了解需要实现的功能和数据库的结构。SqlDelight 添加了对 SQL 语句的编译时验证、表名字和列名字的代码自动完成功能。让编写 SQL 语句更加快捷。在编译的时候，根据 SQL 语句生成 Java 模型接口和 builder 来把数据行和 Java 对象实现转换。虽然这个框架还很年轻，但是通过这个框架目前的功能你就可以发现，SqlDelight 不会变成一个 ORM 框架。并且不会做很重的功能（比如数据懒加载、缓存 、级联删除 等 ORM 框架内常见的功能） 。

SqlDelight 大部分代码都是编译时用的，真正的运行时代码（包含在你应用中的代码）只有10几行代码几个接口而已。它将会使你的SQL编写更加简单，迁移到上面这两个库也会非常的简单，同时你也能享受到响应式的查询，类型安全的对象映射和编译的优点。

这两个框架将不会实现那些ORM框架强制要求你做的事情下面这些功能：

- 不会成为 Java 语言中功能不够全面的数据库查询 API
- 不会实现把外键映射为 Java 对象集合（关系映射）
- 不会有泛字符类型（string-ly typed）的表名字和列名字的引用
- 不会有一个基类需要你的数据库操作对象来继承该类
- 不会在 Java 中定义数据库表，比如通过注解、或者继承一个类等
- 不会自动创建数据表和迁移数据表
- 不会对 Sql 查询和 Java 对象做线程限制
- 不会返回可变的对象，你修改该对象的值，然后调用 save 函数就可以把更新的值保存到数据库了。

SqlBrite 仅仅是一个用来协调更新数据和通知数据变化的轻量级封装，当你对数据表进行操作的时候，其他订阅者可以在数据发生变化的时候收到通知。然后可以用 RxJava 的方式来操作数据。

SqlBrite 不是一个 ORM 框架，也不是一个类型安全的查询框架。不会提供类似Gson中对象序列化的功能，也不会提供数据库迁移的功能。其中的一些功能由可以与SqlBrite一起使用的 [SQLDelight](https://github.com/square/sqldelight/)提供。

这次选型的model层和以往差距较大所以最先要提一下，上述内容只是对model层工具及用意的描述所以自己就不花时间去重复写了多为转载自简书作者：[CameloeAnthony ](http://www.jianshu.com/users/44872eaffa8b)和Piasy：http://blog.piasy.com/ ，这样的设计只在国内看到这两位有自己出过博客，资料都比较少。

### 二：MVP架构

https://github.com/googlesamples/android-architecture 上来先放链接，这么经典的东西我也就不想多说了，一个记事本demo写出了一套系统的感觉，功能虽轻，但对开闭原则和可扩展行做的好犀利。

拿demo里边的代码片段来说一下

1.Contract接口来约定view的方法以及presenter的方法

```java
/**
 * This specifies the contract between the view and the presenter.
 */
public interface DailyContract {

    interface View extends BaseView<Presenter> {

        void showLoadingUi(boolean active);

        void showError(String error_info);

        void showEmpty();

        void showDaily(Daily daily);

    }

    interface Presenter extends BasePresenter {
        void loadDaily(boolean forceUpdate);
    }
}
```

2.使用Fragment作为view的实现类

```java
/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyFragment extends android.support.v4.app.Fragment implements DailyContract.View {

    private TextView tv_name;
    private TextView tv_author;

    private DailyContract.Presenter mPresenter;

    public DailyFragment() {
        // Requires empty public constructor
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.daily_fragment, container, false);

        tv_name = (TextView) root.findViewById(R.id.name);
        tv_author = (TextView) root.findViewById(R.id.author);

//        mPresenter.loadDaily(true);

        return root;

    }

    @Override
    public void showLoadingUi(boolean active) {
        if (active){
            if (BuildConfig.DEBUG) Log.d("DailyFragment", "显示正在加载view......");
        }else {
            if (BuildConfig.DEBUG) Log.d("DailyFragment", "加载view完成,关闭加载框......");
        }

    }

    @Override
    public void showError(String error_info) {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "加载view失败"+error_info);
    }

    @Override
    public void showEmpty() {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "--加载成功--数据为空--");
    }

    @Override
    public void showDaily(Daily daily) {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "---数据获取成功---daily:" + daily.toString());
           tv_name.setText(daily.name());
           tv_author.setText(daily.description());
    }

    @Override
    public void setPresenter(DailyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
```

可以看到Fragment此时非常清晰，实现了在Contract中定义的所有方法

3.Presenter对于View（Fragment）和 Model层的数据拼接

```java
/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyPresenter implements DailyContract.Presenter {

    @NonNull
    private Context context;

    @NonNull
    private final DailyContract.View mDailyView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    public DailyPresenter(Context context, @NonNull DailyContract.View mDailyView, @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.context = context;
        this.mDailyView = mDailyView;
        this.mSchedulerProvider = mSchedulerProvider;

        mDailyView = checkNotNull(mDailyView);
        context = checkNotNull(context);
        mSchedulerProvider = checkNotNull(mSchedulerProvider);

        mSubscriptions = new CompositeSubscription();
        mDailyView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDaily(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadDaily(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadDaily(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadDaily(final boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            mDailyView.showLoadingUi(true);
        }
        if (!forceUpdate) {
            //从缓存中取
        }

        mSubscriptions.clear();

        RetrofitFactory.setBaseUrl(BaseUrl.Daily.DAILY);
        DailyService dailyService = ApiFactory.getFactory().create(DailyService.class);
        DailyApi dailyApi = new DailyApiImpl(dailyService, new DailyDaoImpl(DataBaseManager.getBriteDatabase(context)));

        Subscription subscription = dailyApi.getDaily()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<Daily>() {
                    @Override
                    public void onCompleted() {
                        mDailyView.showLoadingUi(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDailyView.showError(e.toString());
                    }

                    @Override
                    public void onNext(Daily daily) {
                        mDailyView.showDaily(daily);
                    }

                });

        mSubscriptions.add(subscription);
    }
}
```

数据来源于Model层中所暴露出来的

 _____> Observable<T>   DATABASE

_____> Observable<T>   RX



## Next Version 

1.加入NetworkingService 对网络实施监听，避免出现网络缓慢导致的用户体验性问题。

2.加入crash管理机制对日志的上传和crash之后显示一个友好的界面

3.对Sharepreference的改造—参考SqlBrite对 Android 系统的 SQLiteOpenHelper 的封装，对SQL操作引入了响应式语义 （RX） 对 Sharepreference 也做此改造

4.对当前的mvp写法高度封装简化增加一个业务模块所需要的过程



## 特别鸣谢

@Piasy：http://blog.piasy.com/ 

@[CameloeAnthony ](http://www.jianshu.com/users/44872eaffa8b)

@Google：https://github.com/googlesamples/android-architecture



### 