package ru.sobeninalex.domain.features.post

import org.koin.dsl.module
import ru.sobeninalex.domain.features.post.usecase.*

internal val PostFeatureModule = module {

    factory {
        AddCommentUseCase(repository = get())
    }

    factory {
        DeleteCommentUseCase(repository = get())
    }

    factory {
        GetFeedPostsUseCase(repository = get())
    }

    factory {
        GetPostCommentsUseCase(repository = get())
    }

    factory {
        GetPostsByUserIdUseCase(repository = get())
    }

    factory {
        GetPostUseCase(repository = get())
    }

    factory {
        LikeOrUnlikeUseCase(repository = get())
    }
}