var render = (() => {
var searchResults = (results, title) => {
        var extensions = {
            results,
            title
        }
        show.searchResults(extensions)
    }

     return {
        searchResults
    }
})();