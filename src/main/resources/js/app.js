var app = (() => {

    var home = (e) => {
        preventDefault(e);
        remote.mostRecentUploads(10);
        remote.mostDownloads(10);
    }

    var start = () => {

        $("#search").on('click', getSearchView);

        home();
    }
    var getSearchView = (e) => {
        preventDefault(e);
        show.searchView();
    }
    var search = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        var extensionName = $('#name').val();
        remote.searchByName(extensionName, searchCallback);
    }

    function searchCallback(response) { render.searchResults(response,"dsadas") }

    function preventDefault(e) {
        if (e) {
            e.preventDefault();
            e.stopPropagation();
        }
    }
        function hitEnter(e) {
            if (e.type === 'keypress' && e.which !== 13) {
                return false;
            }
            return true
        }


    return {
        start,
        home,
        search
    }
})();

app.start();