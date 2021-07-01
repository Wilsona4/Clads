package com.decagonhq.clads.util

class Article(var author: String, var title: String, var imageUrl: String, var articleLink: String) {
    companion object {
        fun getDefaultArticleList(): ArrayList<Article> {
            return arrayListOf(
                Article(
                    "Chinua Achebe",
                    "Things Fall Apart",
                    "https://images-na.ssl-images-amazon.com/images/I/514QQ3BgxIL._SX323_BO1,204,203,200_.jpg",
                    "https://www.amazon.com/Things-Fall-Apart-Chinua-Achebe/dp/0385474547/"
                ),
                Article(
                    "Wole Soyinka",
                    "The Lion and the Jewel",
                    "https://images-na.ssl-images-amazon.com/images/I/41z1bJPQ08L.jpg",
                    "https://www.amazon.com/Lion-Jewel-Three-Crowns-Books/dp/0199110832"
                ),
                Article(
                    "Chimamanda Adichie",
                    "Purple Hibiscus",
                    "https://images-na.ssl-images-amazon.com/images/I/81nJUluy7cL.jpg",
                    "https://www.amazon.com/Purple-Hibiscus-Chimamanda-Ngozi-Adichie/dp/1616202416"
                ),
                Article(
                    "Ben Okri",
                    "The Famished Road",
                    "https://kbimages1-a.akamaihd.net/3ae6f404-9325-406d-b0c0-ef630e188cbc/1200/1200/False/the-famished-road-2.jpg",
                    "https://www.amazon.com/Famished-Road-Ben-Okri/dp/0385425139"
                ),
                Article(
                    "Buchi Emecheta",
                    "The Joys of Motherhood",
                    "https://images-na.ssl-images-amazon.com/images/I/51DvLRFmDGL.jpg",
                    "https://www.amazon.com/Buchi-Emecheta-Joys-Motherhood-Sixth/dp/B004S7QCFI"
                ),
                Article(
                    "Helon Habila",
                    "Oil on Water",
                    "https://images-na.ssl-images-amazon.com/images/I/61LOAG5H8ML.jpg",
                    "https://www.amazon.com/Oil-Water-Novel-Helon-Habila/dp/0393339645"
                ),
                Article(
                    "Adaobi Nwaubani",
                    "I Do Not Come to You By Chance",
                    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1389641343l/6644280.jpg",
                    "https://www.amazon.com/Do-Not-Come-You-Chance-ebook/dp/B002EBDP5K"
                ),
                Article(
                    "Babatunde Olatunji",
                    "The beat of my drum",
                    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1348016775l/858397.jpg",
                    "https://www.amazon.com/Beat-My-Drum-Autobiography/dp/1592133541"
                ),
                Article(
                    "Chris Abani",
                    "Graceland, Song for Night",
                    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1477391452l/32751931._SY475_.jpg",
                    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1477391452l/32751931._SY475_.jpg"
                ),
                Article(
                    "Chimamanda Adichie",
                    "Purple Hibiscus",
                    "https://images-na.ssl-images-amazon.com/images/I/81nJUluy7cL.jpg",
                    "https://www.amazon.com/Purple-Hibiscus-Chimamanda-Ngozi-Adichie/dp/1616202416"
                ),
                Article(
                    "Ben Okri",
                    "The Famished Road",
                    "https://kbimages1-a.akamaihd.net/3ae6f404-9325-406d-b0c0-ef630e188cbc/1200/1200/False/the-famished-road-2.jpg",
                    "https://www.amazon.com/Famished-Road-Ben-Okri/dp/0385425139"
                ),
                Article(
                    "Buchi Emecheta",
                    "The Joys of Motherhood",
                    "https://images-na.ssl-images-amazon.com/images/I/51DvLRFmDGL.jpg",
                    "https://www.amazon.com/Buchi-Emecheta-Joys-Motherhood-Sixth/dp/B004S7QCFI"
                ),
                Article(
                    "Chinua Achebe",
                    "Things Fall Apart",
                    "https://images-na.ssl-images-amazon.com/images/I/514QQ3BgxIL._SX323_BO1,204,203,200_.jpg",
                    "https://www.amazon.com/Things-Fall-Apart-Chinua-Achebe/dp/0385474547/"
                ),
                Article(
                    "Wole Soyinka",
                    "The Lion and the Jewel",
                    "https://images-na.ssl-images-amazon.com/images/I/41z1bJPQ08L.jpg",
                    "https://www.amazon.com/Lion-Jewel-Three-Crowns-Books/dp/0199110832"
                ),
                Article(
                    "Chimamanda Adichie",
                    "Purple Hibiscus",
                    "https://images-na.ssl-images-amazon.com/images/I/81nJUluy7cL.jpg",
                    "https://www.amazon.com/Purple-Hibiscus-Chimamanda-Ngozi-Adichie/dp/1616202416"
                ),
                Article(
                    "Helon Habila",
                    "Oil on Water",
                    "https://images-na.ssl-images-amazon.com/images/I/61LOAG5H8ML.jpg",
                    "https://www.amazon.com/Oil-Water-Novel-Helon-Habila/dp/0393339645"
                )
            )
        }
    }
}
