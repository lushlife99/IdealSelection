function logout() {
    $.ajax({
        type: 'GET',
        url: '/api/auth/logOut',
        success: function (response) {
            alert("로그아웃");
            window.location.href = '/login';
        },
        error: function (error) {
            alert(error.responseJSON.message);
        }
    });
}

function changePage(page) {
    if (page === 'prev') {
        getPrevPaginationList();
    } else if (page === 'next') {
        getNextPaginationList();
    } else if (typeof page === 'number') {
        currentPage = page;
    }

    updatePagination();
    updateContent();
}

function updatePagination() {
    var paginationLinks = document.querySelectorAll('.pagination a');
    paginationLinks.forEach(function (link) {
        link.classList.remove('active');
        if (link.innerHTML == pagePrefix + currentPage) {
            link.classList.add('active');
        }
    });
}