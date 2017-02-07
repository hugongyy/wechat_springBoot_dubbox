package com.chetong.ctwechat.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chetong.ctwechat.entity.mapping.CtAuth;
import com.chetong.ctwechat.entity.model.RankResponseModel;
import com.chetong.ctwechat.helper.DateUtil;
import com.chetong.ctwechat.service.RankService;

@RestController
@RequestMapping("/rank")
public class RankController {
	private Log log = LogFactory.getLog(RankController.class);

	@Resource
	private RankService rankService;

	@RequestMapping(value = "/queryUserMenuList", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel queryUserMenuList(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String userId = (String) map.get("userId");
		try {
			List<CtAuth> menu = rankService.queryUserMenuList(userId);
			if (menu.size() == 0) {
				response.setCode("fail");
				response.setMessage("该用户没有查看任何一个排行榜的权限.");
				response.setPermissionType("1");
				return response;
			}

			String areaCodes = rankService.queryUserAreaCodes(userId);

			response.setCode("success");
			response.setMessage("查询权限成功.");
			response.setMenu(menu);
			response.setAreaCodes(areaCodes);

		} catch (Exception e) {
			e.printStackTrace();
			// log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("查询权限失败.");
			response.setPermissionType("0");
		}

		return response;
	}

	/**
	 * 保险公司总公司全国   /年/月/周排行
	 */
	@RequestMapping(value = "/getComInsureHeadquartersRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureHeadquartersRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期
		
		if (areaCodes == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}

		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;
		
		try {
			response = rankService.getComInsureHeadquartersRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险总公司委托量排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险总公司下所有分公司全国月/周排行
	 */
	@RequestMapping(value = "/getComInsureFilialeRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (parentId == null || areaCodes == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		
		
		try {
			response = rankService.getComInsureFilialeRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司委托量排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 车险机构委托关系全国 年/月/周排行
	 */
	@RequestMapping(value = "/getCarInsureEntrustRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getCarInsureEntrustRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (areaCodes == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;


		
		try {
			response = rankService.getCarInsureEntrustRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");

		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车险机构委托关系排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险分公司委托关系全国月/周排行
	 */
	@RequestMapping(value = "/getComInsureFilialeEntrustRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeEntrustRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期
		
		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		
		try {
			response = rankService.getComInsureFilialeEntrustRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType,
					pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司委托关系排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 车险机构作业地关系全国 年/月/周排行
	 */
	@RequestMapping(value = "/getCarInsureWorkedRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getCarInsureWorkedRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = null;
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (areaCodes == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getCarInsureWorkedRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车险机构作业地关系排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险分公司作业地关系全国月/周排行
	 */
	@RequestMapping(value = "/getComInsureFilialeWorkedRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeWorkedRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getComInsureFilialeWorkedRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType,
					pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司作业地关系排行榜查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 车险机构委托关系折线图排行数据
	 */
	@RequestMapping(value = "/getCarInsureEntrustRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getCarInsureEntrustRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getCarInsureEntrustRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage(" 车险机构委托关系折线图查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 车险机构作业地关系折线图排行数据
	 */
	@RequestMapping(value = "/getCarInsureWorkedRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getCarInsureWorkedRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期
		
		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}

		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getCarInsureWorkedRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车险机构作业地关系折线图查询失败" + e.getMessage());
		}

		return response;
	}
	
	/**
	 * 保险总公司折线图排行数据
	 */
	@RequestMapping(value = "/getComInsureHeadquartersRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureHeadquartersRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		try {
			response = rankService.getComInsureHeadquartersRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType,
					pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司委托关系折线图查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险分公司委托关系折线图排行数据
	 */
	@RequestMapping(value = "/getComInsureFilialeEntrustRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeEntrustRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期
		
		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}

		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getComInsureFilialeEntrustRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType,
					pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司委托关系折线图查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险分公司作业地关系折线图排行数据
	 */
	@RequestMapping(value = "/getComInsureFilialeWorkedRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeWorkedRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getComInsureFilialeWorkedRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType,
					pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司作业地关系折线图查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 保险分公司买家关系折线图排行数据
	 */
	@RequestMapping(value = "/getComInsureFilialeRankDataChart", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getComInsureFilialeRankDataChart(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId"); // 不需要
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes"); // 不需要
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getComInsureFilialeRankDataChart(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo,
					pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("保险分公司买家关系折线图查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 团队全国排行数据
	 */
	@RequestMapping(value = "/getTeamRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getTeamRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		if (areaCodes == null || parentId == null) {
			response.setCode("fail");
			response.setMessage("缺少必要参数");
			return response;
		}
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getTeamRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("团队全国排行数据查询失败" + e.getMessage());
		}

		return response;
	}

	/**
	 * 车童全国排行数据
	 */
	@RequestMapping(value = "/getCtRankData", method = RequestMethod.POST)
	@ResponseBody
	public RankResponseModel getCtRankData(@RequestBody ModelMap map) {
		RankResponseModel response = new RankResponseModel();
		String parentId = (String) map.get("parentId");
		String date = (String) map.get("date"); // '2016-02' // 如果为空,默认上个月
		String areaCodes = (String) map.get("areaCodes");
		Integer pageNo = (Integer) map.get("pageNo");
		Integer pageSize = (Integer) map.get("pageSize");
		String sortBy = (String) map.get("sortBy"); // 排序(按订单,评分)
		String sortType = (String) map.get("sortType"); // 排序(正序,倒序)
		String rankPeriod = (String) map.get("rankPeriod");  //排行周期

		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		date = date == null ? DateUtil.getLastMonthDateYYYYMM() : date;
		sortBy = sortBy == null ? "quantity" : sortBy;
		sortType = sortType == null ? "desc" : sortType;

		try {
			response = rankService.getCtRankData(rankPeriod, parentId, date, areaCodes, sortBy, sortType, pageNo, pageSize);

			response.setDate(date);
			response.setCode("success");
		} catch (Exception e) {
			log.error(response.getMessage(), e);
			response.setCode("fail");
			response.setMessage("车童全国排行数据查询失败" + e.getMessage());
		}

		return response;
	}
}
