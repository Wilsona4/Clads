package com.decagonhq.clads.util

import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceDetailVideoModel
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.data.domain.resource.ResourceGeneralVideoModel

object ResourceDummyData {
    var videoItem = mutableListOf(
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.description
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.description
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.description
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.description
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.description
        )
    )
    var articleItem = mutableListOf(
        ResourceGeneralArticleModel(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwC9jlm6Te09OSUx0U0vhdHcGaYmJsADr5A&usqp=CAU",
            "Stitching Neatly",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwC9jlm6Te09OSUx0U0vhdHcGaYmJsADr5A&usqp=CAU",
            "Stitching Neatly",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwC9jlm6Te09OSUx0U0vhdHcGaYmJsADr5A&usqp=CAU",
            "Stitching Neatly",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwC9jlm6Te09OSUx0U0vhdHcGaYmJsADr5A&usqp=CAU",
            "Stitching Neatly",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKwC9jlm6Te09OSUx0U0vhdHcGaYmJsADr5A&usqp=CAU",
            "Stitching Neatly",
            "By Nathan Maro"
        )
    )
    var videoViewAllItem = mutableListOf(
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew attire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "How to perfectly sew adire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew attire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "How to perfectly sew adire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew attire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "How to perfectly sew attire", "1 hour 24 mins"),
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew attire", "1 hour 24 mins"),
    )
}
