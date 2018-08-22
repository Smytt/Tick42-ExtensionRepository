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

    var searchByName = (extensionTitle, callBack) => {
        $.ajax({
            type: 'GET',
            url: base + "/api/extension/search/" + extensionTitle,
            success: callBack,
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }

    var mostDownloads = (count) => {
            $.ajax({
                type: 'GET',
                url: base + "/api/extension/mostDownloads/" + count,
                success: (result) => {
                    render.downloadsResult(result, count);
                },
                error: (e) => {
                    console.log("Couldn't retrieve extensions")
                }
            })
        }
        var mostRecentUploads = (countUploads) => {
                $.ajax({
                    type: 'GET',
                    url: base + "/api/extension/mostRecentUploads/" + countUploads,
                    success: (resultUploads) => {
                    console.log(resultUploads);
                        render.uploadsResult(resultUploads, countUploads);
                    },
                    error: (e) => {
                        console.log("Couldn't retrieve extensions")
                    }
                })
            }
    return {
        searchByName,
        searchByTag,
        mostRecentUploads,
        mostDownloads
    }
})()