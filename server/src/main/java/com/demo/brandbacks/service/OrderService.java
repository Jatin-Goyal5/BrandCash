package com.demo.brandbacks.service;


import java.io.ByteArrayOutputStream;
import java.util.List;

import com.demo.brandbacks.dto.OrderListDto;
import com.demo.brandbacks.dto.OrderRequest;
import com.demo.brandbacks.dto.OrderResponse;
import com.demo.brandbacks.model.Order;
import com.demo.brandbacks.security.UserPrincipal;

public interface OrderService {

	public OrderResponse saveOrder(OrderRequest orderRequest, UserPrincipal userDetails);

	public List<OrderListDto> getAll(UserPrincipal userDetails);

	public ByteArrayOutputStream generateQrCodesPdf(List<String> qrDataList) throws Exception;
}
