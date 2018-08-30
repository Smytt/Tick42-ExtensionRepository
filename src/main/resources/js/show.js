var show = (() => {

    var $content =$('#content');

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

    var searchResults = (extensions) => {
        $.ajax({
            url: './templates/search-results.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#search-results").remove();
                $content.empty();
                $content.append($html);
                $('#search-results .one').on('click', app.getExtensionView);
            },
            error: () => {
                var err = "Could not load extension page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var adminView = () => {
        $.ajax({
            url: './templates/admin-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
        },
            error: () => {
                var err = "Could not load admin page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var loginView = () => {
        $.ajax({
            url: './templates/login-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
                $content.find('button').on('click', app.login)
            },
            error: () => {
                var err = "Could not load login page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var registerView = () => {
        $.ajax({
            url: './templates/register-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.empty();
                $content.append($html);
                $content.find('button').on('click', app.register)
        },
            error: () => {
                var err = "Could not load register page";
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
                $('#most-recent-uploads .one').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load upload extensions";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var featuredResults = (featuredResults) => {
        $.ajax({
            url: './templates/featured-results.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, featuredResults);
                $content.find("#featured-results").remove();
                $content.append($html);
                $('#featured-results .one').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load featured extensions";
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
                var err = "Could not load user extensions";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    var extensionView = (extension) => {
        $.ajax({
            url: './templates/extension-view.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extension);
                $content.empty();
                $content.append($html);
                console.log(extension);
                console.log(extension['name'])

            },
            error: () => {
                var err = "Could not load extension view";
                console.log(err);
                $content.prepend(err);
            },
        })
    }
    return {
        searchResults,
        mostDownloadsResult,
        uploadsResult,
        submitView,
        userExtensions,
        loginView,
        extensionView,
        featuredResults,
        adminView,
        registerView
    }
})()