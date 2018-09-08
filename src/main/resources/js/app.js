let app = (() => {

    let $body = $('body');

    const PAGE_SIZE = 10;
    const POPULAR_HOME_PAGE_COUNT = 5;
    const NEW_HOME_PAGE_COUNT = 5;

    let getHomeView = (e) => {
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
        remote.loadByUploadDate('', 1, NEW_HOME_PAGE_COUNT).then(
            res => {
                res = render.shortenTitle(res);
                show.homeNew(res)
            }
        );
        remote.loadByTimesDownloaded('', 1, POPULAR_HOME_PAGE_COUNT).then(
            res => {
                res = render.shortenTitle(res);
                show.homePopular(res)
            }
        );
    }

    let getUsers = function (e) {
        preventDefault(e);
        let state = $(this).attr('id');
        remote.getUsers(state).then(
            res => {
                show.users(res, state)
            }
        )
    }

    function getAdminView(e) {
        preventDefault(e);
        let state = "all"
        remote.getUsers(state).then(
            show.adminView
        )
    }

    function getGithubSettingsView(e) {
        preventDefault(e);
        remote.getCurrentGitHubSettings().then(
            res => {
                show.gitHubSettingsView(res);
                $('.admin-buttons button').removeClass('current');
                $(this).addClass('current');
            }

        )
    }

    function submitGithubSettings(e) {
        preventDefault(e);

        let username = $('#username').val();
        let token = $('#token').val();
        let rate = +$('#rate').val();
        let wait = +$('#wait').val();

        let gitHubSettings = {
            username,
            token,
            rate,
            wait
        }

        remote.setGitHubSettings(gitHubSettings).then(
            res => {
                getHomeView();
            }).catch(e => {
            handle(e);
        })
    }

    function refreshGitHub(e) {
        let extensionId = $(this).attr('extensionId');

        let m = $('.loading-block')

        m.find('div').html('fetching github...');
        m.fadeIn()
        remote.refreshGitHub(extensionId).then(
            res => {
                m.fadeOut();
                getExtensionView(null, extensionId)
            }
        ).catch(e => {
            m.fadeOut();
            handle(e)
        });
    }

    function search(e) {
        preventDefault(e);

        let orderBy = $(this).attr('orderBy');
        let page = 1;
        let query = '';

        if ($(this).attr('pagenum')) {
            page = $(this).attr('pagenum');
            query = $('#query').text();
        }

        else if ($(this).attr('id') === 'search') {
            query = $('#search-input').val().trim();
            if (query.length === 0) return;
        }

        else if ($(this).is('button')) {
            query = $('#query').text();
        }

        let request;

        switch (orderBy) {
            case 'date':
                request = remote.loadByUploadDate(query, page, PAGE_SIZE);
                break;
            case 'name':
                request = remote.loadByName(query, page, PAGE_SIZE);
                break;
            case 'downloads':
                request = remote.loadByTimesDownloaded(query, page, PAGE_SIZE);
                break;
            case 'commits':
                request = remote.loadByLatestCommit(query, page, PAGE_SIZE);
                break;
            default:
                return;
        }

        request.then(
            res => {
                res = render.searchResults(res, query, orderBy)
                show.results(res);
            }
        )
    }

    let getExtensionView = function (e, id) {
        preventDefault(e);

        if (e) {
            id = $(this).attr('extensionId');
        }
        if (remote.isAuth()) {
            remote.getExtension(id).then(
                res => {
                    res = render.extension(res)
                    let userRating = res['currentUserRatingValue']
                    show.extension(res, userRating)
                }

                ).catch(e => {
                handle(e);
            });
        } else {
            remote.getExtension(id).then(
                res => {
                    res = render.extension(res)
                    show.extension(res, 0)
                }
            )
        }
    }

    let rateExtension = function (e) {
        preventDefault(e)

        if (remote.isAuth()) {
            $(this).closest('div').find('a').removeClass('current')
            $(this).addClass('current');
            let rating = $(this).attr('id');
            let timesRated = $('.info .rating').attr('timesRated');
            let extensionId = $(this).attr('extensionId');
            let currentRatedStatus = $('.info .rating').attr('id');
            let displayRating = {
                rating,
                timesRated
            }
            if (rating == currentRatedStatus) {
                console.log('same rating')
            } else {
                remote.rateExtension(extensionId, rating).then(
                    res => {
                        if(currentRatedStatus == 0){
                            timesRated = parseInt(timesRated) + 1;
                            console.log(timesRated)
                            displayRating = {
                                rating,
                                timesRated
                            }
                            $('.info .rating').attr('id', rating);
                            $('.info .rating').attr('timesRated', timesRated);
                          show.currentUserRating(displayRating)
                        }else{
                            show.currentUserRating(displayRating)
                            $('.info .rating').attr('id', rating);
                        }
                    }
                )
            }
        } else {
            $('.not-logged').empty();
            $('.not-logged').append('You must be logged in to rate this extension')
        }
    }

    let getTagView = function (e) {
        preventDefault(e);

        let tagName = $(this).attr('tagName');

        remote.getTag(tagName).then(
            res => {
                res = render.shortenTitle(res);
                show.tag(res)
            }
        ).catch(e => {
            handle(e);
        })
    }

    function getRegisterAdminView(e) {
        preventDefault(e);
        $('.admin-buttons button').removeClass('current');
        $(this).addClass('current');
        show.registerAdminView();
    }

    let getProfileView = function (e) {
        preventDefault(e);

        let id = $(this).attr('userId');

        remote.getUserProfile(id).then(
            res => {
                res = render.shortenTitle(res);
                res = render.profile(res);
                show.user(res);
            }
        ).catch(e => {
            handle(e);
        })
    }

    let getOwnProfileView = function (e) {
        preventDefault(e);

        let id = localStorage.getItem('id');

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

    let getEditView = function (e) {
        preventDefault(e);

        let extensionId = $(this).attr('extensionId');

        remote.getExtension(extensionId).then(
            res => {
                res = render.edit(res);
                show.edit(res);
            }
        ).catch(e => {
            handle(e);
        })
    }

    let getUsersView = (e) => {
        preventDefault(e);
        let id = $(this).attr('id');
        remote.getUsers().then(
            res => {
                res = render.profile(res);
                show.users(res);
            }
        )
    }
    let register = function (e) {
        preventDefault(e);

        let username = $('#username').val();
        let password = $('#password').val();
        let repeatPassword = $('#repeatPassword').val();

        let registrationForm = {
            username,
            password,
            repeatPassword
        }
        if ($(this).attr('id') == 'register-btn') {
            remote.register(registrationForm).then(
                res => {
                    login();
                }
            ).catch(e => {
                handle(e);
            })
        } else {
            remote.registerAdmin(registrationForm).then(
                res => {
                    getHomeView();
                }
            ).catch(e => {
                handle(e);
            })
        }
    }
    let login = function (e) {
        preventDefault(e);

        let username = $('#username').val();
        let password = $('#password').val();

        let user = {
            username,
            password,
        }

        remote.login(user).then(
            res => {
                localStorage.setItem('Authorization', res['token']);
                localStorage.setItem('id', res['id']);
                localStorage.setItem('username', res['username']);
                localStorage.setItem('role', res['role']);
                getHomeView();
            }
        ).catch(e => {
            handle(e);
        })
    }
    let logout = (e) => {
        preventDefault(e);
        localStorage.clear();
        getHomeView();
    }

    let setPublishedState = function (e) {
        preventDefault(e);

        let id = $(this).attr('extensionId');
        let state = $(this).attr('setState');

        remote.setPublishedState(id, state).then(
            res => {
                show.pendingState(res)
            }
        ).catch(e => console.log(e['responseText']));
    }

    let setFeaturedState = function (e) {
        preventDefault(e);

        let id = $(this).attr('extensionId');
        let state = $(this).attr('setState');

        remote.setFeaturedState(id, state).then(
            show.featuredState
        ).catch(e => console.log(e['responseText']));
    }

    let deleteExtension = function (e) {
        preventDefault(e);

        let extensionId = $(this).attr('extensionId');
        let m = $('.loading-block')
        m.find('div').html('deleting extenson...');
        m.fadeIn()
        setTimeout(() => {
            remote.deleteExtension(extensionId).then(
                res => {
                    getHomeView();
                }
            );
        }, 1000)

    }

    function setUserState(e) {
        preventDefault(e);

        let newState = $(this).attr('newState');
        let userId = $(this).attr('userId');
        remote.setUserState(userId, newState).then(
            show.state
        ).catch(e => console.log(e['responseText']));
    }

    function setMultipleUsersState(e) {
        preventDefault(e);
        let newState = $(this).attr('id');
        let userId = $(this).attr('userId');
        let state = $('.action-btn .current').attr('id')
        console.log(state);
        remote.setUserState(userId, newState).then(
            res => {
                remote.getUsers(state).then(
                    res => {
                        show.listUsersAfterStateChange(res)
                    })
            }
        ).catch(e => console.log(e['responseText']));

    }

    let submit = function (e) {
        preventDefault(e);
        if (!hitEnter(e)) {
            return;
        }

        let extensionId = $(this).attr('extensionId');

        let name = $('#name').val();
        let version = $('#version').val();
        let description = $('#description').val().replace(/\n\r?/g, '<br />');
        let github = $('#github').val();
        let tags = $('#tags').val();

        let file = $('#file').prop('files')[0];
        let fileData = new FormData();
        fileData.append('file', file);

        let image = $('#image').prop('files')[0];
        let imageData = new FormData();
        imageData.append('image', image);


        let extension = {
            name,
            version,
            description,
            github,
            tags
        }

        let m = $('.loading-block')
        //new submit
        if ($(this).attr('id') === 'submit-btn') {
            m.find('div').html('linking github...');
            m.fadeIn();
            remote.submitExtension(extension).then(
                res => {
                    let extensionId = res['id'];
                    if (file) {
                        m.find('div').html('uploading file...');
                        remote.submitFile(extensionId, fileData).then(
                            resFile => {
                                if (image) {
                                    m.find('div').html('uploading image...');
                                    remote.submitImage(extensionId, imageData).then(
                                        resImage => {
                                            getExtensionView(null, extensionId)
                                        }
                                    ).catch(e => {
                                        m.fadeOut()
                                        handle(e);
                                    })
                                }
                                else {
                                    getExtensionView(null, extensionId)
                                }
                            }
                        ).catch(e => {
                            m.fadeOut()
                            handle(e);
                        })
                    }
                    else if (image) {
                        m.find('div').html('uploading image...');
                        remote.submitImage(extensionId, fileData).then(
                            resFile => {
                                if (file) {
                                    m.find('div').html('uploading file...');
                                    remote.submitFile(extensionId, imageData).then(
                                        resImage => {
                                            getExtensionView(null, extensionId)
                                        }
                                    ).catch(e => {
                                        m.fadeOut()
                                        handle(e);
                                    })
                                }
                                else {
                                    getExtensionView(null, extensionId)
                                }
                            }).catch(e => {
                            m.fadeOut()
                            handle(e);
                        })
                    }
                    else {
                        getExtensionView(null, extensionId);
                    }
                }
            ).catch(e => {
                m.fadeOut()
                handle(e);
            })
        }
        // edit existing
        else {
            m.find('div').html('linking github...');
            m.fadeIn();
            remote.editExtension(extensionId, extension).then(
                res => {
                    let extensionId = res['id'];
                    if (file) {
                        m.find('div').html('uploading file...');
                        remote.submitFile(extensionId, fileData).then(
                            resFile => {
                                if (image) {
                                    m.find('div').html('uploading image...');
                                    remote.submitImage(extensionId, imageData).then(
                                        resImage => {
                                            getExtensionView(null, extensionId)
                                        }
                                    ).catch(e => {
                                        m.fadeOut()
                                        handle(e);
                                    })
                                }
                                else {
                                    getExtensionView(null, extensionId)
                                }
                            }
                        ).catch(e => {
                            m.fadeOut()
                            handle(e);
                        })
                    }
                    else if (image) {
                        m.find('div').html('uploading image...');
                        remote.submitImage(extensionId, imageData).then(
                            resImage => {
                                if (file) {
                                    m.find('div').html('uploading file...');
                                    remote.submitFile(extensionId, fileData).then(
                                        resFile => {
                                            getExtensionView(null, extensionId)
                                        }
                                    ).catch(e => {
                                        m.fadeOut()
                                        handle(e);
                                    })
                                }
                                else {
                                    getExtensionView(null, extensionId)
                                }
                            }
                        ).catch(e => {
                            m.fadeOut()
                            handle(e);
                        })
                    }
                    else {
                        getExtensionView(null, extensionId)
                    }
                }
            ).catch(e => {
                m.fadeOut()
                handle(e);
            })
        }
    }

    let getPendingExtensionsView = (e) => {
        preventDefault(e);

        remote.loadPending().then(
            res => {
                res = render.shortenTitleWhenAllLoaded(res);
                show.pending(res)
            }
        )
    }

    let getChangePasswordView = function (e) {
        show.changePasswordView();
    }

    let changePassword = function (e) {
        preventDefault(e);
        let currentPassword = $('#current-password').val();
        let newPassword = $('#new-password').val();
        let repeatNewPassword = $('#repeat-new-password').val();

        let changePassword = {
            currentPassword,
            newPassword,
            repeatNewPassword
        }

        remote.changePassword(changePassword).then(
            res => {
                getHomeView();
            }
        ).catch(e => {
            handle(e);
        });
    }

    let downloadFile = function (e) {
        let extensionId = $(this).attr('extensionId');
        $('#downloadTimes').html(+$('#downloadTimes').html() + 1);
        remote.downloadFile(extensionId);
    }

    let selectFile = function () {
        let filename = $(this).val().split(/(\\|\/)/g).pop();
        let id = $(this).attr('id');
        $('label[for="' + id + '"]').find('span').html(filename);
    }

    function preventDefault(e) {
        if (e) {
            e.preventDefault();
            e.stopPropagation();
        }
    }

    function handle(e) {
        $('.errors').empty();
        try {
            console.log(e['responseJSON'])
            e['responseJSON'].forEach(error => $('.errors').append('<p><i class="fas fa-exclamation-triangle"></i>' + error + '</p>'));
        }
        catch (err) {
            console.log(e['responseJSON'])
            $('.errors').append('<p><i class="fas fa-exclamation-triangle"></i>' + e['responseText'] + '</p>');
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

    let start = () => {
        $body.on('click', '.logo a', getHomeView)
        $body.on('click', '#login', getLoginView)
        $body.on('click', '#admin', getAdminView)
        $body.on('click', '#active', getUsers)
        $body.on('click', '#blocked', getUsers)
        $body.on('click', '#all', getUsers)
        $body.on('click', '#users', getAdminView)
        $body.on('click', '#users', getAdminView)
        $body.on('click', '#logout', logout)
        $body.on('click', '#register', getRegisterView)
        $body.on('click', '#profile', getOwnProfileView)
        $body.on('click', '#pending', getPendingExtensionsView)
        $body.on('click', '#search, #discover', search)
        $body.on('click', '#submit', getSubmitView)
        $body.on('click', '.hw-extensions .one', getExtensionView)
        $body.on('click', '#git-settings', getGithubSettingsView)
        $body.on('click', '.pages-control a', search)
        $body.on('click', '#submit-btn', submit)
        $body.on('click', '#set-github-settings', submitGithubSettings)
        $body.on('click', '#submit-edit-btn', submit)
        $body.on('click', '#register-btn', register)
        $body.on('click', '#login-btn', login)
        $body.on('click', '.tags a', getTagView)
        $body.on('click', '.user-link', getProfileView)
        $body.on('click', '#register-admin', getRegisterAdminView)
        $body.on('click', '#register-admin-btn', register)
        $body.on('click', '.admin-view .one button', setMultipleUsersState)
        $body.on('click', '.admin-view .one a', getProfileView)
        $body.on('click', '#orderBy button', search)
        $body.on('click', '.user-state-controls button', setUserState)
        $body.on('click', '.action-btn #change-published-state', setPublishedState)
        $body.on('click', '.action-btn #edit', getEditView)
        $body.on('click', '.action-btn #delete', deleteExtension)
        $body.on('click', '.action-btn #refresh-github', refreshGitHub)
        $body.on('click', '.action-btn #change-featured-state', setFeaturedState)
        $body.on('change', '.submit-file', selectFile)
        $body.on('click', '#download-file', downloadFile)
        $body.on('click', '.rating a', rateExtension)
        $body.on('click', '.change-password-view', getChangePasswordView)
        $body.on('click', '#change-password-btn', changePassword)


        getHomeView();
    }

    return {
        start,
        getHome
    }
})();

app.start();