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
        return extension;
    }

    let profile = (profile) => {
        profile['isOwn'] = localStorage.getItem('id') == profile['id'];
        profile['isAdmin'] = localStorage.getItem('role') == 'ROLE_ADMIN';
        return profile;
    }

    let edit = (extension) => {
        extension['description'] = extension['description'].replace(/<br \/>/gi, '\n');
        return extension;
    }

    return {
        searchResults,
        extension,
        profile,
        edit
    }
})();