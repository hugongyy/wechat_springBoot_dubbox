package com.chetong.ctwechat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chetong.aic.entity.ResultVO;
import com.chetong.aic.evaluate.api.remoting.EvRank;
import com.chetong.aic.evaluate.model.EvUserPeriodRankModel;
import com.chetong.aic.page.domain.PageList;
import com.chetong.ctwechat.dao.CommExeSqlDAO;
import com.chetong.ctwechat.entity.mapping.CtAuth;
import com.chetong.ctwechat.entity.mapping.CtGroup;
import com.chetong.ctwechat.entity.model.RankResponseModel;
import com.chetong.ctwechat.service.RankService;

@Service("rankService")
public class RankServiceImpl implements RankService {
	private Log log = LogFactory.getLog(RankServiceImpl.class);
	@Resource
	private CommExeSqlDAO commExeSqlDAO;
	@Resource
	private EvRank evRank;

	@Value("${headerUrl}")
	private String headerUrl;
	
	@Value("${anBangCompanyUserId}")
	private String anBangCompanyUserId;

	private static final String WHOLE_COUNTRY = "000000";
	
	/**
	 * 通过userId查角色,查权限,组合成排行榜下拉列表.
	 */
	@Override
	public List<CtAuth> queryUserMenuList(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("authName", "排行榜");
		List<CtAuth> list = commExeSqlDAO.queryForList("custom_rank_mapper.queryUserMenuList", map);
		return list;
	}

	/**
	 * 获取该用户的服务范围.
	 */
	@Override
	public String queryUserAreaCodes(String userId) {
		String provCode = null;
		String cityCode = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("authName", "微信管理");

		provCode = commExeSqlDAO.queryForObject("custom_rank_mapper.queryUserAreaCodes", map);

		if (provCode == null) {
			// 应该是查ct_group的provCode.
			CtGroup ctGroup = commExeSqlDAO.queryForObject("custom_rank_mapper.queryUserGroup", map);
			if (ctGroup != null) {
				provCode = ctGroup.getProvCode();
				cityCode = ctGroup.getCityCode();
				if (cityCode != null && WechatServiceImpl.spAreas.indexOf(cityCode) != -1) {
					provCode = cityCode;
				}
			}
		}

		if (provCode.indexOf(WHOLE_COUNTRY) != -1) {
			provCode = WHOLE_COUNTRY; // 全国
		}
		return provCode;
	}

