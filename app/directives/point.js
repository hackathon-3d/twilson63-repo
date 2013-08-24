angular.module('App')
  .directive('zpnPoint', function() {
    return {
      restrict: 'E',
      replace: true,
      template: '<div class="point" style="top: {{xtop}};left: {{xleft}};"></div>',
      scope: {
        'xtop': '@xtop',
        'xleft': '@xleft'
      }
    };
  });