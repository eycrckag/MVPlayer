# 项目简介 #
本项目是手机影音项目，展示了视频播放和音乐播放的基本实现。 另外，本项目部分地方使用了MVP模式，对OKhttp用Volley的姿势做了基本的封装。

# MVP介绍 #
[传送门](https://github.com/uncleleonfan/FanChat)

# 项目初始化 #
* 包的创建
* 基类的创建
* Git初始化

# Splash界面 #
## 全屏样式 ##
    <style name="AppTheme.FullScreen" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowFullscreen">true</item>
    </style>
## 缩放动画 ##
	<?xml version="1.0" encoding="utf-8"?>
	<set xmlns:android="http://schemas.android.com/apk/res/android">
	    <scale
	        android:duration="3000"
	        android:fromXScale="1.5"
	        android:fromYScale="1.5"
	        android:pivotX="50%"
	        android:pivotY="50%"
	        android:toXScale="1"
	        android:toYScale="1"/>	
	</set>
## Activity跳转动画 ##
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

# 主界面 #

## 设置菜单 ##
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                goTo(SettingsActivity.class, false);
                break;
        }
        return true;
    }

## 底部导航条BottomBar ##
[传送门](https://github.com/roughike/BottomBar)

## Fragment切换 ##
    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, FragmentFactory.getInstance().getFragment(tabId));
            fragmentTransaction.commit();
        }
    };

# 设置界面 #
## 初始化Action Bar ##
    @Override
    protected void init() {
        super.init();
        //Be careful, we need to use getSupportActionBar instead of getActionBar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.settings);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

## 使用PreferenceFragment ##
注意SettingsFragment必须是public，在布局文件中指定fragment时必须给fragment一个id。

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            addPreferencesFromResource(R.xml.settings_prefs);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if (preference.getKey().equals(Constant.SP.ABOUT)) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }

## 设置选项 ##
	<?xml version="1.0" encoding="utf-8"?>
	<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
	    <Preference
	        android:key="clear_cache"
	        android:title="@string/clean_cache"/>
	
	    <SwitchPreference
	        android:key="push_notification"
	        android:title="@string/push_notification"/>
		
	    <SwitchPreference
	        android:key="no_wifi_load_images"
	        android:title="@string/no_wifi_load_images"/>
	
	    <Preference
	        android:key="about"
	        android:title="@string/about"/>
	
	</PreferenceScreen>

## 选项点击处理 ##
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(Constant.SP.ABOUT)) {
             Intent intent = new Intent(getActivity(), AboutActivity.class);
             startActivity(intent);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

## 获取SharedPreference ##
    SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    boolean aBoolean = defaultSharedPreferences.getBoolean(Constant.SP.NO_WIFI_LOAD_IMAGES, false);


## Activity进出场动画 ##
    <style name="AppTheme.AnimationActivity" parent="AppTheme">
        <item name="android:windowAnimationStyle">@style/AnimationActivity</item>
    </style>

    <style name="AnimationActivity" parent="@android:style/Animation.Activity">
        <item name="android:activityOpenEnterAnimation">@anim/activity_enter</item>
        <item name="android:activityCloseExitAnimation">@anim/activity_exit</item>
    </style>
**更多动画，请参考[Material Animations](https://github.com/lgvalle/Material-Animations)**

# 首页 #
## 功能需求 ##
* MV列表 （RecyclerView）
* 下拉刷新 (SwipeRefreshLayout)
* 加载更多


# OKHttp的封装 #
## 请求 ##

### 构造方法 ###
    public MVPlayerRequest(String url, NetworkListener<T> listener) {
        mUrl = url;
        mNetworkListener = listener;
        mGson = new Gson();
    }
### 执行网络请求 ###
    public void execute() {
        NetworkManager.getInstance().sendRequest(this);
    }
### 解析网络响应 ###
    public T parseNetworkResponse(String result) {
        Class c = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) c.getGenericSuperclass();
        Type actualType = parameterizedType.getActualTypeArguments()[0];
        return mGson.fromJson(result, actualType);
    }

## 回调 ##
	public interface NetworkListener<T> {
	
	    void onError(String errorMsg);
	
	    void onSuccess(T result);
	}
## NetworkManager ##
NetworkManager维护一个OkhttpClient的对象来执行网络请求。当收到网络结果后，通过绑定主线程的Handler回调到主线程。

    public void sendRequest(final MVPlayerRequest mvPlayerRequest) {
        final Request request = new Request.Builder().get().url(mvPlayerRequest.getUrl()).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mvPlayerRequest.getNetworkListener().onError(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                final Object parsedResponse =  mvPlayerRequest.parseNetworkResponse(responseString);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mvPlayerRequest.getNetworkListener().onSuccess(parsedResponse);
                    }
                });
            }
        });
    }


# 悦单 #
## 功能需求 ##
* 悦单列表 （RecyclerView）
* 下拉刷新 (SwipeRefreshLayout)
* 加载更多



# 列表的抽取 #
由于首页，悦单，MV界面的列表的布局一致（RecyclerView和SwipeRefreshLayout），业务逻辑一致（加载列表数据，下拉刷新，加载更多数据），所以我们可以进行抽取，避免重复的代码。

## BaseListView ##
View层需要提供的接口非常简单，只需处理数据加载成功或者失败的情况。

	public interface BaseListView {
	
	    void onLoadListDataFailed();
	
	    void onLoadListDataSuccess();
	}

## BaseListFragment ##
BaseListFragment的布局抽取了RecyclerView和SwipeRefreshLayout以及相关的业务逻辑。
### 下拉刷新 ###
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mBaseListPresenter.refresh();
        }
    };

### 加载更多 ###
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (isScrollToBottom()) {
                    mBaseListPresenter.loadMoreListData();
                }
            }
        }
    };

    private boolean isScrollToBottom() {
        return mLinearLayoutManager.findLastVisibleItemPosition() == mBaseListPresenter.getListData().size() - 1;
    }
### 加载数据成功或者失败 ###
    public void onLoadListDataSuccess() {
        toast(R.string.load_data_success);
        mAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadListDataFailed() {
        toast(R.string.load_data_failed);
    }

### 子类需要实现Adapter和Presenter ###
    public abstract RecyclerView.Adapter getListAdapter();
    public abstract BaseListPresenter getPresenter(BaseListView view);

## BaseListPresenter ##
BaseListPresenter定义了一个BaseListFragment的业务逻辑，由各个界面具体实现该Presenter，比如HomePresenterImpl, YueDanPresenterImpl, MVPagePresenterImpl。

	public interface BaseListPresenter<T> {
	
	    void loadListData();
	
	    List<T> getListData();
	
	    void refresh();
	
	    void loadMoreListData();
	
	}


# MV #

# MV详情界面 #

# V榜 #

# 音乐播放界面 #
