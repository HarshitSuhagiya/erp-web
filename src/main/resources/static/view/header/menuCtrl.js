app.controller("menuCtrl",["$scope",function($scope){
	
	$scope.menuList = [
		/*{id : 1,value : "Dashboard",isChildMenu : false,uiSref: "temp",icon : "fa fa-desktop",className : "active",subList : []},*/
		{id : 2,value : "Sales",uiSref: "temp",icon : "fa fa-desktop",className : "",subList : [
			{id: 3,value : "Sale",uiSref: "temp",icon : "fa fa-desktop"},
			{id: 4,value : "Return",uiSref: "temp",icon : "fa fa-desktop"}
		]}/*,
		{id : 5,value : "Purchase",isChildMenu : true,uiSref: "temp",icon : "fa fa-desktop",className : "",subList : [
			{id: 8,value : "Purchase",uiSref: "temp",icon : "fa fa-desktop"},
			{id: 9,value : "Return",uiSref: "temp",icon : "fa fa-desktop"}
		]},
		{id : 6,value : "Masters",isChildMenu : true,uiSref: "temp",icon : "fa fa-desktop",className : "",subList : [
			{id: 10,value : "Customers",uiSref: "home.customers",icon : "fa fa-desktop"},
			{id: 11,value : "Route",uiSref: "home.route",icon : "fa fa-desktop"},
			{id: 12,value : "Categories",uiSref: "home.categories",icon : "fa fa-desktop"},
			{id: 13,value : "Products",uiSref: "home.products",icon : "fa fa-desktop"},
			{id: 14,value : "Accounts",uiSref: "home.accounts",icon : "fa fa-desktop"},
			{id: 15,value : "Companies",uiSref: "home.companies",icon : "fa fa-desktop"}
			
		]},
		{id : 7,value : "Reports",isChildMenu : false,uiSref: "temp",icon : "",className : "fa fa-desktop",subList : []},*/
	];
	
	
	/*$scope.toggleDropDown = function(index) {
		console.log($("drop" + index).closest("ul"));
		$("drop" + index).closest("ul").addClass("addToggle");
	}*/
	
}]);