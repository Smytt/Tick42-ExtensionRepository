let show = (() => {

    let $content = $('#content');
    let $nav = $('nav');

    let home = () => {
        $.ajax({
            url: './templates/home.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $content.html($html);
                app.getHome();
            }
        })
    }

    let users = (users, buttonId) => {
        $.ajax({
             url: './templates/list-users.html',
             success: (tmpl) => {
                let $html = Mustache.render(tmpl, users);
                $('#admin-view-container').html($html);
                $('.action-btn button').removeClass('current');
                $('.action-btn #' + buttonId).addClass('current');
             }
         })
    }

    let adminView = (users) => {
        $.ajax({
             url: './templates/admin-view.html',
             success: (tmpl) => {
                 let $html = Mustache.render(tmpl);
                 $content.html($html);
                 show.users(users, "all")
             }
         })
    }

    let userNav = () => {
        $.ajax({
            url: './templates/nav-user.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $nav.html($html);
            }
        })
    }

    let guestNav = () => {
        $.ajax({
            url: './templates/nav-guest.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $nav.html($html);
            }
        })
    }

    let adminNav = () => {
        $.ajax({
            url: './templates/nav-admin.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $nav.html($html);
                $('#orderBy button').removeClass('current')
            }
        })
    }

    let homeFeatured = (page) => {
        $.ajax({
            url: './templates/home-featured.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, page);
                $("#featured").html($html);
            }
        })
    }

    let homePopular = (extensions) => {
        $.ajax({
            url: './templates/home-popular.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extensions);
                $("#popular").html($html);
            }
        })
    }

    let homeNew = (extensions) => {
        $.ajax({
            url: './templates/home-new.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extensions);
                $("#new").html($html);
            }
        })
    }

    let submit = () => {
        $.ajax({
            url: './templates/submit-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $content.html($html);
            }
        })
    }


    let edit = (extension) => {
        $.ajax({
            url: './templates/edit-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extension);
                $content.html($html);
            }
        })
    }

    let results = (results) => {
        $.ajax({
            url: './templates/search-results.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, results);
                $content.html($html);
                $('#orderBy button').removeClass('current')
                $('#orderBy button[orderBy="' + results['orderBy'] + '"]').addClass('current')
            }
        })
    }

    let login = () => {
        $.ajax({
            url: './templates/login-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $content.html($html);
            }
        })
    }

    let register = () => {
        $.ajax({
            url: './templates/register-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $content.html($html);
            }
        })
    }

    let registerAdminView = () => {
        $.ajax({
            url: './templates/register-admin.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $('#admin-view-container').empty();
                $('#admin-view-container').html($html);
            }
        })
    }

    let gitHubSettingsView = () => {
        $.ajax({
            url: './templates/github-settings.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl);
                $('#admin-view-container').empty();
                $('#admin-view-container').html($html);
            }
        })
    }

    let user = (extensions) => {
        $.ajax({
            url: './templates/profile-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extensions);
                $content.html($html);
            }
        })
    }

    let extension = (extension) => {
        $.ajax({
            url: './templates/extension-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extension);
                $content.html($html);

            }
        })
    }

    let tag = (tag) => {
        $.ajax({
            url: './templates/tag-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, tag);
                $content.html($html);
            }
        })
    }

    let state = (user) => {
        $.ajax({
            url: './templates/user-state.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, user);
                $('.user-state-controls').html($html);
            }
        })
    }

    let pending = (extensions) => {
        $.ajax({
            url: './templates/pending-view.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extensions);
                $content.html($html);
            }
        })
    }

    let pendingState = (extension) => {
        $.ajax({
            url: './templates/pending-state.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extension);
                $('#pending-state').html($html);
            }
        })
    }

    let featuredState = (extension) => {
        $.ajax({
            url: './templates/featured-state.html',
            success: (tmpl) => {
                let $html = Mustache.render(tmpl, extension);
                $('#featured-state').html($html);
            }
        })
    }

    return {
        home,
        userNav,
        guestNav,
        adminNav,
        results,
        homeFeatured,
        homePopular,
        homeNew,
        submit,
        user,
        users,
        adminView,
        login,
        extension,
        register,
        registerAdminView,
        gitHubSettingsView,
        edit,
        tag,
        state,
        pending,
        pendingState,
        featuredState
    }
})()