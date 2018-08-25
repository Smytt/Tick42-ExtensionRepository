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

            show.searchResults(res)

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

            render.downloadsResult(res)

            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }
    var mostRecentUploads = (count, callBack) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/mostRecentUploads/" + count,
            success: (res) => {

            render.uploadsResult(res)

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
    return {
        searchByName,
        searchByTag,
        mostRecentUploads,
        mostDownloads,
        submitExtension
    }
})()