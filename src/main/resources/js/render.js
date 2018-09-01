let render = (() => {

    let searchResults = (res, query) => {
        res['prev'] = +res['currentPage'] > 1;
        res['next'] = +res['currentPage'] < res['totalPages'];
        res['prevNum'] = +res['currentPage'] - 1;
        res['nextNum'] = +res['currentPage'] + 1;
        res['query'] = query;
        return res;
    }

    let extension = (extension) => {
        extension['uploadDate'] = moment(extension['uploadDate']).format('MMM, DD YYYY');
        extension['lastCommit'] = moment(extension['lastCommit']).format('MMM, DD YYYY');
        extension['isOwn'] = localStorage.getItem('id') == extension['ownerId'];
        extension['isAdmin'] = localStorage.getItem('role') === 'ROLE_ADMIN';
        return extension;
    }

    let profile = (profile) => {
        profile['isAdmin'] = localStorage.getItem('role') === 'ROLE_ADMIN';
        return profile;
    }

    return {
        searchResults,
        extension,
        profile
    }
})();