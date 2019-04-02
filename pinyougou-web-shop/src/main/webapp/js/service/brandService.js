// 定义服务层:
app.service("brandService",function($http){
	this.findSQ = function(){
		return $http.get("../brand/findSQ.do");
	}
	
	this.findPage = function(page,rows){
		return $http.get("../brand/findPage.do?pageNum="+page+"&pageSize="+rows);
	}
	
	this.shenhe = function(entity){
		return $http.post("../brand/shenhe.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brand/update.do",entity);
	}
	
	this.findOne=function(id){
		return $http.get("../brand/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brand/deletes.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brand/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../brand/selectOptionList.do");//url 入参：无  返回值：List<Map>
	}
});