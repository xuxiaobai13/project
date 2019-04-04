app.service("contentService",function($http){
	this.findByCategoryId = function(categoryId){
		return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
	};

    this.findByGoods = function(){
        return $http.get("content/findByGoods.do");
    };

    this.findItemByCat = function(parentId){
        return $http.get("content/findItemByCat.do?parentId="+parentId);
    };

});