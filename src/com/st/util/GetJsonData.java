package com.st.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.st.bean.Collection;
import com.st.bean.Goods;
import com.st.bean.GoodsClass;
import com.st.bean.GoodsSort;
import com.st.bean.Store;
import com.st.bean.UserIf;

public class GetJsonData {

	public static List<GoodsClass> GetJsonByClass() {
		URL httpurl;
		List<GoodsClass> goodsclasslist = new ArrayList<GoodsClass>();

		try {
			httpurl = new URL(BaseUrl.queryclassurl);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			System.out.println("jsonstr:" + sb.toString());
			goodsclasslist = (List<GoodsClass>) JSON.parse(jsonstr);
			System.out.println("goodsclasslist:" + goodsclasslist);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goodsclasslist;
	}

	public static List<Goods> getJsonAllGoods(String url) {

		URL httpurl;
		List<Goods> goodslist = new ArrayList<Goods>();

		try {
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			// Gson解析json数据 会报错说开始解析期望是一个jsonobject但是是一个jsonarray
			// 我觉得可能还得添一个对象的list集合
			// String jsonstr = sb.toString();
			// System.out.println("jsonstr:" + sb.toString());
			// Gson gson = new Gson();
			// Goods goods= gson.fromJson(jsonstr,Goods.class);
			// System.out.println("解析成功！");
			// System.out.println("goodsid:"+goods.getGid());
			// System.out.println("goodspublishtime:"+goods.getGoodsPublishTime());

			// fastjson解析
			String jsonstr = sb.toString();
			System.out.println("jsonstr:" + sb.toString());
			goodslist = JSON.parseArray(jsonstr, Goods.class);
			System.out.println("解析成功！");
			System.out.println("goodslist:" + goodslist.get(0).goodsImage);

			return goodslist;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Goods> getJsonGoodsByClassId(String classid) {

		URL httpurl;
		List<Goods> goodslist = new ArrayList<Goods>();

		try {
			httpurl = new URL(BaseUrl.querygoodbyclassurl + "?GoodsClassId="
					+ classid);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			goodslist = (List<Goods>) JSON.parse(jsonstr);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodslist;
	}

	public static List<GoodsClass> getJsonAllGoodsClass() {

		URL httpurl;
		List<GoodsClass> goodslist = new ArrayList<GoodsClass>();

		try {
			httpurl = new URL(BaseUrl.queryallgoodsclassurl);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			goodslist = JSON.parseArray(jsonstr, GoodsClass.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodslist;
	}

	public static List<GoodsSort> getJsonAllGoodsSort() {

		URL httpurl;
		List<GoodsSort> goodslist = new ArrayList<GoodsSort>();

		try {
			httpurl = new URL(BaseUrl.queryallgoodssorturl);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			goodslist = JSON.parseArray(jsonstr, GoodsSort.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodslist;
	}

	public static List<Goods> getJsonGoodsByClassName(String classname) {

		URL httpurl;
		List<Goods> goodslist = new ArrayList<Goods>();

		try {
			String url = BaseUrl.querygoodsbyclassnameurl + "?GoodsClassName="
					+ URLEncoder.encode(classname);
			System.out.println("url:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			goodslist = JSON.parseArray(jsonstr, Goods.class);
			System.out.println("jsonstr:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodslist;
	}

	// 查询用户名是否存在
	public static List<UserIf> CheckLoginInf(String username, String pwd) {

		URL httpurl;
		List<UserIf> userlist = new ArrayList<UserIf>();

		try {
			String url = BaseUrl.loginurl + "?userName="
					+ URLEncoder.encode(username) + "&userPwd=" + pwd;
			System.out.println("loginurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			System.out.println("userif:" + sb.toString());
			if (sb.toString().equals("loginfailed")) {
				System.out.println("登录失败！");
			} else {
				UserIf user = new UserIf();
				user = JSON.parseObject(jsonstr, UserIf.class);
				userlist.add(user);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userlist;
	}

	public static String RegisterUser(String username, String pwd,
			String userimage) {
		URL httpurl;
		String registerstr = null;

		try {
			String url = BaseUrl.registerurl + "?userName="
					+ URLEncoder.encode(username) + "&userPwd=" + pwd
					+ "&registerImage=" + URLEncoder.encode(userimage, "utf-8");
			System.out.println("registerurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			registerstr = sb.toString();
			System.out.println("registerif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return registerstr;
	}

	public static void addStore(String stname, String sttel, String stplace,
			String userId) {

		URL httpurl;
		String addstorestr = null;

		try {
			String url = BaseUrl.addstoreurl + "?stname="
					+ URLEncoder.encode(stname) + "&sttelphone=" + sttel
					+ "&stplace=" + stplace + "&stowner=" + userId;
			System.out.println("addstoreurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			addstorestr = sb.toString();
			System.out.println("addstoreif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String CheckUsername(String username) {

		String checkusernamestr = null;
		URL httpurl;

		try {
			String url = BaseUrl.checkuserurl + "?userName="
					+ URLEncoder.encode(username);
			System.out.println("checkuserurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			checkusernamestr = sb.toString();
			System.out.println("checkusernameif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return checkusernamestr;
	}

	public static String findcollectionif(String userid, String goodsid) {

		String findcollectionifstr = null;
		URL httpurl;

		try {
			String userid2 = userid.replace(" ", "%20");
			String url = BaseUrl.findcollectionifurl + "?userId=" + userid2
					+ "&goodsId=" + goodsid;
			System.out.println("findcollectionifurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			findcollectionifstr = sb.toString();
			System.out.println("findcollectionif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return findcollectionifstr;

	}

	public static List<Store> queryStoreInfoByUserId(String userid) {

		URL httpurl;
		List<Store> storelist = new ArrayList<Store>();

		try {
			String url = BaseUrl.querystoreinfobaseurl + "?userId=" + userid;
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			storelist = JSON.parseArray(jsonstr, Store.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return storelist;

	}

	public static String UploadGoods(String goodsname, String goodsdesc,
			String goodsprice, String classitemname, String sortitemname,
			String goodsimagesubstring, String userid) {

		URL httpurl;
		String uploadgoodsurl = null;
		String userid2 = userid.replace(" ", "%20");

		try {
			String url = BaseUrl.uploadgoodsurl + "?GoodsName="
					+ URLEncoder.encode(goodsname) + "&GoodsDesc="
					+ URLEncoder.encode(goodsdesc) + "&GoodsPrice="
					+ goodsprice + "&GoodsClassId="
					+ URLEncoder.encode(classitemname) + "&GoodsSortId="
					+ URLEncoder.encode(sortitemname) + "&GoodsImage="
					+ URLEncoder.encode(goodsimagesubstring, "utf-8")
					+ "&userId=" + userid2;
			System.out.println("uploadgoodsurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			uploadgoodsurl = sb.toString();
			System.out.println("uploadgoodsif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uploadgoodsurl;

	}

	public static void saveCollection(String userid, String goodsid) {

		URL httpurl;
		String savecollectionstr = null;

		try {
			String url = BaseUrl.savecollectionurl + "?userId=" + userid
					+ "&goodsId=" + goodsid;
			System.out.println("addstoreurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			savecollectionstr = sb.toString();
			System.out.println("savecollectionif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static List<Goods> querypublishgoods(String userid, int pageNow,
			int pageSize) {
		List<Goods> mypublishgoodslist = new ArrayList<Goods>();
		URL httpurl;

		try {
			String userid2 = userid.replace(" ", "%20");
			String url = BaseUrl.querymypublishgoodsurl + "?userId=" + userid2
					+ "&pageNow=" + pageNow + "&pageSize=" + pageSize;
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			System.out.println("querypublishgoods:"+jsonstr);
			mypublishgoodslist = JSON.parseArray(jsonstr, Goods.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mypublishgoodslist;
	}

	public static List<Collection> querycollectgoods(String userid,
			int pageNow, int pageSize) {
		List<Collection> collectionlist = new ArrayList<Collection>();
		URL httpurl;

		try {
			String userid2 = userid.replace(" ", "%20");
			String url = BaseUrl.querycollectiongoodifurl + "?userId=" + userid2
					+ "&pageNow=" + pageNow + "&pageSize=" + pageSize;
			httpurl = new URL(url);
	

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			collectionlist = JSON.parseArray(jsonstr, Collection.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return collectionlist;
	}

	public static List<Goods> queryGoodsByGoodsId(String goodsId) {
		URL httpurl;
		List<Goods> goodslist = new ArrayList<Goods>();

		try {
			httpurl = new URL(BaseUrl.querygoodsbygoodsidurl + "?goodsId="
					+ goodsId);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			// fastjson解析
			String jsonstr = sb.toString();
			goodslist = JSON.parseArray(jsonstr,Goods.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodslist;
		
	}

	//这里不是删除该物品 而是将其状态改为下架就可以 
	public static String deletegoodsbyid(String goodsid) {
		String deletegoodsstr = null;
		URL httpurl;

		try {
			String url = BaseUrl.deletegoodsbyidurl + "?GoodsId="+goodsid;
			System.out.println("deletegoodsbyidurl:" + url);
			httpurl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("text/html", "charset=utf-8");
			conn.setReadTimeout(3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			deletegoodsstr = sb.toString();
			System.out.println("deletegoodsstrif:" + sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return deletegoodsstr;
	}

}
