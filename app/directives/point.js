angular.module('App')
  .directive('zpnPoint', function() {
    return {
      restrict: 'E',
      replace: true,
      template: '<div class="point" style="top: {{xtop}};left: {{xleft}};z-index=900;"></div>',
      scope: {
        'xtop': '@xtop',
        'xleft': '@xleft'
      }
    };
  });