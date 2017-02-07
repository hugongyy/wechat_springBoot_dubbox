package com.chetong.service.order;

import com.chetong.webservice.ctapp.bean.BaseBean;
import com.chetong.webservice.ctapp.bean.order.GrabOrderBean;

public interface CtAppOrderService {


	public BaseBean grabOrder(GrabOrderBean grabOrder, String token) throws Exception;

}