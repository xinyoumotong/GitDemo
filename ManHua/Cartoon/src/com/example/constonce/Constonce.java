package com.example.constonce;

public class Constonce {
	// 今日推荐
	public final static String TODAY = "http://api.kuaikanmanhua.com/v1/comic_lists/1?offset=";
	public final static String TODAY_LIMIT = "&limit=10";
	// 评论
	public final static String COMMENT_HEAD = "http://api.kuaikanmanhua.com/v1/comics/";
	public final static String COMMENT_FOOT = "/hot_comments";

	// 今日推荐的各个item获得地址+id;
	public final static String TODAY_ITEM = "http://api.kuaikanmanhua.com/v1/comics/";
	// 今日推荐各阿
	// 发现页
	// 发现页头部ViewPage
	public final static String DISTORY_HEAD = "http://api.kuaikanmanhua.com/v1/banners";
	// http://api.kuaikanmanhua.com/v1/comics/发现页主体
	public final static String DISTORY_BODY = "http://api.kuaikanmanhua.com/v1/topic_lists/mixed";
	// 发现页更多按钮 PATH1+action+PATH2+OFSET+PATH3+LIMIT
	public final static String DISTORY_MORE_PATH1 = "http://api.kuaikanmanhua.com/v1/";
	public final static String DISTORY_MORE_PATH2 = "?offset=";
	public final static String DISTORY_MORE_PATH3 = "&limit=";

	// 搜索Activity--按类别搜索
	public final static String SEARCHTYPE = "http://api.kuaikanmanhua.com/v1/tag_suggestion";
	// 搜索Activity别搜索PATH1+keyword+PATH2+OFSET+PATH3+LIMIT
	public final static String SEARCH_PATH1 = "http://api.kuaikanmanhua.com/v1/topics/search?keyword=";
	public final static String SEARCH_PATH2 = "&offset=";
	public final static String SEARCH_PATH3 = "&limit=";
	// 类别来查询
	public final static String SEARCH_TYPE = "http://api.kuaikanmanhua.com/v1/topics?&tag=";

}
