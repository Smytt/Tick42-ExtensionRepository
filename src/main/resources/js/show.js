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
            },
        })
    }

    var submitView = () => {
        $.ajax({
            url: './templates/submit-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
                $content.find('button').on('click', app.submit)
            },
            error: () => {
                var err = "Could not load submit page";
                console.log(err);
            },
        })
    }
    var submitView = () => {
        $.ajax({
            url: './templates/submit-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
                remote.getUserExtensions();
            },
            error: () => {
                var err = "Could not load submit page";
                console.log(err);
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
                $('#search-results .one-item').on('click', app.getExtensionView)
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
            url: './templates/top-downloads.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#top-downloads").remove();
                $content.append($html);
                $('#top-downloads .one-item').on('click', app.getExtensionView)
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
            url: './templates/most-recent-uploads.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, uploadsResults);
                $content.find("#most-recent-uploads").remove();
                $content.append($html);
                $('#most-recent-uploads .one-item').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load extension page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var userExtensions = (extensions) => {
        $.ajax({
            url: './templates/user-extensions.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#user-extensions").remove();
                $content.append($html);
                $('#user-extensions .one-item').on('click', app.getExtensionView)
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
        uploadsResult,
        submitView,
        userExtensions
    }
})()