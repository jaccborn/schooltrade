package com.st.util;

public class BaseUrl {

	//上传头像路径
	public static String uploadheadimageurl = "http://192.168.1.103:8080/SchoolTradeService/uploadheadimage.action";
	//上传物品图片路径
	public static String uploadgoodsimageurl = "http://192.168.1.103:8080/SchoolTradeService/uploadgoodsimage.action";
	//上传物品路径
	public static String uploadgoodsurl = "http://192.168.1.103:8080/SchoolTradeService/goods!addGoods.action";
	//查所有goods
	public static String queryallgoodsurl = "http://192.168.1.103:8080/SchoolTradeService/goods!queryAllGoods.action";
	//查类别名称
	public static String queryclassurl = "http://192.168.1.103:8080/SchoolTradeService/goods!queryClass.action";
	//查所有大类别里的属性
	public static String queryallgoodsclassurl = "http://192.168.1.103:8080/SchoolTradeService/goods!queryGoodsClass.action";
	//查所有大类别里的属性
	public static String queryallgoodssorturl = "http://192.168.1.103:8080/SchoolTradeService/goods!queryGoodsSort.action";
	//物品图片路径
	public static String basegoodsimageurl = "http://192.168.1.103:8080/SchoolTradeService/image/goods/";
	//头像图片路径
	public static String baseheadimageurl = "http://192.168.1.103:8080/SchoolTradeService/image/headimage/";
	//根据goodsclassid找所有的goods
	public static String querygoodbyclassurl="http://192.168.1.103:8080/SchoolTradeService/goods!queryGoodsByClass.action";
	//根据classname找所有goods
	public static String querygoodsbyclassnameurl="http://192.168.1.103:8080/SchoolTradeService/goods!queryGoodsByClassName.action";
	//注册
	public static String registerurl="http://192.168.1.103:8080/SchoolTradeService/user!register.action";
	//登录
	public static String loginurl="http://192.168.1.103:8080/SchoolTradeService/user!login.action";
	//添加店铺
	public static String addstoreurl="http://192.168.1.103:8080/SchoolTradeService/store!addstore.action";
	//检查用户名
	public static String checkuserurl="http://192.168.1.103:8080/SchoolTradeService/user!checkusername.action";
	//查找是否收藏
	public static String findcollectionifurl = "http://192.168.1.103:8080/SchoolTradeService/collection!findcollectionif.action";
	//查询店铺
	public static String querystoreinfobaseurl = "http://192.168.1.103:8080/SchoolTradeService/store!findstorebyid.action";
	//保存收藏信息
	public static String savecollectionurl = "http://192.168.1.103:8080/SchoolTradeService/collection!savecollection.action";
	//查询已经收藏的物品信息
	public static String querycollectiongoodifurl = "http://192.168.1.103:8080/SchoolTradeService/collection!querycollectgoods.action";
	//查询我已经发布的商品信息
	public static String querymypublishgoodsurl = "http://192.168.1.103:8080/SchoolTradeService/goods!querymypublishgoods.action";
	//根据goodsid查找goodslist
	public static String querygoodsbygoodsidurl = "http://192.168.1.103:8080/SchoolTradeService/goods!queryGoodsByGoodsId.action";
	//将物品状态改为下架
	public static String deletegoodsbyidurl = "http://192.168.1.103:8080/SchoolTradeService/goods!deletegoodsbyid.action";



}
