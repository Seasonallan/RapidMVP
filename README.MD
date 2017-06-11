Android基于自身习惯的一个快速开发框架<br>
====
使用MVP模式，让View专注于处理数据的可视化以及与用户的交互，同时让Model只关系数据的处理<br>

引用到的技术：<br>
-------

1、Retrofit 进行网络数据加载，封装Retrofit，抽象出参数加密解密方法<br>
```
public abstract class BaseNetModel {
    /**
     * api的base URL.
     * @return
     */
    protected abstract String getBaseUrl();

    /**
     * 请求拦截器，用于对参数进行加密或添加公用参数
     * @return
     */
    protected abstract Interceptor getInterceptor();

    /**
     * 返回数据解析器，用于解密返回数据
     * @return
     */
    protected abstract Converter.Factory getConverterFactory();

}
```
2、RxJava 进行耗时操作响应式编程<br>
```
public abstract class BaseLocalModel {

    /**
     * 直接获取数据
     * @param key
     * @return
     */
    public abstract Object getValueImmediately(String key);

    /**
     * 直接设置数据
     * @param key
     * @param value
     * @return
     */
    public abstract boolean setValueImmediately(String key, Object value);

}
```
3、Picasso 轻量级图片加载框架 进行一层封装<br>
```
public class ImageModel {
    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void bindImage2View(ImageView imageView, String url) {
        Picasso.with(BaseApplication.sContext).load(url).into(imageView);
    }
}
```
4、Gson 网络数据JSON解析<br>
```
Gson gson = new Gson();
gson.toJson(entry);
gson.fromJson(jsonStr, entry.class);
```

框架使用：<br>
-------

1.View层里面Activity中的使用<br>
```
public class CommentActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener {
    CommentPresenter mPresenter;
    PullToRefreshListView mPullToRefreshListView;
    CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getTitleBar().setTopTile("VideoDetail");
        getTitleBar().enableLeftButton();

        //第一步，绑定Presenter
        mPresenter = new CommentPresenter(this);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_view);
        mPullToRefreshListView.enableAutoLoadingMore();
        mPullToRefreshListView.setOnRefreshListener(this);

        //第二步，Presenter请求数据，等待响应
        mPresenter.loadList(BasePresenter.CREATE);
    }

    //第三步，响应结果 成功数据处理
    @Override
    public <T> void onResponse(int type, T result) {
        Console.log(Thread.currentThread().getName() + " onResponseUI result=" + result);
        super.onResponse(type, result);
        if (result instanceof CommentList){
            CommentList commentList = (CommentList) result;
            mPullToRefreshListView.onRefreshComplete();
            getEmptyView().dismissEmptyView();
            if (type == BasePresenter.REFRESH || mCommentAdapter == null) {
                mCommentAdapter = new CommentAdapter(this, commentList.comments){

                    public void onItemClick(CommentList item){
                        //CommentActivity.show(getContext(), item);
                    }
                };
                mPullToRefreshListView.setAdapter(mCommentAdapter);
            } else {
                mCommentAdapter.append(commentList.comments);
                mCommentAdapter.notifyDataSetChanged();
            }
            if ((mCommentAdapter == null || mCommentAdapter.getCount() <= 0) && commentList.comments == null){
                getEmptyView().showEmptyView();
            }
            if (commentList.comments != null && commentList.comments.size() < Configure.PAGE_SIZE){
                mPullToRefreshListView.noMore();
            }

        }
    }

    //第三步，响应结果 成功失败处理
    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
        mPullToRefreshListView.errorLoadingMore();
        if (mCommentAdapter == null || mCommentAdapter.getCount() <= 0){
            getEmptyView().showEmptyView();
        }
    }

    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mPresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return mCommentAdapter;
    }

    @Override
    public void onRefresh() {
        mPresenter.loadList(BasePresenter.REFRESH);
    }

    @Override
    public void onLoadingMore() {
        mPresenter.loadList(BasePresenter.MORE);
    }
}
```
其中，BaseActivity是封装好的一个集成标题栏，加载状态和空显示功能的基类<br>
```
public abstract class BaseActivity extends Activity implements ITitleBarAction, ILoadingAction, IEmptyAction, IView {
    ITitleBar mTitleBar;
    /**
     * 顶部标题控制栏
     * @return
     */
    public ITitleBar getTitleBar(){
        if (mTitleBar == null){
            mTitleBar = new TitleBarImpl(this);
        }
        return mTitleBar;
    }
}
```
PullToRefreshListView是在RecycleView基础上拓展的一个下拉刷新，滑动加载更多的列表视图<br>
```
mPullToRefreshListView.disableFooterView();
mPullToRefreshListView.enableAutoLoadingMore();
mPullToRefreshListView.errorLoadingMore();
mPullToRefreshListView.noMore();

```
2.Presenter层的使用<br>
```
public class CommentPresenter extends BasePresenter {

    //第一步，绑定视图
    public CommentPresenter(IView view){
        super(view);
    }

    protected <T> void onResponse2UI(int type, T result) {
        super.onResponse2UI(type, result);
        if (type == REFRESH){
            //本地数据缓存
            ModelFactory.local().file().setValue("DetailView", result, null);
        }
    }

    /**
     * 发送评论
     * @param comment
     */
    public void sendComment(String comment){
        ModelFactory.net().video().sentComment(comment, new HttpCallback<String>(COMMENT));
    }

    /**
     * 获取列表数据
     * @param callType
     */
    public void loadList(int callType) {
        if (callType == CREATE){
            getView().getLoadingView().showLoadingView();
            Console.log("check local cache");
            //本地数据缓存
            ModelFactory.local().file().getValue("DetailView" + vid, new LocalObserver<CommentList>() {
                @Override
                public void onError(Throwable e) {
                    Console.log("empty local cache, load from net");
                    loadList(REFRESH);
                }

                @Override
                public void onNext(CommentList o) {
                    Console.log("local cache");
                    super.onNext(o);
                }
            });
            return;
        }

        String maxId = "0";

        BaseRecycleAdapter adapter = getView().getAdapter();
        if (callType == MORE) {
            if (adapter != null && adapter.getCount() > 0) {
                maxId = ((CommentItem)adapter.getItem(adapter.getCount() - 1)).id;
            }
        }
        //第二步，通过Model获取数据并在Callback中通过IView接口响应到界面
        ModelFactory.net().video().getComment(Configure.PAGE_SIZE, maxId, vid, new HttpCallback<CommentList>(callType));

    }
}
```
其中，BasePresenter是请求Callback的二次封装

3.Model层<br>
ModelFactory是封装好的数据工厂，有本地数据工厂和网络数据工厂，可以通过以下方式在Presenter层中调用<br>
```
//本地文件数据
ModelFactory.local().file().getValue("keyData", new Observer<ClientKey>());

//sharedPreferences文件数据
ModelFactory.local().sharedPreferences().getValue("key", new Observer<ClientKey>());

//网络数据
 ModelFactory.net().video().getComment();

```


