var render = (() => {
    var searchResults = (results, searchedTittle) => {
        var extensions = {
            results,
            searchedTittle,
            count: results.length,
        }
        show.searchResults(extensions);
    }
    var uploadsResult = (results, count) => {
        var extensions = {
            results,
            count
        }
        show.uploadsResult(extensions);
    }

    var downloadsResult = (results, count) => {
        var extensions = {
            results,
            count
        }
        show.mostDownloadsResult(extensions);
    }

    var featuredResults = (results) => {
        var results = {
            results,
        }
        show.featuredResults(results);
    }

    var userExtensions = (results) => {
    //if render required
        var extensions = {
        results
        }
        show.userExtensions(extensions);
    }
    var extensionInfo = (extension) => {
        //if render required
    show.extensionView(extension);
    }

    var allUsers = (results) => {
        var users = {
            results
        }
        show.adminView(users);
    }
    return {
        searchResults,
        uploadsResult,
        downloadsResult,
        extensionInfo,
        featuredResults,
        userExtensions,
        users
    }
})();