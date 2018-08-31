var show = (() => {

    var $content = $('#content');

    var loadHome = () => {
        $.ajax({
            url: './templates/home.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $content.html($html);
                app.loadHome();
            },
            error: () => {
                var err = "Could not load home page";
                console.log(err);
            },
        })
    }

    var userNav = () => {
        $.ajax({
            url: './templates/nav-user.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $("nav").html($html);
                app.attachNavEvents();
            },
            error: () => {
                var err = "Could not load user nav";
                console.log(err);
            },
        })
    }

    var guestNav = () => {
        $.ajax({
            url: './templates/nav-guest.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $("nav").html($html);
                app.attachNavEvents();
            },
            error: () => {
                var err = "Could not load user nav";
                console.log(err);
            },
        })
    }

    var adminNav = () => {
        $.ajax({
            url: './templates/nav-admin.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl);
                $("nav").html($html);
                app.attachNavEvents();
            },
            error: () => {
                var err = "Could not load user nav";
                console.log(err);
            },
        })
    }

    var homeFeatured = (extensions) => {
        $.ajax({
            url: './templates/home-featured.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#featured").html($html);
                $('#featured .one').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load featured extensions";
                console.log(err);
                $content.prepend(err);
            },
        })
    }

    var homePopular = (extensions) => {
        $.ajax({
            url: './templates/home-popular.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#popular").html($html);
                $('#popular .one').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load extension page";
                console.log(err);
                $content.prepend(err);
            },
        })
    }

    var homeNew = (extensions) => {
        $.ajax({
            url: './templates/home-new.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, extensions);
                $content.find("#new").html($html);
                $('#new .one').on('click', app.getExtensionView)
            },
            error: () => {
                var err = "Could not load upload extensions";
                console.log(err);
                $content.prepend(err);
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

    var searchResults = (res) => {
        $.ajax({
            url: './templates/search-results.html',
            success: (tmpl) => {
                var $html = Mustache.render(tmpl, res);
                $content.html($html);
                $('.one').on('click', app.getExtensionView);
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
                $content.find('form button').on('click', app.login)
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
        loadHome,
        userNav,
        guestNav,
        adminNav,
        searchResults,
        homePopular,
        homeNew,
        submitView,
        userExtensions,
        loginView,
        extensionView,
        homeFeatured,
        adminView,
        registerView
    }
})()