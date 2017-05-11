/**
 * Creation of spinner directive.
 */
angular.module('showcase').directive('spinner', function() {
  return {
    scope: {
      type: '@?'
    },
    templateUrl: 'components/spinner/tmpl.html'
  };
});
