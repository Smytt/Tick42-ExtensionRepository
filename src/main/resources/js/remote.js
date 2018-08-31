remote = (() => {

    const base = "http://localhost:8080";

    var isAuth = () => {
        return localStorage.getItem('Authorization') !== null;
    }

    var searchByTag = (tagName) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/tags/" + tagName,
            success: (result) => {
                render.extensions(result);

            },
            error: (e) => {
                console.log("Couldn't retrieve extensions");
            }
        })
    }

    var searchByName = (extensionTitle) => {
        var token = document.cookie;
        console.log(token);
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/search/" + extensionTitle,
            success: (res) => {
                render.searchResults(res, extensionTitle);
            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }

    var loadByTimesDownloaded = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extension/search" + "?name=" + name + "&orderBy=downloads" + "&perPage=" + perPage
        })
    }

    var loadByUploadDate = (name, page, perPage) => {
       return $.ajax({
            type: 'GET',
            url: base + "/api/extension/search" + "?name=" + name + "&orderBy=date" + "&perPage=" + perPage
        })
    }
    var loadFeatured = () => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extension/featured"
        })
    }

    var submitExtension = (extension) => {
        $.ajax({
            type: 'POST',
            url: base + "/api/extension/add",
            data: JSON.stringify(extension),
            contentType: 'application/json',
            headers: {
                "Authorization": JSON.parse(localStorage.getItem("Authorization"))
            },
            success: () => {

            },
            error: (e) => {
                console.log("Couldn't submit extension");
            }
        })
    }

    var getUserExtensions = () => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/userExtensions/secured",
            headers: {
                "Authorization": JSON.parse(localStorage.getItem("Authorization"))
            },
            success: (res) => {

                render.userExtensions(res);

            },
            error: (e) => {
                console.log("Couldn't retrieve user's extensions")
            }
        })
    }

    var login = (user) => {
        $.ajax({
            type: 'POST',
            url: base + "/api/user/login",
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: (res) => {
                let token = JSON.stringify(res);
                localStorage.setItem('Authorization', token);
                app.home()
            },
            error: (e) => {
                console.log("Couldn't login");
            }
        })
    }
    var changeActiveState = (username, state) => {
        $.ajax({
            type: 'POST',
            url: base + '/api/user/changeActiveState' + username + state,
            data: JSON.stringify(username),
            contentType: 'application/json',
            headers: {
                'Authorization': JSON.parse(localStorage.getItem('Authorization'))
            },
            success: (res) => {

            },
            error: (e) => {
                console.log("Couldn't change state");
            }
        })
    }
    var listAllUsers = () => {
        $.ajax({
            type: 'GET',
            url: base + '/api/user/all',
            headers: {
                "Authorization": JSON.parse(localStorage.getItem("Authorization"))
            },
            success: (res) => {
                render.allUsers(res);


            },
            error: (e) => {
                console.log("couldn't retrieve users")
            }
        })
    }
    var getExtension = (id) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/" + id,
            success: (res) => {
                render.extensionInfo(res);

            },
            error: (e) => {
                console.log("Couldn't retrieve extension by id")
            }
        })
    }
    var getUsers = () => {
        $.ajax({
            type: 'GET',
            url: base + "/api/user/listAll",
            success: (res) => {
                render.users(res);
                console.log(res);
                console.log(res);
            },
            error: (e) => {
                console.log("Couldn't retrieve extension by id")
            }
        })
    }
    return {
        isAuth,
        searchByName,
        searchByTag,
        loadByUploadDate,
        loadByTimesDownloaded,
        submitExtension,
        getUserExtensions,
        login,
        getExtension,
        loadFeatured,
        changeActiveState,
        listAllUsers,
        getUsers
    }
})()
