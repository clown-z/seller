<html>
	<head>
		<meta charset="utf-8">
		<title>卖家订单详情</title>
		<link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-2 column">
				</div>
				<div class="col-md-10 column">
					<div class="row clearfix">
						<div class="col-xs-5 col-md-5 column">
							<table class="table table-bordered table-condensed table-hover ">
								<thead>
									<tr>
										<th>订单id</th>
										<th>订单金额</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${orderDTO.orderId}</td>
										<td>${orderDTO.orderAmount}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<#-- 订单详情表数据 -->
					<table class="table table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th>商品id</th>
								<th>商品名称</th>
								<th>价格</th>
								<th>数量</th>
								<th>总额</th>
							</tr>
						</thead>
						<tbody>
							<#list orderDTO.getOrderDetailsList() as orderDetail>
							<tr>
								<td>${orderDetail.productId}</td>
								<td>${orderDetail.productName}</td>
								<td>${orderDetail.productPrice}</td>
								<td>${orderDetail.productQuantity}</td>
								<td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
							</tr>
							</#list>
						</tbody>
					</table>
					
					<#-- 操作 -->
					<div class="col-xs-5 col-md-5 column">
						<#if orderDTO.getOrderStatusEnum().message == "新订单">
							 <a href="finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
							 <a href="cancle?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
						</#if>
					</div>
		
				</div>
			</div>
		</div>
	</body>
</html>