	/**
	 * 保险公司总公司全国月/周排行
	 */
	@Override
	public RankResponseModel getComInsureHeadquartersRankData(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankMonthFormat(date);
		param.setPage(page);
		param.setRows(pageSize);
		param.setRankPeriod(rankPeriod);
		if (areaCodes != null && areaCodes.length() > 0 && !WHOLE_COUNTRY.equals(areaCodes)) {
			param.setAreaCodeArray(areaCodes.split(","));
		}

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureHeadquartersRankData(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险总公司下所有分公司全国月/周排行
	 */
	@Override
	public RankResponseModel getComInsureFilialeRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy,
			String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankAscription(Long.parseLong(parentId));
		param.setRankMonthFormat(date);
		param.setPage(page);
		param.setRows(pageSize);
		// if (areaCodes != null && areaCodes.length() > 0 &&
		// !WHOLE_COUNTRY.equals(areaCodes)) {
		// param.setAreaCodeArray(areaCodes.split(","));
		// }

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeRankData(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 车险机构委托关系全国 年/月/周排行
	 */
	@Override
	public RankResponseModel getCarInsureEntrustRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy,
			String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();
		String userName = null;

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		if (areaCodes != null && areaCodes.length() > 0 && !WHOLE_COUNTRY.equals(areaCodes)) {
			param.setAreaCodeArray(areaCodes.split(","));
		}

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getCarInsureEntrustRankData(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		if (pageList != null) {
			for (EvUserPeriodRankModel model : pageList) {
				userName = model.getUserName();
				if (userName != null) {
					userName = userName.replaceAll("车险", "").replaceAll("车童网事业部", "");
				}
				model.setUserName(userName);
			}
		}
		response.setRankInfoList(pageList);

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险分公司委托关系全国月/周排行
	 */
	@Override
	public RankResponseModel getComInsureFilialeEntrustRankData(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();
		

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankAscription(Long.parseLong(parentId));
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		// if (areaCodes != null && areaCodes.length() > 0 &&
		// !WHOLE_COUNTRY.equals(areaCodes)) {
		// param.setAreaCodeArray(areaCodes.split(","));
		// }

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeEntrustRankData(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		response.setRankInfoList(pageList);

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 车险机构作业地关系全国 年/月/周排行
	 */
	@Override
	public RankResponseModel getCarInsureWorkedRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy,
			String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();
		String userName = null;

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		if (areaCodes != null && areaCodes.length() > 0 && !WHOLE_COUNTRY.equals(areaCodes)) {
			param.setAreaCodeArray(areaCodes.split(","));
		}

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getCarInsureWorkedRankData(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		if (pageList != null) {
			for (EvUserPeriodRankModel model : pageList) {
				userName = model.getUserName();
				if (userName != null) {
					userName = userName.replaceAll("车险", "").replaceAll("车童网事业部", "");
				}
				model.setUserName(userName);
			}
		}
		response.setRankInfoList(pageList);

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险分公司作业地关系全国月/周排行
	 */
	@Override
	public RankResponseModel getComInsureFilialeWorkedRankData(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setRankAscription(Long.parseLong(parentId));
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		// if (areaCodes != null && areaCodes.length() > 0 &&
		// !WHOLE_COUNTRY.equals(areaCodes)) {
		// param.setAreaCodeArray(areaCodes.split(","));
		// }

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeWorkedRankData(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 车险机构委托关系折线图排行数据
	 */
	@Override
	public RankResponseModel getCarInsureEntrustRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankPeriod(rankPeriod);
		param.setRankMonthFormat(date);
		

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getCarInsureEntrustRankDataChart(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 车险机构作业地关系折线图排行数据
	 */
	@Override
	public RankResponseModel getCarInsureWorkedRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankPeriod(rankPeriod);
		param.setRankMonthFormat(date);

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getCarInsureWorkedRankDataChart(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险总公司折线图排行数据
	 */
	@Override
	public RankResponseModel getComInsureHeadquartersRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer pageNo, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankPeriod(rankPeriod);
		param.setRankMonthFormat(date);

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureHeadquartersRankDataChart(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		if (pageList != null) {
			for (EvUserPeriodRankModel model : pageList) {
				model.setQuality(null);
				model.setQualityRank(null);
			}
		}
		response.setRankInfoList(pageList);

		response.setQualityChartFlag("0");
		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险分公司委托关系折线图排行数据
	 */
	@Override
	public RankResponseModel getComInsureFilialeEntrustRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankPeriod(rankPeriod);
		param.setRankMonthFormat(date);

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeEntrustRankDataChart(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险分公司作业地关系折线图排行数据
	 */
	@Override
	public RankResponseModel getComInsureFilialeWorkedRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankMonthFormat(date);

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeWorkedRankDataChart(param);

		response.setRankInfoList(resultVO.getResultObject());

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 保险分公司买家关系折线图排行数据
	 */
	@Override
	public RankResponseModel getComInsureFilialeRankDataChart(String rankPeriod, String parentId, String date, String areaCodes,
			String sortBy, String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		param.setUserId(Long.parseLong(parentId));
		param.setRankPeriod(rankPeriod);
		param.setRankMonthFormat(date);

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getComInsureFilialeRankDataChart(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		if (pageList != null) {
			for (EvUserPeriodRankModel model : pageList) {
				model.setQuality(null);
				model.setQualityRank(null);
			}
		}
		response.setRankInfoList(pageList);

		response.setQualityChartFlag("0");
		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 团队全国排行数据
	 */
	@Override
	public RankResponseModel getTeamRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy,
			String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		if (page == 1) {
			param.setUserId(Long.parseLong(parentId));
		}
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		if (areaCodes != null && areaCodes.length() > 0 && !WHOLE_COUNTRY.equals(areaCodes)) {
			param.setAreaCodeArray(areaCodes.split(","));
		}

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getTeamRankData(param);

		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();

		if (anBangCompanyUserId.equals(parentId)) {
			// 如果是邦业总部查看团队排行榜,则只能看到安邦北京团队,安邦贵州团队,安邦河南团队的信息. edit by Gavin
			// 20161208
			List<Long> anBangTeamUserIds = queryTeamUserIds8OrgUserId(Long.parseLong(parentId));

			List<EvUserPeriodRankModel> anBangList = new ArrayList<EvUserPeriodRankModel>();
			if (pageList != null) {

				for (EvUserPeriodRankModel model : pageList) {
					if (anBangTeamUserIds.contains(model.getUserId())) {
						anBangList.add(model);
					}
				}
				pageList.clear();
				pageList.addAll(anBangList);
			}
		}

		// 还是需要后端排序,因为第一页,查出团队自己,为了将自己置顶.
		response.setRankInfoList(pageList);
		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	/**
	 * 车童全国排行数据(团队的传parentId,机构传areaCodes,全国的就什么都不传)
	 */
	@Override
	public RankResponseModel getCtRankData(String rankPeriod, String parentId, String date, String areaCodes, String sortBy,
			String sortType, Integer page, Integer pageSize) {
		RankResponseModel response = new RankResponseModel();

		EvUserPeriodRankModel param = new EvUserPeriodRankModel();
		if (parentId != null) {
			param.setRankAscription(Long.parseLong(parentId));
		}
		param.setRankMonthFormat(date);
		param.setRankPeriod(rankPeriod);
		param.setPage(page);
		param.setRows(pageSize);
		if (areaCodes != null && areaCodes.length() > 0 && !WHOLE_COUNTRY.equals(areaCodes)) {
			param.setAreaCodeArray(areaCodes.split(","));
		}

		if ("quantity".equals(sortBy)) {
			param.setSortQuantity(true);
		} else {
			param.setSortQuantity(false);
		}

		if ("desc".equals(sortType)) {
			param.setSortDesc(true);
		} else {
			param.setSortDesc(false);
		}

		ResultVO<PageList<EvUserPeriodRankModel>> resultVO = evRank.getCtRankData(param);
		PageList<EvUserPeriodRankModel> pageList = resultVO.getResultObject();
		if (pageList != null) {
			for (EvUserPeriodRankModel model : pageList) {
				// 前端排序
				model.setQuantityRank(null);
				model.setQualityRank(null);
			}
		}

		response.setRankInfoList(pageList);

		response.setPaginator(resultVO.getPaginator());
		return response;
	}

	@Override
	public List<Long> queryTeamUserIds8OrgUserId(Long orgUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgUserId", orgUserId);
		return commExeSqlDAO.queryForList("custom_rank_mapper.queryTeamUserIds8OrgUserId", map);
	}

}
