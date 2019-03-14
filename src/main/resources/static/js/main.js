function addToFavorite (id) {

    $.ajax({
        url: '/user/addToFavorite/'+id,
        type: 'GET',
        success: function () {
            alert("Post added to favorite posts");
        }

    })

}