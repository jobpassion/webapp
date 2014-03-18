package com.redrum.webapp.weibo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.redrum.webapp.weibo.entity.FansEntity;
import com.redrum.webapp.weibo.entity.WeiboAccount;

import weibo4j.Friendships;
import weibo4j.Search;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

@Service
public class ExecuteWeiboByAPI {

	@Value("${access_token}")
	String access_token = "2.00Lj6eJEiAwHIBdb114dc228iRIPyD";

	@Autowired
	private WeiboService weiboService;

	private Logger logger = Logger.getLogger(ExecuteWeiboByAPI.class);

//	@Scheduled(cron = "0 0/5 * * * *")
	private void followInterval() {
//		logger.info("follow start");
		List<FansEntity> fans = weiboService.getNextFansToFollow();
//		logger.info("follow fans:" + fans);
		for (FansEntity fan : fans) {
			Friendships fm = new Friendships();
//			logger.info("follow " + fan.getScreenName());
			for (WeiboAccount wa : weiboService.queryAccounts()) {
//				logger.info("follow by account:" + wa.getUsername());
				fm.client.setToken(wa.getAccessToken());
				try {
					User user = fm.createFriendshipsById(fan.getUserId());
					fan.setFollowing(true);
					logger.info("success followed "
							+ fan.getScreenName());
					weiboService.save(fan);
				} catch (Exception e) {
//					 e.printStackTrace();
					logger.info(e.getMessage());
					logger.info("api followed failed");
				}
			}
		}
	}

//	 @PostConstruct
	private void initFollowers() {

		String access_token = "2.00Lj6eJEiAwHIBdb114dc228iRIPyD";
		Search search = new Search();
		search.client.setToken(access_token);
		try {
			JSONArray array = search.searchSuggestionsUsers("海淘");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);

				// String screen_name =
				// "\u56db\u6d77\u6dd8\u5b9d\u8fd4\u5229\u7f51";
				String screen_name = obj.getString("screen_name");
				logger.info("start batch follow:" + screen_name);
				initFollowers(screen_name);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initFollowers(String screen_name) {
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		Integer cursor = 0;
		boolean t = true;
		while (t) {
			t = false;
			try {
				UserWapper users = fm.getFollowersByName(screen_name, 200,
						cursor);
				int c = 0;
				for (User u : users.getUsers()) {
					t = true;
					c++;
					if (weiboService.existFan(u.getId())) {
						continue;
					}
					FansEntity fan = new FansEntity();
					try {
						Util.copy(fan, u);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fan.setId(null);
					fan.setUserId(u.getId());
					try {
						weiboService.save(fan);
					} catch (Exception e) {
					}

				}
				logger.info("curr count:" + c);
				logger.info("next cursor at :" + users.getNextCursor());
				logger.info("total :" + users.getTotalNumber());
				cursor = new Long(users.getNextCursor()).intValue();
			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}
	}
}
