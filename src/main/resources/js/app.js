var app = (() => {

    const perPage = 10;

    var home = (e) => {
        preventDefault(e);
        loadNav();

        show.loadHome();
    }

    var loadHome = () => {
        var count = 5;
        remote.loadFeatured().then(
            res => {
                render.homeFeatured(res);
            }
        );
        remote.loadByUploadDate("", 1, count).then(
            res => {
                render.homeNew(res)
            }
        );
        remote.loadByTimesDownloaded("", 1, count).then(
            res => {
                render.homePopular(res)
            }
        );
    }

    var search = (e) => {
        preventDefault(e);

        var query = $('#search-input').val().trim();

        if (query.length === 0) return;

        remote.loadByUploadDate(query, 1, perPage).then(
            res => {
                show.searchResults(res, query);
            }
        );
    }

    var discover = (e) => {
        preventDefault(e);

        remote.loadByUploadDate("", 1, perPage).then(
            res => {
                show.searchResults(res);
            }
        );
    }


    var getLoginView = (e) => {
        preventDefault(e);
        show.loginView();
    }
    var getAdminView = (e) => {
        preventDefault(e);
        show.adminView();
    }
    var getRegisterView = (e) => {
        preventDefault(e);
        show.registerView();
    }
    var getUserExtensions = (e) => {
        preventDefault(e);
        remote.getUserExtensions();
    }
    var getExtensionView = function (e) {
        preventDefault(e);
        var id = $(this).attr('extensionId');
        remote.getExtension(id);
    }
    var getSubmitView = (e) => {
        preventDefault(e);
        show.submitView();
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

        var name = $('#name').val();
        var version = $('#version').val();
        var description = $('#description').val();
        var github = $('#github').val();
        var tags = $('#tags').val();

        var extension = {
            name,
            version,
            description,
            github,
            tags
        }
        remote.submitExtension(extension)
    }

    function preventDefault(e) {
        if (e) {
            e.preventDefault();
            e.stopPropagation();
        }
    }

    var loadNav = () => {
        if (remote.isAuth()) {
            show.userNav();
        }
        else {
            show.guestNav();
        }
    }

    var attachNavEvents = () => {
        $("#login").on('click', getLoginView);
        $("#search").on('click', search);
        $("#users").on('click', getAdminView);
        $("#register").on('click', getRegisterView);
        $("#submit").on('click', getSubmitView);
        $("#user-results").on('click', getUserExtensions);
        $("#discover").on('click', discover);
    }

    function hitEnter(e) {
        if (e.type === 'keypress' && e.which !== 13) {
            return false;
        }
        return true
    }

    return {
        attachNavEvents,
        home,
        search,
        submit,
        login,
        getExtensionView,
        loadHome
    }
})();

app.home();