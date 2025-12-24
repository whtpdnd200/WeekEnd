


function createDocumentTag(content, userId) {

    let html = '';
    let profileImageTag = '';
    let postImageTag = '';
    let modiAndDelBtn = '';
    let commentTop3 = '<div class="comments-top3 m-3 p-3">';
    let isLikeTag = '';
    let commentBox = '';
    let followTag = '';
    let followingTag = '';
    if (userId != content.userId) {

        if (content.follower) {
            followingTag = '<span class="ml-2 text-small text-secondary">회원님을 팔로우중 입니다</span>';
        }
        if (!content.follow) {
            followTag = '<div>' +
                '   <button data-id="' + content.userId + '" class="followBtn btn btn-primary" type="button">팔로우</button>' +
                followingTag +
                '</div>'
        }

        if (content.follow && content.follower) {
            followTag = '<button data-id="' + content.userId + '" class="followRemoveBtn btn btn-success" type="button">맞 팔로우중</button>';
        }

        if (content.follow && !content.follower) {
            followTag = '<button data-id="' + content.userId + '" class="followRemoveBtn btn btn-info" type="button">팔로우중</button>';
        }
    }

    if (content.like) {
        isLikeTag = '<i data-post-id="' + content.id + '" class="bi bi-heart-fill text-danger like-btn"></i>';
    } else {
        isLikeTag = '<i data-post-id="' + content.id + '" class="bi bi-heart text-danger unlike-btn"></i>';
    }

    if (content.profileImage == null || content.profileImage == '') {
        profileImageTag = '<div class="d-flex pl-3 align-items-center">' +
            '                        <div><i class="bi bi-person-circle"></i></div>' +
            '                        <div class="pl-1 my-1"> ' + content.name + '</div>';
    } else {
        profileImageTag = '<div class="d-flex pl-3 align-items-center">' +
            '                        <div><img class="round-image" width="24px" height="24px" src="' + content.profileImage + '"></div>' +
            '                        <div class="pl-1 my-1"> ' + content.name + '</div>';
    }

    if (content.imagePath != null && content.imagePath != '') {
        postImageTag = '<div class="d-flex justify-content-center my-1">' +
            '                        <img class="postImage" width="450px" height="300px" src="' + content.imagePath + '">' +
            '                    </div>'
    }


    if (userId != null && content.userId == userId) {
        modiAndDelBtn = '<div class="d-flex justify-content-between w-50 ml-5">' +
            '                                <div>' +
            '                                    <a href="/post/modify?postId=' + content.id + '" class="text-dark"><i class="bi bi-pencil fs-5 postModify"></i></a>' +
            '                                </div>' +
            '                                <div>' +
            '                                    <i data-id="' + content.id + '" class="bi bi-trash postRemove"></i>' +
            '                                </div>' +
            '                            </div>';
    }

    if (content.comments.length == 0) {
        commentBox = '<div>' +
            '            <div class="font-weight-bold text-center">댓글이 없습니다</div>' +
            '         </div>';
        commentTop3 += commentBox;
    } else {
        $.each(content.comments, function (idx, comment) {
            commentBox = '';
            let commentProfileImage = '';

            if (comment.profileImage == null || comment.profileImage == '') {
                commentProfileImage = '<div>' +
                    '                     <i class="bi bi-person-circle personIcon"></i>' +
                    '                  </div>';
            } else {
                commentProfileImage = '<div>' +
                    '                     <img class="round-image" width="36px" height="36px" src="' + comment.profileImage + '">' +
                    '                   </div>';
            }

            commentBox = '<div class="comment d-flex my-1 align-items-center">' +
                commentProfileImage +
                '                        <div>' +
                '                            <div class=" my-1">' + comment.name + '</div>' +
                '                            <div class="mx-3 my-1">' + comment.comment + '</div>' +
                '                        </div>' +
                '</div>';

            commentTop3 += commentBox;
        });
    }

    commentTop3 += '                </div>';
    html = '<div class="post m-2">' +
        '                <div class="post-info">' +
        profileImageTag +
        '<div class="ml-2">' +
        followTag +
        '</div>' +
        '</div>' +
        postImageTag +
        '                    <div class="my-3 pl-5">' +
        '                        <div class="postContents">' + content.contents + '</div>' +
        '                    </div>' +
        '                    <div class="d-flex justify-content-center my-3">' +
        '                        <div class="w-75 d-flex justify-content-between">' +
        '                            <div class="d-flex justify-content-between w-50 mr-5">' +
        '                                <div class="d-flex align-items-center">' +
        isLikeTag +
        '                                    <div class="ml-2">' + content.likeCount + '</div>' +
        '                                </div>' +
        '                                <div class="d-flex align-items-center">' +
        '                                    <a data-id="' + content.id + '" class="commentList" data-bs-toggle="modal" href="#myModal"><i class="bi bi-chat-right text-dark"></i></a>' +
        '                                    <div class="ml-2">' + content.commentCount + '</div>' +
        '                                </div>' +
        '                            </div>' +
        modiAndDelBtn +
        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        commentTop3 +
        '<div class="d-flex p-3 align-items-end">' +
        '    <input name="documentCommentInput" type="text" class="form-control col-9 documentCommentInput">' +
        '    <button type="button" data-post-id="' + content.id + '"  class="btn btn-info col-3 ml-2 documentCommentBtn">입력</button>' +
        '</div>' +
        '            </div>';
    return html;
}

function createCommentTag(comment, userId) {
    let modiAndDelBtn = "";
    let profileTag = "";
    let html = '';

    if(comment.profileImage != '' && comment.profileImage != null) {
        profileTag = '<div class="userProfileImage">' +
            '   <img class="round-image" width="36px" height="36px" src="' + comment.profileImage +'">' +
            '</div>';
    } else {
        profileTag = '<div class="userProfileImage">' +
            '                   <i class="bi bi-person-circle personIcon"></i>' +
            '</div>';
    }

    if(userId == comment.userId) {

        modiAndDelBtn = '<div class="d-flex justify-content-end align-items-end">' +
            '               <div class="mr-3">' +
            '                   <i data-comment-id="' + comment.id + '" class="isModify bi bi-pencil commentModify"></i>' +
            '               </div>' +
            '               <div>' +
            '                   <i data-comment-id="' + comment.id + '" class="bi bi-trash commentRemove"></i>' +
            '               </div>' +
            '           </div>'
    }

    html = '<div id="modalComment'+ comment.id +'" class="modalComment d-flex my-3 align-items-center justify-content-between">' +
        '<div class="d-flex align-items-center">' +
        profileTag +
        '                            <div class="testDiv">' +
        '                            <div class="mx-3 my-1 userName">' + comment.name + '</div>' +
        '                            <div id="userComment'+ comment.id +'" class="mx-3 my-1 userComment">' + comment.comment + '</div>' +
        '                           </div>' +
        '</div>' +
        modiAndDelBtn +
        '</div>';
    return html;
}