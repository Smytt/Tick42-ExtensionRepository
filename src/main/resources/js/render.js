var render = (() => {
    var searchResults = (results, title) => {
        var extensions = {
            results,
            title
        }
        show.searchResults(extensions)
    }
    var uploadsResult = (results, count) => {
        var extensions = {
            results,
            count
        }
        show.uploadsResult(extensions)
    }

    var downloadsResult = (results, count) => {
        var extensions = {
            results,
            count
        }
        show.mostDownloadsResult(extensions)
    }

    var submitExtension = (extension) => {
//        formatTags(extensions)
        remote.submitExtension(extension)

    }
    var userExtensions = (extensions) => {
        var extension = {
        results
        }
        show.userExtensions(extensions)
    }

    return {
        searchResults,
        uploadsResult,
        downloadsResult,
        submitExtension
    }
})();