(function () {
    'use strict';
    march4.app = angular.module('march4', ['ngRoute'], function($controllerProvider){
        march4.app.registerController = function(sName,fController){
            $controllerProvider.register(sName,fController);
        };
    }).directive('repeatFunc',function(){
        return function(scope,element, attrs){
            scope[attrs.repeatFunc](element);
        };
    });
    
    march4.app.factory('UserService', function($http) {
        return {
            getCurrent : function(){
                return $http.get('/users/',"");
            },
            getByEmail : function(email){
                return $http.get('/users/' + email);
            },
            getByUser : function(user){
                return $http.post('/users/signin', user);
            },
            create : function(user) {
                return $http.post('/users/', user);
            },
            signout : function() {
                return $http.post('/users/signout');
            },
            delete : function(id) {
                return $http.delete('' + user.id);
            }
        };
    });

    march4.app.factory('QuestService', function($http) {
        return {
            quests:{},
            pid:0,
            getQuests : function(pid){
                console.log(pid);
                $http.get('/api/projects/'+pid+'/quests').success(function(data, status, headers, config) {
                    this.quests = data;
                    this.pid = pid;
                    console.log(this);
                }.bind(this));
            }
        };
    });

    march4.app.controller('mainController',function($scope, $rootScope, UserService){
        $rootScope.getUser = function(){
            UserService.getCurrent()
            .success(function(response){
                $rootScope.user = response;
                if($rootScope.user.avatarImg === undefined){
                    $rootScope.user.defaultImg = "img"+Math.floor(Math.random()*14);
                }
            })
            .error(function (response, status, headers, config) {
                if (status == 403) {
                    $rootScope.user = null;
                    console.log("user not logged in.")
                }else{
                    console.log("server error.")
                }
            });    
        }

        $rootScope.getUser();
    });
}());
