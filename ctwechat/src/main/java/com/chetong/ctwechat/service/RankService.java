package com.chetong.ctwechat.service;

import java.util.List;

import com.chetong.ctwechat.entity.mapping.CtAuth;
import com.chetong.ctwechat.entity.model.RankResponseModel;

public interface RankService {

	List<CtAuth> queryUserMenuList(String parentId);

	String queryUserAreaCodes(String parentId);

	RankResponseModel getComInsureHeadquartersRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType, Integer page,
			Integer pageSize);

	RankResponseModel getComInsureFilialeRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType, Integer page,
			Integer pageSize);

	RankResponseModel getCarInsureEntrustRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType, Integer page,
			Integer pageSize);

	RankResponseModel getComInsureFilialeEntrustRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getCarInsureWorkedRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getComInsureFilialeWorkedRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getCarInsureEntrustRankDataChart(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getCarInsureWorkedRankDataChart(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getComInsureFilialeEntrustRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize);

	RankResponseModel getComInsureFilialeWorkedRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize);

	RankResponseModel getComInsureFilialeRankDataChart(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType,
			Integer page, Integer pageSize);

	RankResponseModel getTeamRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType, Integer page,
			Integer pageSize);

	RankResponseModel getCtRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy, String sortType, Integer page,
			Integer pageSize);

	RankResponseModel getComInsureHeadquartersRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer pageNo, Integer pageSize);

	List<Long> queryTeamUserIds8OrgUserId(Long orgUserId);


}
