remote = (() => {

    const base = "http://localhost:8080";

    let isAuth = () => {
        return localStorage.getItem('Authorization') !== null;
    }

    let getTag = (tagName) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/tag/" + tagName,
        })
    }

    let loadByTimesDownloaded = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=downloads" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadByUploadDate = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=date" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadFeatured = () => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/featured"
        })
    }

    let submitExtension = (extension) => {
        $.ajax({
            type: 'POST',
            url: base + "/api/extensions",
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

    let getUserProfile = (id) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/user/" + id,
        })
    }

    let login = (user) => {
        $.ajax({
            type: 'POST',
            url: base + "/api/user/login",
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: (res) => {
                // let token = JSON.stringify(data); //todo
                localStorage.setItem('Authorization', res['token']);
                localStorage.setItem('id', res['id']);
                localStorage.setItem('username', res['username']);
                localStorage.setItem('role', res['role']);
                app.home();
            },
            error: (e) => {
                console.log("Couldn't login");
            }
        })
    }

    let changeActiveState = (username, state) => {
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

    let getExtension = (id) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/" + id
        })
    }

    return {
        isAuth,
        getTag,
        loadByUploadDate,
        loadByTimesDownloaded,
        submitExtension,
        getUserProfile,
        login,
        getExtension,
        loadFeatured,
        changeActiveState,
    }
})()
