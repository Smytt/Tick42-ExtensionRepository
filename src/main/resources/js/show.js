var show = (() => {

    var $content = $('#content');

    var searchView = () => {
        $.ajax({
            url: './templates/search.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
                $content.find('button').on('click', app.search)
            },
            error: () => {
                var err = "Could not load search page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }

    var searchResults = (extensions) => {
        $.ajax({
            url: './templates/search-results.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#search-results").remove();
                $content.append($html);
                $('#search-results .one-item').on('click', app.getMovieView)
            },
            error: () => {
                var err = "Could not load extension page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var mostDownloadsResult = (extensions) => {
            $.ajax({
                url: './templates/home-page-results.html',
                success: (tmpl) => {
                    var $html = Mustache.render(tmpl, extensions);
                    $content.find("#home-page-results").remove();
                    $content.append($html);
                    $('#home-page-results .one-item').on('click', app.getMovieView)
                },
                error: () => {
                    var err = "Could not load extension page";
                    console.log(err);
                    $content.prepend(err);
                },
            })
        }
            var uploadsResult = (uploadsResults) => {
                    $.ajax({
                        url: './templates/home-page-results.html',
                        success: (tmpl) => {
                            var $html = Mustache.render(tmpl, uploadsResults);
                            $content.find("#home-page-results").remove();
                            $content.append($html);
                            $('#home-page-results .one-item').on('click', app.getMovieView)
                        },
                        error: () => {
                            var err = "Could not load extension page";
                            console.log(err);
                            $content.prepend(err);
                        },
                    })
                }
    return {
        searchView,
        searchResults,
        mostDownloadsResult,
        uploadsResult
    }
})()