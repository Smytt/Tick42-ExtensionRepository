let render = (() => {

    let searchResults = (res, query, orderBy) => {
        res['prev'] = +res['currentPage'] > 1;
        res['next'] = +res['currentPage'] < res['totalPages'];
        res['prevNum'] = +res['currentPage'] - 1;
        res['nextNum'] = +res['currentPage'] + 1;
        res['query'] = query;
        res['orderBy'] = orderBy
        return res;
    }

    let extension = (extension) => {
        extension['uploadDate'] = moment(extension['uploadDate']).format('MMM, DD YYYY HH:mm:ss');
        extension['lastCommit'] = moment(extension['lastCommit']).format('MMM, DD YYYY HH:mm:ss');
        extension['isOwn'] = localStorage.getItem('id') == extension['ownerId'];
        extension['isAdmin'] = localStorage.getItem('role') == 'ROLE_ADMIN';
        extension['rating'] = +extension['rating'].toFixed(2);
        return extension;
    }

    let profile = (profile) => {
        profile['isOwn'] = localStorage.getItem('id') == profile['id'];
        profile['isAdmin'] = localStorage.getItem('role') == 'ROLE_ADMIN';
        profile['rating'] = +profile['rating'].toFixed(2);
        return profile;
    }

    let edit = (extension) => {
        extension['description'] = extension['description'].replace(/<br \/>/gi, '\n');
        return extension;
    }

    let shortenTitle = (page) => {
        page['extensions'].forEach(extension => {
            extension['name'] = extension['name'].length >= 20 ? extension['name'].substr(0, 17) + "..." : extension['name']
            extension['rating'] = +extension['rating'].toFixed(2);
        })
        return page;
    }

    let shortenTitleWhenAllLoaded = (extensions) => {
        extensions.forEach(extension => {
            extension['name'] = extension['name'].length >= 20 ? extension['name'].substr(0, 17) + "..." : extension['name']
            extension['rating'] = +extension['rating'].toFixed(2);
        })
        return extensions;
    }

    return {
        searchResults,
        extension,
        profile,
        edit,
        shortenTitle,
        shortenTitleWhenAllLoaded
    }
})();