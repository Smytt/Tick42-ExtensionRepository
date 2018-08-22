var render = (() => {
var searchResults = (results, title) => {
        var extensions = {
            results,
            title
        }
        show.searchResults(extensions)
    }
    var uploadsResult = (resultsUpload, countUploads) => {
            var extensionsUploads = {
                resultsUpload,
                countUploads
            }
            show.uploadsResult(extensionsUploads)
        }

            var downloadsResult = (results, count) => {
                    var extensions = {
                        results,
                        count
                    }
                    show.mostDownloadsResult(extensions)
                }

     return {
        searchResults = se,
        uploadsResult,
        downloadsResult
    }
})();