//服务层
app.service('loginService',function($http){
	//读取列表数据绑定到表单中
	this.showName=function(){
		return $http.get('../login/name.do');
	}

    //订单列表
    this.findOrderList=function(){
        return $http.get('../login/findOrderList.do');
    }
	
});