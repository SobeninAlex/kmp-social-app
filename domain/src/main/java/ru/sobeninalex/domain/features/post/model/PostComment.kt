package ru.sobeninalex.domain.features.post.model

data class PostComment(
    val commentId: String,
    val postId: String,
    val userId: String,
    val content: String,
    val userName: String,
    val avatar: String?,
    val createdAt: String,
    val isOwnComment: Boolean = false,
    val isDeletingComment: Boolean = false,
) {

    companion object {
        val Preview = PostComment(
            commentId = "7ca39450-3afc-4775-9446-bcdd5c0939ff",
            postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
            userId = "e9400b68-cb98-41b9-877d-8e175de19705",
            content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
            userName = "Sara Conor",
            avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            createdAt = "30.04.2025 14:25"
        )

        val PreviewList = listOf(
            PostComment(
                commentId = "7ca39450-3afc-4775-9446-bcdd5c0939ff",
                postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
                userId = "e9400b68-cb98-41b9-877d-8e175de19705",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Sara Conor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            PostComment(
                commentId = "8a329b93-3e2a-4574-b3a8-e6a33d486e42",
                postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
                userId = "4aea60d2-e0e1-490b-9bab-fdee780cc428",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Terminator",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            PostComment(
                commentId = "569ad670-6ae7-442e-8a8e-7159ab877fe0",
                postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
                userId = "a717fb28-b6fb-4046-8587-235fc0bd467b",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "John Connor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            PostComment(
                commentId = "199295a7-abf8-4cd7-bec5-1b717cc79664",
                postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
                userId = "6f583612-10a7-465a-882a-c369c76fe026",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "T-1000",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            PostComment(
                commentId = "58edae01-eb30-411c-b0d4-834bd566928d",
                postId = "62331b5c-d98b-49b7-8934-2a8489607ee6",
                userId = "5639c487-7514-4365-98e0-9a231cea07e2",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Katrin Buster",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
        )
    }
}
