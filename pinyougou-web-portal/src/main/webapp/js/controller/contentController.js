app.controller("contentController", function ($scope, contentService) {

    //数组
    $scope.contentList = [];

    $scope.ItemCat1List = [];


    // 根据分类ID查询广告的方法:
    //入参：categoryId 分类Id  1 轮播图 2 今天  3 活动。。。
    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            $scope.contentList[categoryId] = response;//List<Content>
        });
    }

    $scope.findByGoods = function () {
        contentService.findByGoods().success(function (response) {
            $scope.goodsMap = response;
        })
    }


    //主键查询分类信息 第一级parentId =0
    $scope.findItemByCat = function (parentId) {
        contentService.findItemByCat(parentId).success(function (response) {
            $scope.ItemCat1List[parentId] = response;
        });
    };


   /* // 查询一级分类列表:
    $scope.selectItemCat1List = function(){
        //商品分类服务层  查询所有商品分类（父ID为0）
        itemCatService.findByParentId(0).success(function(response){//List<ItemCat>
            $scope.itemCat1List = response;//List<ItemCat>
        });


    }

    // 查询二级分类列表:
    $scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
        itemCatService.findByParentId(newValue).success(function(response){
            $scope.itemCat2List = response;
        });
    });

    // 查询三级分类列表:
    $scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
        itemCatService.findByParentId(newValue).success(function(response){
            $scope.itemCat3List = response;
        });
    });
*/


    //搜索  （传递参数）
    $scope.search = function () {
        location.href = "http://localhost:9003/search.html#?keywords=" + $scope.keywords;
    }


});