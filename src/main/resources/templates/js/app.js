// function LoginController($scope, $http) {
//     $scope.login = function() {
//         $http.post('/checklogin', {username: $scope.username, password: $scope.password})
//             .then(function(response) {
//                 // Login successful, redirect to list page
//                 window.location.href = '/ListStudents';
//             }, function(error) {
//                 // Login failed, display error message
//                 $scope.errorMessage = 'Wrong username or password';
//             });
//     };
// }