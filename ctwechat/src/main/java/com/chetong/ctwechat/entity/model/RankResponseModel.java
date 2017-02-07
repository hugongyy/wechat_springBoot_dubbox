package com.chetong.ctwechat.entity.model;

import java.util.List;

import com.chetong.aic.evaluate.model.EvUserPeriodRankModel;
import com.chetong.aic.page.domain.PageList;
import com.chetong.ctwechat.entity.mapping.CtAuth;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RankResponseModel extends BasesModel {

	private String date; // 日期
	private Long userId; // 当前的用户ID
	private String orderBy; // 排序方式
	private String url; // 当前的url
	private String areaCodes; // 管辖区域 440300,440000,210200
	private List<CtAuth> menu; // 各种排行榜菜单
	private PageList<EvUserPeriodRankModel> rankInfoList; // 排名列表
	private String qualityChartFlag = "1";
	
	private String permissionType;  //权限查询失败后返回类型, 0-异常，1-菜单为空，无权限
	
	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CtAuth> getMenu() {
		return menu;
	}

	public void setMenu(List<CtAuth> menu) {
		this.menu = menu;
	}

	public PageList<EvUserPeriodRankModel> getRankInfoList() {
		return rankInfoList;
	}

	public void setRankInfoList(PageList<EvUserPeriodRankModel> rankInfoList) {
		this.rankInfoList = rankInfoList;
	}

	public String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getQualityChartFlag() {
		return qualityChartFlag;
	}

	public void setQualityChartFlag(String qualityChartFlag) {
		this.qualityChartFlag = qualityChartFlag;
	}

}
