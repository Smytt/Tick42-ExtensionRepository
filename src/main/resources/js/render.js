var render = (() => {
    var searchResults = (results, title) => {
        var extensions = {
            results,
            title
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

    var submitExtension = (extension) => {
//        formatTags(extensions)
        remote.submitExtension(extension);

    }
    var userExtensions = (extensions) => {
    //if some render required
        var extensions = {
        results
        }
        show.userExtensions(extensions);
    }
    var extensionInfo = (extension) => {
        //if some render required
    show.extensionView(extension);
    }

    return {
        searchResults,
        uploadsResult,
        downloadsResult,
        submitExtension,
        extensionInfo
    }
})();