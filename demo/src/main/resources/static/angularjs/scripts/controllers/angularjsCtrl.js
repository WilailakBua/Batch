var app = angular.module('MyApp', [])

app.controller('MyCtrl', function($scope, $http, $location){
	
	
	
// DELETE PARAMGROUP
	$scope.deleteItem = function(id){
		Swal.fire({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, delete it!'
			}).then((result) => {
			  if (result.value) {
				  $http({
		            method: 'DELETE',
		            url: '/api/delete/' + id
				  }).then(
				       Swal.fire(
				      'Deleted!',
				      'Your file has been deleted.',
				      'success'
			       ).then((result) => {
					  if (result.value) {
						  window.location.reload(true)
					  }
				  })
			    );	  
			  }
			})
	}
// DISPLAY EDIT FORM PARAMGROUP
	$scope.formEdit = true;
	$scope.editItem = function(param){
		$scope.editGroupId = param.paramGroupId;
		$scope.editGroupName = param.paramGroupName;
		console.log(param.paramGroupName)
		$scope.formEdit = false;
	}

// EDIT PARAMGROUP
	$scope.myEdit = function(){
		if($scope.editGroupName.length === 0 || typeof $scope.editGroupName === 'undefined'){	
			alert("please Enter Group Name")
		}else{
			$scope.groupFormEdit = {
				paramGroupId : $scope.editGroupId,
				paramGroupName : $scope.editGroupName,
				isdeleted : "N"
			};
		}
		
		Swal.fire({
			  title: 'Are you sure?',
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, update it!'
			}).then((result) => {
			  if (result.value) {
				  $http({
			            method: 'PATCH',
			            url: '/api/update',
			            data: angular.toJson($scope.groupFormEdit),
			            headers: {
			                'Content-Type': 'application/json'
			            }
			        }).then(
				       Swal.fire(
				      'Updated!',
				      'Your name has been updated.',
				      'success'
			       ).then((result) => {
					  if (result.value) {
						  window.location.reload(true)
					  }
				  })
			    );	  
			  }else{
				  window.location.reload(true)
			  }
			})
		
	}

// DISPLAY DATA PARAMGROUP
    var url ="http://localhost:8081/api/findallgroup";
    $http.get(url).then(function (paramgroup){
        $scope.paramgroup = paramgroup.data;   
// console.log(paramgroup.data);
    }, function error(paramgroup) {
        $scope.postResultMessage = "Error Status: " + paramgroup.statusText;
    });
    
// DISPLAY DATA PARAMINFO
    $scope.tableInfo = true;
    $scope.displayInfoItem = function(param){
    	$scope.tableInfo = false;
    	console.log(param.paramGroupId)
    	$http({
            method: 'GET',
            url: '/api/findallinfo/' + param.paramGroupId
           
        }).then(function (paraminfo){
            $scope.paraminfo = paraminfo.data;   
            console.log(paraminfo.data);    
       }, function error(paraminfo) {
           $scope.postResultMessage = "Error Status: " + paraminfo.statusText;
       });
    	
    }
    
  
// DISPLAY ADD FORM PARAMINFO
	$scope.formAddInfo = true;
	$scope.addInfoItem = function(param){
		$scope.formAddInfo = false;
		$scope.addGroupId = param.paramGroupId;
		
	}
// SAVE PARAMINFO
	$scope.myInfoAdd = function(){
		if($scope.addInfoName === undefined){	
			alert("please Enter Group Name")
		}else{
			$scope.infoFormsave = {
				paramInfoName : $scope.addInfoName,
				isdeleted : "N"
			}
		}
 	
    	$http({
            method: 'POST',
            url: '/api/insertinfo' + "?groupId=" + $scope.addGroupId,
            data: angular.toJson($scope.infoFormsave),
            headers: {
                'Content-Type': 'application/json'
            }
    	}).then(function(response) {
        	console.log(response.data);
         	if(response.data.responseCode === "200"){
				Swal.fire({
    			  icon: 'success',
    			  title: 'Your work has been saved',
    			  confirmButtonText: 'OK'
    			}).then((result) => {
				  if (result.value) {
					  window.location.reload(true)
				  }
    			})
        	}else{
        		Swal.fire({
    			  icon: 'error',
    			  title: 'Oops...',
    			  text: 'Something went wrong!',
    			}).then((result) => {
				  if (result.value) {
					  window.location.reload(true)
				  }
			  })
        	}	
        });
    }
	
	
// DISPLAY EDIT FORM PARAMINFO
	$scope.formEditinfo = true;
	$scope.editInfoItem = function(paramin){
		$scope.editInfoId = paramin.paramInfoId;
		$scope.editInfoName = paramin.paramInfoName;
// console.log(param.paramGroupName)
		$scope.formEditinfo = false;
	}
// EDIT PARAMINFO
	$scope.myInfoEdit = function(){
		console.log($scope.editInfoId)
		console.log($scope.editInfoName)
		if($scope.editInfoName.length === 0 || typeof $scope.editInfoName === 'undefined'){	
			alert("please Enter Group Name")
		}else{
			$scope.infoFormEdit = {
					paramInfoId : $scope.editInfoId,
					paramInfoName : $scope.editInfoName,
					isdeleted : "N"
			};
		}
		
		Swal.fire({
			  title: 'Are you sure?',
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, update it!'
			}).then((result) => {
			  if (result.value) {
				  $http({
			            method: 'PATCH',
			            url: '/api/updateinfo',
			            data: angular.toJson($scope.infoFormEdit),
			            headers: {
			                'Content-Type': 'application/json'
			            }
			        }).then(
				       Swal.fire(
				      'Updated!',
				      'Your name has been Updated.',
				      'success'
			       ).then((result) => {
					  if (result.value) {
						  window.location.reload(true)
					  }
				  })
			    );	  
			  }else{
				  window.location.reload(true)
			  }
			})
		}    
    	

	
// DELETE PARAMINFO
	$scope.deleteInfoItem = function(id){
		
		Swal.fire({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, delete it!'
			}).then((result) => {
			  if (result.value) {
				  $http({
			            method: 'DELETE',
			            url: '/api/deleteinfo/' + id
			        }).then(
				       Swal.fire(
				      'Deleted!',
				      'Your file has been deleted.',
				      'success'
			       ).then((result) => {
					  if (result.value) {
						  window.location.reload(true)
					  }
				  })
			    );	  
			  }
			})
	}    
});

