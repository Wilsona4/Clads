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
            R.drawable.desc_four
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.desc_one
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.desc_seven
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.desc_five
        ),
        ResourceGeneralVideoModel(
            "https://html5demos.com/assets/dizzy.mp4",
            "How to perfectly sew adire",
            R.drawable.desc_two
        )
    )
    var articleItem = mutableListOf(
        ResourceGeneralArticleModel(
            "https://i.pinimg.com/236x/84/21/22/84212218025a261761611acde7bcc750--modern-patterns-vintage-sewing-patterns.jpg",
            "Modern Pattern Design",
            "By Micheal Isesele"
        ),
        ResourceGeneralArticleModel(
            "https://images-na.ssl-images-amazon.com/images/I/71zkHiajWKL.jpg",
            "Tailoring",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://d3re0f381bckq9.cloudfront.net/21704349_1538442884364_336x400.jpg",
            "Sewing Book",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://cdn.exoticindia.com/images/products/original/books-2017/naj257.jpg",
            "Stitching Neatly",
            "By Nathan Maro"
        ),
        ResourceGeneralArticleModel(
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fd1w7fb2mkkr3kw.cloudfront.net%2Fassets%2Fimages%2Fbook%2Flrg%2F9781%2F6008%2F9781600853357.jpg&imgrefurl=https%3A%2F%2Fwww.bookdepository.com%2FCouture-Sewing-Techniques-Claire-B-Shaeffer%2F9781600853357&tbnid=QxNx15mBTejnrM&vet=10CBgQxiAoB2oXChMIyNz66fb48QIVAAAAAB0AAAAAEAg..i&docid=HrY8DBWVY-9VwM&w=344&h=430&itg=1&q=tailoring%20publication%20covers&ved=0CBgQxiAoB2oXChMIyNz66fb48QIVAAAAAB0AAAAAEAg",
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
        ResourceDetailVideoModel("https://html5demos.com/assets/dizzy.mp4", "How to perfectly sew attire", "1 hour 24 mins")
    )
}
