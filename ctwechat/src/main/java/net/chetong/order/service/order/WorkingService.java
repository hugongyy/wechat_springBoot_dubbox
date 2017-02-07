package net.chetong.order.service.order;

import com.chetong.aic.entity.ResultVO;

public interface WorkingService {

	/**
	 * 查询作业信息.
	 * 
	 * @param orderNo
	 * @param orderType
	 * @return
	 */
	ResultVO<Object> queryWorkingModel(String orderNo, String orderType, String userId) throws Exception; // TODO 这里使用aic的ResultVO会不会有问题?
}
