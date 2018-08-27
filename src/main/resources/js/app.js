var app = (() => {

    var count = 10;
    var home = (e) => {
        preventDefault(e);
        remote.mostRecentUploads(count,remote.mostRecentUploads);
        remote.mostDownloads(count,remote.mostDownloads);
    }


    var start = () => {
        $("#login").on('click', getLoginView);
        $("#search").on('click', getSearchView);
        $("#submit").on('click', getSubmitView);
        $("#user-results").on('click', getUserExtensions);
        home();
    }

    var getSearchView = (e) => {
        preventDefault(e);
        show.searchView();
    }

    var getLoginView = (e) => {
        preventDefault(e);
        show.loginView();
    }

    var getUserExtensions = (e) => {
        preventDefault(e);
        remote.getUserExtensions();
    }
    var getExtensionView = function (e){
        preventDefault(e);
        var id = $(this).attr('extensionId');
        remote.getExtension(id);
    }
    var getSubmitView = (e) => {
        preventDefault(e);
        show.submitView();
    }

    var search = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        extensionName = $('#name').val();
        remote.searchByName(extensionName);
    }

    var login = function (e) {
        preventDefault(e);
        if (!hitEnter(e)) return;

        var username = $('#username').val();
        var password = $('#password').val();

        var user = {
            username,
            password,
        }
        remote.login(user);
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

        render.submitExtension(extension)
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
        submit,
        login,
        getExtensionView
    }
})();

app.start();