function placeholderAutoHide(element) {
    var placeholder = element.attr("placeholder");

    function hide() {
        element.removeAttr("placeholder");
    }

    function show() {
        element.attr("placeholder", placeholder);
    }

    element.on("focus", hide);
    element.on("blur", show);
}