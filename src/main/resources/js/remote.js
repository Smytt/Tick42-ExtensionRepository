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
                var dataToStore = JSON.stringify(res);
               localStorage.setItem('Authorization', dataToStore);
                },
                error: (e) => {
                    console.log("Couldn't login");
                }
            })
        }
    var getExtension = (id) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/get/" + id,
            success: (res) => {

            render.extensionInfo(res);

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
        featuredExtensions
    }
})()