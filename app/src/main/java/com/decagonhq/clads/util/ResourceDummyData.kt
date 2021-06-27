package com.decagonhq.clads.util

import com.decagonhq.clads.data.domain.resource.ResourceDetailVideoModel
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.data.domain.resource.ResourceGeneralVideoModel

object ResourceDummyData {
    var videoItem = mutableListOf(
        ResourceGeneralVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire"),
        ResourceGeneralVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire"),
        ResourceGeneralVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire"),
        ResourceGeneralVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire"),
        ResourceGeneralVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew adire")
    )
    var articleItem = mutableListOf(
        ResourceGeneralArticleModel("https://atafo.africa/brand/", "Stitching Neatly", "By Nathan Maro"),
        ResourceGeneralArticleModel("https://atafo.africa/brand/", "Stitching Neatly", "By Nathan Maro"),
        ResourceGeneralArticleModel("https://atafo.africa/brand/", "Stitching Neatly", "By Nathan Maro"),
        ResourceGeneralArticleModel("https://atafo.africa/brand/", "Stitching Neatly", "By Nathan Maro"),
        ResourceGeneralArticleModel("https://atafo.africa/brand/", "Stitching Neatly", "By Nathan Maro")
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
