$(function () {
    var pathname = window.location.pathname;
    var search = window.location.search;
    var curAonj =$(".menu_dropdown").find("a[href='" + pathname + "']");
    curAonj.parent("dl").find("dt").addClass("selected");
    curAonj.parent("li").addClass("current");
})