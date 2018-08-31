var render = (() => {
    var searchResults = (results, query) => {
        var extensions = {
            extensions: results['extensions'],
            query,
            count: results['totalResults'],
        }
        show.searchResults(extensions);
    }

    var homeNew = (results) => {
        var extensions = {
            extensions: results,
        }
        show.homeNew(extensions);
    }

    var homePopular = (results) => {
        var extensions = {
            extensions: results
        }
        show.homePopular(extensions);
    }

    var homeFeatured = (results) => {
        var results = {
            extensions: results,
        }
        show.homeFeatured(results);
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
        homeNew,
        homePopular,
        extensionInfo,
        homeFeatured,
        userExtensions
    }
})();