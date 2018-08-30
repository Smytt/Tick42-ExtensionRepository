remote = (() => {

    const base = "http://localhost:8080";

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

    var mostDownloads = (count) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/mostDownloads/" + count,
            success: (res) => {
                render.downloadsResult(res);

            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }
    var mostRecentUploads = (count) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/mostRecentUploads/" + count,
            success: (res) => {
                render.uploadsResult(res);

            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }
    var featuredExtensions = () => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/featured",
            success: (res) => {
                render.featuredResults(res);
            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }

    var submitExtension = (extension) => {
        $.ajax({
            type: 'POST',
            url: base + "/api/extension/add",
            data: JSON.stringify(extension),
            contentType: 'application/json',
            headers: {
                "Authorization":JSON.parse(localStorage.getItem("Authorization"))
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
                "Authorization":JSON.parse(localStorage.getItem("Authorization"))
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
                let dataToStore = JSON.stringify(res);
                localStorage.setItem('Authorization', dataToStore);
                document.cookie =
                 'cookie1='+dataToStore+'; expires=Fri, 3 Aug 2019 20:47:11 UTC; path=/'
            },
            error: (e) => {
                console.log("Couldn't login");
            }
        })
    }
    var changeActiveState = (username, state) =>{
        $.ajax({
            type: 'POST',
            url: base + '/api/user/changeActiveState' + username + state,
            data: JSON.stringify(username),
            contentType: 'application/json',
            headers: {
                'Authorization':JSON.parse(localStorage.getItem('Authorization'))
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
            type:'GET',
            url: base + '/api/user/listAll',
            headers: {
                "Authorization":JSON.parse(localStorage.getItem("Authorization"))
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
            },
            error: (e) => {
                console.log("Couldn't retrieve extension by id")
            }
        })
    }
    return {
        searchByName,
        searchByTag,
        mostRecentUploads,
        mostDownloads,
        submitExtension,
        getUserExtensions,
        login,
        getExtension,
        featuredExtensions,
        changeActiveState,
        listAllUsers,
        getUsers
    }
})()
