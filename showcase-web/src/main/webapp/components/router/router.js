"use strict";
/**
 * Showcase route provider.
 */
angular.module('showcase')
.config(function($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl : "pages/home/home.html",
      controller : "showcaseHomeController"
    })
    .when("/about", {
      templateUrl : "pages/about/about.html"
    })
    .when("/contact", {
      templateUrl : "pages/contact/contact.html"
    })
    .when("/faq", {
      templateUrl : "pages/faq/faq.html"
    })
    .when("/program", {
      templateUrl : "pages/programs/programs.html",
      controller : "programsCtrl"
    })
    .when("/publish", {
      templateUrl : "pages/publish/tmpl.html",
      controller : "publishCtrl"
    })
    .when("/publish/list", {
      templateUrl : "pages/dashboard/dashboard.html"
    })
    .when("/publish/schedule", {
      templateUrl : "pages/dashboard/dashboard.html"
    })
    .when("/publish/authorize", {
      templateUrl : "pages/dashboard/dashboard.html"
    })
    .when("/config/channel/:currentPrefix?", {
      templateUrl: 'pages/channels/tmpl.html',
      controller: 'channelsCtrl'
    })
    .when("/config/channel/edit?/:editChannelId?", {
      templateUrl: 'pages/channels/tmpl.html',
      controller: 'channelsCtrl'
    })
    .when("/config/channels-group/:currentChannelGroup?", {
      templateUrl: 'pages/channels-group/tmpl.html',
      controller: 'channelsGroupCtrl'
    })
    .when("/config/channels-group/edit?/:editChannelGroupId?", {
      templateUrl: 'pages/channels-group/tmpl.html',
      controller: 'channelsGroupCtrl'
    })
    .when("/config/category", {
      templateUrl : "pages/genre/genre.html",
      controller : "GenreCtrl"
    })
    .when("/config/user", {
      templateUrl : "pages/user/user-list.html",
      controller : "UserListController"
    })
    .when("/config/user/:uid?/register", {
      templateUrl : "pages/user/user-register.html",
      controller : "UserRegisterController"
    })
    .when("/config/user/new", {
      templateUrl : "pages/user/user-register.html",
      controller : "UserRegisterController"
    })
    .when("/config/user/self", {
      templateUrl : "pages/user/user-register.html",
        controller : "UserRegisterController"
    })
    .when("/search", {
      templateUrl : "pages/search/tmpl.html",
      controller : "searchController"
    })
    .when("/staff", {
      templateUrl : "pages/staff/tmpl.html",
      controller : "staffController"
    })
    .otherwise({
      //$anchorScroll();
      redirectTo : "/"
    });
});
