var app = (() => {

    var count = 10;
    var home = (e) => {
        preventDefault(e);
        remote.mostRecentUploads(count,remote.mostRecentUploads);
        remote.mostDownloads(count,remote.mostDownloads);
    }


    var start = () => {

        $("#search").on('click', getSearchView);
        $("#submit").on('click', getSubmitView);
        home();
    }

    var getSearchView = (e) => {
        preventDefault(e);
        show.searchView();
    }

     var getSubmitView = (e) => {
            preventDefault(e);
            show.submitView();
     }

    var searchFieldName;

    var search = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        searchFieldName = $('#name').val();
        remote.searchByName(searchFieldName, remote);
    }

    var submit = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        var name = $('#title').val();
        var version = $('#version').val();
        var description = $('#description').val();
        var github = $('#github').val();
        var tags = $('#tags').val();

        var extension = {
            name,
            version,
            description,
            tags
        }

//        render.submitMovie(extension)
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
        search,
        submit
    }
})();

app.start();