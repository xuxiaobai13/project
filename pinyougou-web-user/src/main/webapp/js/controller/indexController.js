//首页控制器
app.controller('indexController',function($scope,loginService){
	$scope.showName=function(){

			loginService.showName().success(
					function(response){
						$scope.loginName=response.loginName;
					}
			);
	}

    //查询订单列表
    $scope.findOrderList=function(){
        loginService.findOrderList().success(
            function(response){
                $scope.entity=response;

            }
        );
    }
});