var app = angular.module('app',[]);
app.filter('reverse', function() {
  return function(items) {
    return items.slice().reverse();
  };
});
app.controller('GameController', ['$scope','GameService', function ($scope,GameService) {
	  

    

        $scope.startGame = function () {

            var id = $scope.player1.id;
            GameService.startGame($scope.player1.id,$scope.player2.id)
              .then(function success(response){
                  $scope.board = response.data;
                 console.log($scope.board);
              },
              function error (response ){
                  $scope.message = '';
                  if (response.status === 404){
                      $scope.errorMessage = 'User not found!';
                  }
                  else {
                      $scope.errorMessage = "Error getting user!";
                  }
              });
        }

             $scope.playGame = function (index) {

                      var id = $scope.player1.id;
                                GameService.playGame($scope.board.boardId,$scope.board.currentPlayer.playerId,index)
                                  .then(function success(response){
                                      $scope.board = response.data;
                                     console.log($scope.board);
                                  },
                                  function error (response ){
                                      $scope.message = '';
                                      if (response.status === 404){
                                          $scope.errorMessage = 'User not found!';
                                      }
                                      else {
                                          $scope.errorMessage = "Error getting user!";
                                      }
                                  });

                }


}]);

app.service('GameService',['$http', function ($http) {


	 this.startGame = function startGame(player1Id,player2Id){
            return $http({
              method: 'GET',
              url: 'startGame/',
              params: {player1: player1Id,player2:player2Id}
            });
    	}
	 this.playGame = function playGame(boardId,playerId,index){
                return $http({
                  method: 'GET',
                  url: 'play/',
                  params: {boardID: boardId,currentPlayerID:playerId,selectedPit:index}
                });
        	}


}]);