var app = (() => {

    var count = 10;
    var home = (e) => {
        preventDefault(e);
        remote.mostRecentUploads(count,uploadsCallBack);
        remote.mostDownloads(count, downloadsCallBack);
    }

    function uploadsCallBack(response) {
    console.log(count);
        render.downloadsResult(response,count)

    }

    function downloadsCallBack(response) {
        render.uploadsResult(response,count)

    }

    var start = () => {

        $("#search").on('click', getSearchView);

        home();
    }

    var getSearchView = (e) => {
        preventDefault(e);
        show.searchView();
    }

    var searchFieldName;

    var search = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        searchFieldName = $('#name').val();
        remote.searchByName(searchFieldName, searchCallback);
    }

    function searchCallback(response) {
        render.searchResults(response,searchFieldName)

    }

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