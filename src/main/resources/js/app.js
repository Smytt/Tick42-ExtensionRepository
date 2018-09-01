let app = (() => {

    let $body = $('body');

    const PAGE_SIZE = 10;
    const POPULAR_HOME_PAGE_COUNT = 5;
    const NEW_HOME_PAGE_COUNT = 5;

    let home = (e) => {
        preventDefault(e);
        loadNav();

        show.home();
    }

    let getHome = () => {
        remote.loadFeatured().then(
            res => {
                show.homeFeatured(res);
            }
        );
        remote.loadByUploadDate("", 1, NEW_HOME_PAGE_COUNT).then(
            res => {
                show.homeNew(res)
            }
        );
        remote.loadByTimesDownloaded("", 1, POPULAR_HOME_PAGE_COUNT).then(
            res => {
                show.homePopular(res)
            }
        );
    }

    let search = (e) => {
        preventDefault(e);

        let orderBy = $(this).attr('orderBy');

        switch (orderBy) {
            case 'date':
                searchByUploadDate(e);
                break;
            case 'name':
                searchByUploadDate(e);
                break;
            case 'downloads':
                searchByUploadDate(e);
                break;
            case 'commits':
                searchByUploadDate(e);
                break;
        }
    }

    function searchByUploadDate(e) {
        preventDefault(e);
        let page = 1;
        let query = '';

        if ($(this).attr('pagenum')) {
            page = +$(this).attr('pagenum');
            query = $('#query').text();
        }

        else if ($(this).attr('id') === 'search') {
            query = $('#search-input').val().trim();
            if (query.length === 0) return;
        }

        remote.loadByUploadDate(query, page, PAGE_SIZE).then(
            res => {
                res = render.searchResults(res, query)
                show.results(res);
            }
        );
    }

    let getExtensionView = function (e) {
        preventDefault(e);

        let id = $(this).attr('extensionId');

        remote.getExtension(id).then(
            res => {
                res = render.extension(res)
                show.extension(res)
            }
        );
    }

    let getExtensionsByTag = function (e) {
        preventDefault(e);

        let tagName = $(this).attr('tagName');

        remote.getTag(tagName).then(
            show.tag
        )
    }

    let getProfileView = function (e) {
        preventDefault(e);
        let id = $(this).attr('userId');
        remote.getUserProfile(id).then(
            res => {
                res = render.profile(res);
                show.user(res);
            }
        )
    }

    let getLoginView = (e) => {
        preventDefault(e);
        show.login();
    }

    let getRegisterView = (e) => {
        preventDefault(e);
        show.register();
    }

    let getSubmitView = (e) => {
        preventDefault(e);
        show.submit();
    }

    let register = function (e) {
        //todo
    }

    let login = function (e) {
        preventDefault(e);
        if (!hitEnter(e)) return;

        let username = $('#username').val();
        let password = $('#password').val();

        let user = {
            username,
            password,
        }
        remote.login(user);
    }

    let logout = (e) => {
        preventDefault(e);
        localStorage.clear();
        home();
    }

    let submit = (e) => {
        preventDefault(e);
        if (!hitEnter(e)) return;

        let name = $('#name').val();
        let version = $('#version').val();
        let description = $('#description').val();
        let github = $('#github').val();
        let tags = $('#tags').val();

        let extension = {
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

    let loadNav = () => {
        if (remote.isAuth()) {
            if (localStorage.getItem('role') === 'ROLE_ADMIN') {
                show.adminNav();
            }
            else show.userNav();
        }
        else {
            show.guestNav();
        }
    }

    function hitEnter(e) {
        if (e.type === 'keypress' && e.which !== 13) {
            return false;
        }
        return true
    }

    $body.on('click', '.logo a', home);
    $body.on('click', '#login', getLoginView)
    $body.on('click', '#logout', logout)
    $body.on('click', '#register', getRegisterView)
    $body.on('click', '#search, #discover', searchByUploadDate)
    $body.on('click', '#submit', getSubmitView)
    $body.on('click', '.one', getExtensionView)
    $body.on('click', '.pages-control a', search)
    $body.on('click', '#submit-btn', submit)
    $body.on('click', '#register-btn', register)
    $body.on('click', '#login-btn', login)
    $body.on('click', '.tags a', getExtensionsByTag)
    $body.on('click', '.user-link', getProfileView)

    $body.on('click', '#orderBy button', search)


    return {
        home,
        getHome
    }
})();

app.home();