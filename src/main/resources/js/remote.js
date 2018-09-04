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

    let loadByUploadDate = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=date" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadByName = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=name" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadByTimesDownloaded = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=downloads" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadByLatestCommit = (name, page, perPage) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/filter" + "?name=" + name + "&orderBy=commits" + "&page=" + page + "&perPage=" + perPage
        })
    }

    let loadFeatured = () => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/featured"
        })
    }

    let submitExtension = (extension) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/extensions",
            data: JSON.stringify(extension),
            contentType: 'application/json',
            headers: {
                "Authorization": localStorage.getItem("Authorization")
            },
        })
    }

    let submitFile = (id, file) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/upload/file/" + id,
            data: file,
            contentType: false,
            processData: false
        })
    }

    let submitImage = (id, image) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/upload/image/" + id,
            data: image,
            contentType: false,
            processData: false
        })
    }

    let editExtension = (id, extension) => {
        return $.ajax({
            type: 'PATCH',
            url: base + "/api/extensions/" + id,
            data: JSON.stringify(extension),
            contentType: 'application/json',
            headers: {
                "Authorization": localStorage.getItem("Authorization")
            },
        })
    }

    let getUserProfile = (id) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/users/" + id,
            headers: {
                "Authorization": localStorage.getItem("Authorization")
            },
        })
    }

    let login = (user) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/users/login",
            data: JSON.stringify(user),
            contentType: 'application/json'
        })
    }

    let register = (user) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/users/register",
            data: JSON.stringify(user),
            contentType: 'application/json',
            headers: {
                "Authorization": localStorage.getItem("Authorization")
            },
        })
    }

    let registerAdmin = (user) => {
        return $.ajax({
            type: 'POST',
            url: base + "/api/auth/users/adminRegistration",
            data: JSON.stringify(user),
            contentType: 'application/json',
            headers: {
                "Authorization": localStorage.getItem("Authorization")
            },
        })
    }

    let setPublishedState = (id, state) => {
        return $.ajax({
            type: 'PATCH',
            url: base + '/api/extensions/' + id + '/status/' + state
        })
    }

    let setFeaturedState = (id, state) => {
        return $.ajax({
            type: 'PATCH',
            url: base + '/api/extensions/' + id + '/featured/' + state
        })
    }

    let deleteExtension = (id) => {
        return $.ajax({
            type: 'DELETE',
            url: base + '/api/extensions/' + id,
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            },
        })

    }

    let setUserState = (id, state) => {
        return $.ajax({
            type: 'PATCH',
            url: base + '/api/auth/users/setState/' + id + '/' + state,
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            }
        })
    }

    let getExtension = (id) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/extensions/" + id,
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            }
        })
    }

    let getUsers = (state) => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/auth/users/all" + "?state=" + state,
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            }
        })
    }



    let loadPending = () => {
        return $.ajax({
            type: 'GET',
            url: base + "/api/auth/extensions/unpublished",
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            }
        })
    }


    return {
        isAuth,
        getTag,
        loadByUploadDate,
        loadByName,
        loadByTimesDownloaded,
        loadByLatestCommit,
        submitExtension,
        submitFile,
        submitImage,
        editExtension,
        getUserProfile,
        login,
        getUsers,
        getExtension,
        setPublishedState,
        setFeaturedState,
        deleteExtension,
        loadFeatured,
        loadPending,
        setUserState,
        register,
        registerAdmin
    }
})()