// SAVE PARAMGROUP
app.controller('myFrom', function($scope, $http, $location){


	
	$scope.mySave = function(){
		if($scope.GroupName === undefined){	
			alert("please Enter Group Name")
		}else{
			if($scope.GroupName === ""){	
				alert("please Enter Group Name")
				}else{
					$scope.groupForm = {
						paramGroupName : $scope.GroupName,
						isdeleted : "N"
					};
			    	console.log($scope.GroupName);
			    	$http({
			            method: 'POST',
			            url: '/api/savegroup',
			            data: angular.toJson($scope.groupForm),
			            headers: {
			                'Content-Type': 'application/json'
			            }
			        }).then(function(response) {
			        	console.log(response.data);
			        	if(response.data.responseCode === "200"){
	        				Swal.fire({
		        			  icon: 'success',
		        			  title: 'Your work has been saved',
		        			  confirmButtonText: 'OK'
		        			}).then((result) => {
	        				  if (result.value) {
	        					  window.location.reload(true)
	        				  }
		        			})
			        	}else{
			        		Swal.fire({
		        			  icon: 'error',
		        			  title: 'Oops...',
		        			  text: 'Something went wrong!',
		        			}).then((result) => {
	        				  if (result.value) {
	        					  window.location.reload(true)
	        				  }
        				  })
			        	}	
			        });
					
				}
			
		}

// window.location.reload(true)
    }
	
	
	$scope.myExcel = function(){
		window.location.href ="http://localhost:8081/api/exceldownload";	
	}
	
	$scope.myPdf = function(){
		window.location.href ="http://localhost:8081/api/pdf";	
	}
});






