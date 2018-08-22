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
            success: (result) => {
                render.searchResults(result, extensionTitle);
            },
            error: (e) => {
                console.log("Couldn't retrieve extensions")
            }
        })
    }
    return {
        searchByName,
        searchByTag
    }
})()